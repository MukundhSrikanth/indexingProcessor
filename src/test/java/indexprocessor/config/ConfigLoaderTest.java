package indexprocessor.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigLoaderTest {

    private ConfigLoader configLoader;

    @BeforeEach
    void setUp() {
        configLoader = new ConfigLoader();
    }

    @Test
    void testGetDelimiterForTxt() {
        String delimiter = configLoader.getDelimiter("txt");
        assertThat(delimiter).isNotNull();
        assertThat(delimiter).isEqualTo("\\s+");
    }

    @Test
    void testGetDelimiterForCsv() {
        String delimiter = configLoader.getDelimiter("csv");
        assertThat(delimiter).isNotNull();
        assertThat(delimiter).isEqualTo(",");
    }

    @Test
    void testGetDelimiterForUnknownTypeUsesDefault() {
        String delimiter = configLoader.getDelimiter("unknownType");
        assertThat(delimiter).isEqualTo("\\s+");
    }

    @Test
    void testGetAllowedFileTypes() {
        List<String> types = configLoader.getAllowedFileTypes();
        assertThat(types).contains("txt", "csv", "json");
    }
}
