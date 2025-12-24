package emptio.adapters.rest;

import com.sun.net.httpserver.HttpExchange;
import emptio.common.SymetricEncryptor;
import emptio.common.SymetricEncryptorException;
import emptio.domain.RepositoryException;
import emptio.domain.user.User;
import emptio.domain.user.UserService;
import lombok.Getter;
import lombok.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public final void handle(HttpExchange exchange) throws IOException {

        String sessionKey = getCookies(exchange).get("sessionKey");

        if (isSessionValid(sessionKey)) {
            User user = fetchUserForSession(sessionKey);
            this.handleSession(exchange, user);
        } else {
            this.showPage(exchange, getFallbackPage());
        }
    }

    public abstract void handleSession(HttpExchange exchange, User user) throws IOException;

    private boolean isSessionValid(String sessionKey)  {
        try {
            fetchUserForSession(sessionKey);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    private User fetchUserForSession(String sessionKey) {
        try {
            int id = Integer.parseInt(symmetricEncryptor.decrypt(sessionKey));
            return this.userService.getUser(id);
        } catch (SymetricEncryptorException | NumberFormatException | RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String,String> getCookies(HttpExchange exchange) {

        StringBuilder mergedCookies = new StringBuilder();

        for (String c :  exchange.getRequestHeaders().get("Cookie"))
            mergedCookies.append(c);


        Map<String,String> cookies = new HashMap<>();

        for (String entry : mergedCookies.toString().split(";")) {
            String key = entry.trim().split("=")[0].trim();
            String value = entry.substring(key.length() + 2).trim();
            cookies.put(key, value);
        }

        return cookies;
    }


}
