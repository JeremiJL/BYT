package emptio.adapters.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import emptio.adapters.rest.home.EditProfileFormHandler;
import emptio.adapters.rest.home.EditProfileHandler;
import emptio.adapters.rest.home.HomeHandler;
import emptio.adapters.rest.login.CreateAccountHandler;
import emptio.adapters.rest.login.LoginFormHandler;
import emptio.adapters.rest.login.LoginHandler;
import emptio.adapters.rest.login.CreateAccountFormHandler;
import static emptio.adapters.rest.utils.FilePathToBytes.getBytes;

import emptio.adapters.rest.merchant.CreateProductHandler;
import emptio.common.SymetricEncryptor;
import emptio.domain.product.ProductService;
import emptio.domain.user.User;
import emptio.domain.user.UserService;
import lombok.NonNull;

import java.io.IOException;
import java.net.InetSocketAddress;




public class Server {

    private final int port;
    private final @NonNull UserService userService;
    private final @NonNull ProductService productService;
    private final @NonNull SymetricEncryptor symmetricEncryptor;

    public Server(int port, UserService userService, ProductService productService, SymetricEncryptor symmetricEncryptor) {
        this.port = port;
        this.userService = userService;
        this.productService = productService;
        this.symmetricEncryptor = symmetricEncryptor;
    }

    public void run() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        this.linkHandlers(server);
        server.setExecutor(null);
        server.start();
    }

    private void linkHandlers(HttpServer server) {
//        linkHandlersOfStaticContent(server);
        linkTemplatePages(server);
    }

    private void linkHandlersOfStaticContent(HttpServer server) {
        server.createContext("/static/basic.css", new BasicHandler(getBytes("src/main/resources/ui/static/basic.css")) {
            @Override
            public void handleExchange(HttpExchange exchange) throws IOException {
                this.renderPage(exchange, getDefaultPage());
            }
        });
        server.createContext("/static/forms.css", new BasicHandler(getBytes("src/main/resources/ui/static/forms.css")) {
            @Override
            public void handleExchange(HttpExchange exchange) throws IOException {
                this.renderPage(exchange, getDefaultPage());
            }
        });

        server.createContext("/static/logo.png", new BasicHandler(getBytes("src/main/resources/ui/static/logo.png")) {
            @Override
            public void handleExchange(HttpExchange exchange) throws IOException {
                this.renderPage(exchange, getDefaultPage());
            }
        });
    }

    private void linkTemplatePages(HttpServer server) {
        linkPagesForNotLoggedUsers(server);
        linkPagesForLoggedUsers(server);
        linkPagesForLoggedMerchants(server);
        linkPagesForLoggedShoppers(server);
        linkPagesForLoggedAdvertisers(server);
    }

    private void linkPagesForNotLoggedUsers(HttpServer server) {
        server.createContext("/",
                new LoginFormHandler(getBytes("src/main/resources/ui/template/login/login_form.html"))); // Landing login form page
        server.createContext("/login",
                new LoginHandler(getBytes("src/main/resources/ui/template/login/login.html"), // Login data is sent, redirect to home page performed
                        this.userService, this.symmetricEncryptor));
        server.createContext("/create_account_form",
                new CreateAccountFormHandler(getBytes("src/main/resources/ui/template/login/create_account_form.html"))); // New account form page for new users
        server.createContext("/create_account",
                new CreateAccountHandler(getBytes("src/main/resources/ui/template/login/create_account.html"), this.userService)); // New account data is sent, redirect to success/failure page performed

    }

    private void linkPagesForLoggedUsers(HttpServer server) {
        server.createContext("/home",
                new HomeHandler(
                        getBytes("src/main/resources/ui/template/home/home.html"), // Home page for logged users
                        this.userService, this.symmetricEncryptor));
        server.createContext("/edit_profile_form",
                new EditProfileFormHandler(
                        getBytes("src/main/resources/ui/template/home/edit_profile_form.html"), // Edit profile form page for logged users
                        this.userService, this.symmetricEncryptor));
        server.createContext("/edit_profile",
                new EditProfileHandler(
                        getBytes("src/main/resources/ui/template/home/edit_profile.html"), // Edit profile page for logged users
                        this.userService, this.symmetricEncryptor));

    }

    private void linkPagesForLoggedAdvertisers(HttpServer server) {

    }

    private void linkPagesForLoggedShoppers(HttpServer server) {

    }

    private void linkPagesForLoggedMerchants(HttpServer server) {
        server.createContext("/merchant/create_product_form",
                new SessionHandler(
                        getBytes("src/main/resources/ui/template/merchant/create_product_form.html"), // Create new product form page for logged merchants
                        this.userService, this.symmetricEncryptor) {
                    @Override
                    public void handleExchangeSession(HttpExchange exchange, User user) throws IOException {
                        renderPage(exchange, getDefaultPage());
                    }
                });
        server.createContext("/merchant/create_product",
                new CreateProductHandler(
                        getBytes("src/main/resources/ui/template/merchant/create_product.html"), // Create new product page for logged merchants
                        this.userService, this.symmetricEncryptor,
                        this.productService
                ));
    }
}