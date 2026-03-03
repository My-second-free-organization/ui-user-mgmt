package io.flowforge.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();
    private JsonUtils() {}

    public static String toJson(Object obj) {
        try { return mapper.writeValueAsString(obj); }
        catch (JsonProcessingException e) { throw new RuntimeException("JSON serialize failed", e); }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try { return mapper.readValue(json, clazz); }
        catch (JsonProcessingException e) { throw new RuntimeException("JSON deserialize failed", e); }
    }

    public static JsonNode parse(String json) {
        try { return mapper.readTree(json); }
        catch (JsonProcessingException e) { throw new RuntimeException("JSON parse failed", e); }
    }
}
