package indexprocessor.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConfigLoader {

    private final Properties properties = new Properties();

    public ConfigLoader() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (in != null) {
                properties.load(in);
            } else {
                throw new RuntimeException("application.properties not found in resources");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public String getDelimiter(String fileType) {
        return properties.getProperty("delimiter." + fileType, "\\s+");
    }

    public List<String> getAllowedFileTypes() {
        String types = properties.getProperty("allowedFileTypes", "txt,csv,json");
        return Arrays.asList(types.split(","));
    }
}
