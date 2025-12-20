package emptio.adapters.rest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class HttpFormConverter {

    public static String convertToJson(byte[] data) {
        try {
            return new ObjectMapper().writeValueAsString(convertToMap(data));
        } catch (RuntimeException | JsonProcessingException e) {
            throw new HttpFormToJsonConversionException("Conversion of data from HTTP form to json failed : " + e);
        }
    }

    public static Map<String, String> convertToMap(byte[] data) {
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
}

