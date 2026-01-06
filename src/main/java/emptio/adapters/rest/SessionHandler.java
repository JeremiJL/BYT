package emptio.adapters.rest;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.utils.CookiesExtractor;
import emptio.common.SymetricEncryptor;
import emptio.common.SymetricEncryptorException;
import emptio.domain.user.User;
import emptio.domain.user.UserService;
import lombok.Getter;
import lombok.NonNull;

import java.io.IOException;
import java.util.Optional;

import static emptio.adapters.rest.utils.FilePathToBytes.getBytes;

public abstract class SessionHandler extends BasicHandler {

    @NonNull private final UserService userService;
    @NonNull private final SymetricEncryptor symmetricEncryptor;
    @Getter @NonNull private final byte[] fallbackPage;

    public SessionHandler(byte[] pageWithAuthorizedAccess, UserService userService, @NonNull SymetricEncryptor symmetricEncryptor) {
        super(pageWithAuthorizedAccess);
        this.fallbackPage = getBytes("src/main/resources/ui/template/login/login_form.html");
        this.userService = userService;
        this.symmetricEncryptor = symmetricEncryptor;
    }

    @Override
    public final void handleExchange(HttpExchange exchange) throws IOException {

        String sessionKey = CookiesExtractor.getCookies(exchange).get("sessionKey");
        Optional<User> user = fetchUser(sessionKey);

        if (user.isPresent()) {
            this.handleExchangeSession(exchange, user.get());
        } else {
            this.renderPage(exchange, getFallbackPage());
        }
    }

    public abstract void handleExchangeSession(HttpExchange exchange, User user) throws IOException;

    private Optional<User> fetchUser(String sessionKey) {
        try {
            int userId = Integer.parseInt(
                    symmetricEncryptor.decrypt(sessionKey)
            );
            return Optional.of(this.userService.getUser(userId));
        } catch (SymetricEncryptorException | NumberFormatException e) {
            return Optional.empty();
        }
    }
}
