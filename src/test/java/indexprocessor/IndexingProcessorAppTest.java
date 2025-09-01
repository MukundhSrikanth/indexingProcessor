package indexprocessor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class IndexingProcessorAppTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testNoArguments() {
        IndexingProcessorApp.main(new String[]{});
        String output = outputStream.toString().trim();
        assertThat(output).contains("Please provide the path to the input file");
    }

    @Test
    void testFileNotFound() {
        IndexingProcessorApp.main(new String[]{"nonexistent.txt"});
        String output = outputStream.toString().trim();
        assertThat(output).contains("No tokens could be extracted from the file");
    }

    @Test
    void testNormalFile() {
        Path filePath = Path.of("src/test/resources/sample.txt");
        IndexingProcessorApp.main(new String[]{filePath.toString()});

        String output = outputStream.toString();
        assertThat(output).contains("Rule: StartsWithMRule");
        assertThat(output).contains("Rule: LongWordsRule");
        assertThat(output).contains("Mukund", "microservice");
    }

    @Test
    void testFileWithPunctuation() {
        Path filePath = Path.of("src/test/resources/special.txt");
        IndexingProcessorApp.main(new String[]{filePath.toString()});

        String output = outputStream.toString();
        assertThat(output).contains("Rule: StartsWithMRule");
        assertThat(output).contains("Rule: LongWordsRule");
        assertThat(output).contains("microservice");
    }

    @Test
    void testEmptyFile() {
        Path filePath = Path.of("src/test/resources/empty.txt");
        IndexingProcessorApp.main(new String[]{filePath.toString()});

        String output = outputStream.toString();
        assertThat(output).contains("No tokens could be extracted from the file");
    }
}
