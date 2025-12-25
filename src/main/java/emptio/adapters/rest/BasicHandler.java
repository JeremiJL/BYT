package emptio.adapters.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.Getter;
import lombok.NonNull;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static emptio.adapters.rest.utils.FilePathToBytes.getBytes;


public abstract class BasicHandler implements HttpHandler {

    @NonNull @Getter private final byte[] defaultPage;
    @NonNull @Getter private final byte[] errorPage;

    public BasicHandler(@NonNull byte[] page) {
        this.defaultPage = page;
        this.errorPage = getBytes("src/main/resources/ui/template/error.html");
    }

    @Override
    public final void handle(HttpExchange exchange) throws IOException {
        try {
            handleExchange(exchange);
        } catch (Exception e) {
            Map<String, String> templateData = new HashMap<>();
            templateData.put("ERROR", e.getMessage());
            renderPage(exchange, applyDataToTemplate(
                    getErrorPage(), templateData));
        }
    }

    public abstract void handleExchange(HttpExchange exchange) throws IOException;

    public void renderPage(HttpExchange exchange, byte[] pageToBeShown) throws IOException {

        exchange.sendResponseHeaders(200, pageToBeShown.length);
        OutputStream os = exchange.getResponseBody();
        os.write(pageToBeShown);
        os.close();
        exchange.close();
    }

    public byte[] applyDataToTemplate(byte[] pageWithTemplate, Map<String, String> templateData) {

        String updatedPage = new String(pageWithTemplate);

        for (Entry<String, String> record : templateData.entrySet()) {
            String templatedKey = "{{ " + record.getKey() + " }}";
            String templatedValue = record.getValue();
            updatedPage = updatedPage.replace(templatedKey, templatedValue);
        }

        return updatedPage.getBytes();
    }

}
