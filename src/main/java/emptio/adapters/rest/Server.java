package emptio.adapters.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import emptio.adapters.rest.login.CreateAccountHandler;
import emptio.adapters.rest.login.LoginFormHandler;
import emptio.adapters.rest.login.LoginHandler;
import emptio.adapters.rest.login.CreateAccountFormHandler;
import static emptio.adapters.rest.utils.FilePathToBytes.getBytes;

import emptio.common.SymetricEncryptor;
import emptio.domain.user.UserService;

import java.io.IOException;
import java.net.InetSocketAddress;




public class Server {

    private final UserService userService;
    private final SymetricEncryptor symmetricEncryptor;

    public Server(UserService userService, SymetricEncryptor symmetricEncryptor) {
        this.userService = userService;
        this.symmetricEncryptor = symmetricEncryptor;
    }

    public void run() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        this.linkHandlers(server);
        server.setExecutor(null);
        server.start();
    }

    private void linkHandlers(HttpServer server) {

//        server.createContext("/static/basic.css", new BasicHandler(getBytes("src/main/resources/ui/static/basic.css")) {
//            @Override
//            public void handle(HttpExchange exchange) throws IOException {
//                this.showPage(exchange, getPage());
//            }
//        }); // css file
//        server.createContext("/static/forms.css", new BasicHandler(getBytes("src/main/resources/ui/static/forms.css")) {
//            @Override
//            public void handle(HttpExchange exchange) throws IOException {
//                this.showPage(exchange, getPage());
//            }
//        }); // css file
//
//        server.createContext("/static/logo.png", new BasicHandler(getBytes("src/main/resources/ui/static/logo.png")) {
//            @Override
//            public void handle(HttpExchange exchange) throws IOException {
//                this.showPage(exchange, getPage());
//            }
//        }); // image file

        server.createContext("/",
                new LoginFormHandler(getBytes("src/main/resources/ui/template/login/login_form.html"))); // Landing login form page
        server.createContext("/login",
                new LoginHandler(getBytes("src/main/resources/ui/template/login/login.html"), // Login data is sent, redirect to home page performed
                        this.userService, this.symmetricEncryptor));

        server.createContext("/create_account_form",
                new CreateAccountFormHandler(getBytes("src/main/resources/ui/template/login/create_account_form.html"))); // New account form page for new users
        server.createContext("/create_account",
                new CreateAccountHandler(getBytes("src/main/resources/ui/template/login/create_account.html"), this.userService)); // New account data is sent, redirect to success/failure page performed

        server.createContext("/home",
                new HomeHandler(
                        getBytes("src/main/resources/ui/template/home/home.html"), // Home page for logged users
                        getBytes("src/main/resources/ui/template/login/login_form.html"), // Redirects to log page if user is not logged
                        this.userService, this.symmetricEncryptor));
    }

}