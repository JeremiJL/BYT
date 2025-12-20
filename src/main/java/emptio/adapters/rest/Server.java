package emptio.adapters.rest;

import com.sun.net.httpserver.HttpServer;
import emptio.adapters.rest.login.CreateAccountHandler;
import emptio.adapters.rest.login.LoginFormHandler;
import emptio.adapters.rest.login.LoginHandler;
import emptio.adapters.rest.login.CreateAccountFormHandler;
import static emptio.adapters.rest.utils.FilePathToBytes.getBytes;
import emptio.domain.user.UserService;

import java.io.IOException;
import java.net.InetSocketAddress;




public class Server {

    private final UserService userService;

    public Server(UserService userService) {
        this.userService = userService;
    }

    public void run() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        this.linkHandlers(server);
        server.setExecutor(null);
        server.start();
    }

    private void linkHandlers(HttpServer server) {

        server.createContext("/",
                new LoginFormHandler(getBytes("src/main/resources/ui/login/.html"))); // Landing login form page
        server.createContext("/login",
                new LoginHandler(getBytes("src/main/resources/ui/login/login.html"), this.userService)); // Login data is sent, redirect to home page performed

        server.createContext("/home",
                new HomeHandler(getBytes("src/main/resources/ui/home.html"))); // Home page for logged users

        server.createContext("/create_account_form",
                new CreateAccountFormHandler(getBytes("src/main/resources/ui/login/create_account_form.html"))); // New account form page for new users
        server.createContext("/create_account",
                new CreateAccountHandler(getBytes("src/main/resources/ui/login/create_account.html"), this.userService)); // New account data is sent, redirect to success/failure page performed
    }

}