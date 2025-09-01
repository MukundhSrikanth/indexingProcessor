package indexprocessor.core;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenizerTest {

    private final Tokenizer tokenizer = new Tokenizer();

    @Test
    void testTokenizeFile() {
        Path filePath = Path.of("src/test/resources/sample.txt");
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isPresent();
        assertThat(tokens.get()).contains("Hello", "world", "Java", "Mukund", "microservice");
    }

    @Test
    void testFileWithPunctuation() {
        Path filePath = Path.of("src/test/resources/special.txt");
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isPresent();
        assertThat(tokens.get()).contains("Hello", "world!", "Java-17", "microservice.");
    }

    @Test
    void testEmptyFile() {
        Path filePath = Path.of("src/test/resources/empty.txt");
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isPresent();
        assertThat(tokens.get()).isEmpty();
    }

    @Test
    void testWhitespaceFile() {
        Path filePath = Path.of("src/test/resources/whitespace.txt");
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isPresent();
        assertThat(tokens.get()).isEmpty();
    }

    @Test
    void testFileNotFound() {
        Path filePath = Path.of("src/test/resources/nonexistent.txt");
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isEmpty();
    }

    @Test
    void testFileWithNumbers() {
        Path filePath = Path.of("src/test/resources/numbers.txt");
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isPresent();
        assertThat(tokens.get()).contains("123", "4567", "test", "Mukund");
    }
}
