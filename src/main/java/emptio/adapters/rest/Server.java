package emptio.adapters.rest;

import com.sun.net.httpserver.HttpServer;

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
        server.createContext("/user", new UserHandler());
        server.createContext("/product", new ProductHandler());
    }

}