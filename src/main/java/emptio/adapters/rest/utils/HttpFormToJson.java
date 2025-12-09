package emptio.adapters.rest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class HttpFormToJson {

    public static String convert(byte[] data) {
        try {
            String plain = new String(data);
            String[] pairs = plain.split("&");
            Map<String, String> map = new HashMap<>();
            for (String pair : pairs) {
                map.put(pair.split("=")[0],pair.split("=")[1]);
            }
            return new ObjectMapper().writeValueAsString(map);
        } catch (RuntimeException | JsonProcessingException e) {
            throw new HttpFormToJsonConversionException("Conversion of data from HTTP form to json failed : " + e);
        }
    }
}

