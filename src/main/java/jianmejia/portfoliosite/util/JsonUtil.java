package jianmejia.portfoliosite.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readJson(String filePath, TypeReference<T> type) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        return objectMapper.readValue(file, type);
    }

    public static <T> void writeJson(String filePath, T data) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
    }
}
