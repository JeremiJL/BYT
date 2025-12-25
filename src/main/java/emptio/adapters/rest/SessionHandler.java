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

public abstract class SessionHandler extends BasicHandler {

    @NonNull private final UserService userService;
    @NonNull private final SymetricEncryptor symmetricEncryptor;
    @Getter @NonNull private final byte[] fallbackPage;

    public SessionHandler(byte[] pageWithAuthorizedAccess, byte[] fallbackPage, UserService userService, @NonNull SymetricEncryptor symmetricEncryptor) {
        super(pageWithAuthorizedAccess);
        this.fallbackPage = fallbackPage;
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
            return this.userService.getUser(userId);
        } catch (SymetricEncryptorException | NumberFormatException e) {
            return Optional.empty();
        }
    }
}
