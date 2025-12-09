package emptio.adapters.rest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class HtppFormToJson {

    public static String convert(byte[] data) {
        String plain = new String(data);
        String[] pairs = plain.split("&");
        Map<String, String> map = new HashMap<>();
        for (String pair : pairs)
            map.put(pair.split("=")[0],pair.split("=")[1]);
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
