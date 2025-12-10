package emptio.adapters.rest.login;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.BasicHandler;
import emptio.adapters.rest.utils.HttpFormConverter;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CreateAccountHandler extends BasicHandler {

    public CreateAccountHandler(byte[] page) {
        super(page);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        byte[] requestData = exchange.getRequestBody().readAllBytes();
        Map<String,String> readableRequestData = HttpFormConverter.convertToMap(requestData);

        System.out.println(readableRequestData);
    }
}
