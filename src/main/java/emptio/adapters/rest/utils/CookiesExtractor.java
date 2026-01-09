package emptio.adapters.rest.utils;

import com.sun.net.httpserver.HttpExchange;

import java.util.*;

public class CookiesExtractor {

    public static Map<String, String> getCookies(HttpExchange exchange) {
        try {
            List<String> headerValues = exchange.getRequestHeaders().get("Cookie");
            if (headerValues == null || headerValues.isEmpty()) {
                return Collections.emptyMap();
            }
            String mergedCookies = String.join("; ", headerValues);
            Map<String, String> cookies = new HashMap<>();

            for (String entry : mergedCookies.split(";")) {
                String trimmed = entry.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                int eq = trimmed.indexOf('=');
                if (eq < 0) {
                    continue;
                }

                String key = trimmed.substring(0, eq).trim();
                String value = trimmed.substring(eq + 1);
                cookies.put(key, value);
            }

            return cookies;
        } catch (RuntimeException e) {
            return Collections.emptyMap();
        }
    }

}
