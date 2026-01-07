package emptio.adapters.rest.merchant;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.SessionHandler;
import emptio.adapters.rest.utils.HttpConverter;
import emptio.adapters.rest.utils.ImageBytesExtractor;
import emptio.common.SymetricEncryptor;
import emptio.domain.user.User;
import emptio.domain.user.UserService;
import lombok.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class CreateProductHandler extends SessionHandler {

    private final UserService userService;

    public CreateProductHandler(byte[] pageWithAuthorizedAccess, UserService userService, @NonNull SymetricEncryptor symmetricEncryptor) {
        super(pageWithAuthorizedAccess, userService, symmetricEncryptor);
        this.userService = userService;
    }

    @Override
    public void handleExchangeSession(HttpExchange exchange, User user) throws IOException {

        ImageBytesExtractor.extract(exchange);

//        String productCategory = requestData.get("productCategory");
//        String title = requestData.get("title");
//        String description = requestData.get("description");

        renderPage(exchange, getDefaultPage());
    }
}
