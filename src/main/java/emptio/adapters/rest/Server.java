package emptio.adapters.rest;

import com.sun.net.httpserver.HttpServer;
import emptio.adapters.rest.login.CreateAccountHandler;
import emptio.adapters.rest.login.LoginFormHandler;
import emptio.adapters.rest.login.LoginHandler;
import emptio.adapters.rest.login.CreateAccountFormHandler;
import emptio.adapters.rest.utils.FileToBytes;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;


public class Server {

    static public void run() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        linkHandlers(server);
        server.setExecutor(null);
        server.start();
    }

    private static void linkHandlers(HttpServer server) {

        server.createContext("/", new LoginFormHandler(FileToBytes.getBytes(new File("ui/login/.html")))); // Landing login form page
        server.createContext("/login", new LoginHandler(FileToBytes.getBytes(new File("ui/login/login.html")))); // Login data is sent, redirect to home page performed

        server.createContext("/home", new HomeHandler(FileToBytes.getBytes(new File("ui/home.html")))); // Home page for logged users

        server.createContext("/create_account_form", new CreateAccountFormHandler(FileToBytes.getBytes(new File("ui/login/create_account_form.html")))); // New account form page for new users
        server.createContext("/create_account", new CreateAccountHandler(FileToBytes.getBytes(new File("ui/login/create_account.html")))); // New account data is sent, redirect to success/failure page performed
    }

}