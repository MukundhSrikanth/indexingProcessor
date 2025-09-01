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
    private final PrintStream originalErr = System.err;
    private ByteArrayOutputStream outputStream;
    private ByteArrayOutputStream errStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        errStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(errStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void testNoArguments() {
        IndexingProcessorApp.main(new String[]{});
        String output = errStream.toString().trim();
        assertThat(output).contains("Please provide the path to the input file");
    }

    @Test
    void testFileNotFound() {
        IndexingProcessorApp.main(new String[]{"nonexistent.txt"});
        String output = errStream.toString().trim();
        assertThat(output).contains("No tokens could be extracted from the file");
    }

    @Test
    void testNormalFile() {
        Path filePath = Path.of("src/test/resources/testFiles/sample.txt");
        IndexingProcessorApp.main(new String[]{filePath.toString()});

        String output = outputStream.toString();
        assertThat(output).contains("Rule: StartsWithMRule");
        assertThat(output).contains("Rule: LongWordsRule");
        assertThat(output).contains("Mukund", "micro-services");
    }

    @Test
    void testFileWithPunctuation() {
        Path filePath = Path.of("src/test/resources/testFiles/special.txt");
        IndexingProcessorApp.main(new String[]{filePath.toString()});

        String output = outputStream.toString();
        assertThat(output).contains("Rule: StartsWithMRule");
        assertThat(output).contains("Rule: LongWordsRule");
        assertThat(output).contains("micro-services");
    }

    @Test
    void testEmptyFile() {
        Path filePath = Path.of("src/test/resources/testFiles/empty.txt");
        IndexingProcessorApp.main(new String[]{filePath.toString()});

        String output = errStream.toString().trim();
        assertThat(output).contains("No tokens could be extracted from the file");
    }
}
