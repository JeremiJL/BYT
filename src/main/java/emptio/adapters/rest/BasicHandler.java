package emptio.adapters.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.NonNull;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

public abstract class BasicHandler implements HttpHandler {

    @NonNull private byte[] page;

    public BasicHandler(byte[] page) {
        this.page = page;
    }

    public void showPage(HttpExchange exchange) throws IOException {

        exchange.sendResponseHeaders(200, page.length);
        OutputStream os = exchange.getResponseBody();
        os.write(page);
        os.close();
    }

    public void renderTemplate(Map<String, String> templateData) throws IOException {

        String pageAsText = new String(page);

        for (Entry<String, String> record : templateData.entrySet()) {
            String templatedKey = "{{ " + record.getKey() + " }}";
            String templatedValue = record.getValue();
            pageAsText = pageAsText.replace(templatedKey, templatedValue);
        }

        this.page = pageAsText.getBytes();
    }

}
