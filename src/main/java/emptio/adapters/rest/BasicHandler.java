package emptio.adapters.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.NonNull;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BasicHandler implements HttpHandler {

    @NonNull private final byte[] page;

    public byte[] getPage() {
        return page;
    }

    public BasicHandler(byte[] page) {
        this.page = page;
    }

    public void showPage(HttpExchange exchange, byte[] pageToBeShown) throws IOException {

        exchange.sendResponseHeaders(200, pageToBeShown.length);
        OutputStream os = exchange.getResponseBody();
        os.write(pageToBeShown);
        os.close();
        exchange.close();
    }

    public byte[] renderTemplate(Map<String, String> templateData) {

        String pageAsText = new String(this.getPage());

        for (Entry<String, String> record : templateData.entrySet()) {
            String templatedKey = "{{ " + record.getKey() + " }}";
            String templatedValue = record.getValue();
            pageAsText = pageAsText.replace(templatedKey, templatedValue);
        }

        return pageAsText.getBytes();
    }

}
