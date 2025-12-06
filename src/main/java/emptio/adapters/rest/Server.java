package emptio.adapters.rest;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import static com.sun.net.httpserver.HttpsServer.create;

public class Server {

    static public void run() throws IOException {

        HttpServer server = create(new
                InetSocketAddress(8000),0);
        linkHandlers(server);
        server.start();
    }

    private static void linkHandlers(HttpServer server) {
        server.createContext("/user").setHandler(new UserHandler());
        server.createContext("/product").setHandler(new ProductHandler());
    }

}