package emptio.adapters.rest.login;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.BasicHandler;
import emptio.adapters.rest.utils.HttpFormToJson;

import java.io.File;
import java.io.IOException;

public class CreateAccountHandler extends BasicHandler {

    public CreateAccountHandler(File page) {
        super(page);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        byte[] requestData = exchange.getRequestBody().readAllBytes();
        String readableRequestData = HttpFormToJson.convert(requestData);

        System.out.println(readableRequestData);
    }
}
