package emptio.adapters.rest.utils;

import java.util.HashMap;
import java.util.Map;

public class HttpConverter {

    public static Map<String, String> convertFormDataToMap(byte[] data) throws HttpFormToJsonConversionException {
        try {
            String plain = new String(data);
            String[] pairs = plain.split("&");
            Map<String, String> map = new HashMap<>();
            for (String pair : pairs) {
                String key = pair.split("=")[0];
                String value = (pair.split("=").length == 1) ? null : pair.split("=")[1];
                map.put(key,value);
            }
            return map;
        } catch (RuntimeException e) {
            throw new HttpFormToJsonConversionException("Conversion of data from HTTP form to json failed : " + e);
        }
    }

    public static String convertEscapeCharacters(String value) {
        return value
                .replaceFirst("%40", "@")
                .replaceFirst("/+"," ");
    }
}

