package emptio.adapters.rest.home;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.SessionHandler;
import emptio.common.SymetricEncryptor;
import emptio.domain.user.*;
import emptio.domain.utils.AccountTypeUtils;

import java.io.IOException;
import java.util.Map;

public class HomeHandler extends SessionHandler {

    public HomeHandler(byte[] pageWithAuthorizedAccess, UserService userService, SymetricEncryptor symmetricEncryptor) {
        super(pageWithAuthorizedAccess, userService, symmetricEncryptor);
    }

    @Override
    public void handleExchangeSession(HttpExchange exchange, User user) throws IOException {
        String userName = user.getName();
        String accountType = AccountTypeUtils.getAccountType(user).toString();

        Map<String, String> template = Map.of("USER_NAME", userName, "ACCOUNT_TYPE", accountType);
        this.renderPage(exchange, applyDataToTemplate(getDefaultPage(), template));
    }

}
