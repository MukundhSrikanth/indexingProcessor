package indexprocessor;

import indexprocessor.core.Tokenizer;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenizerTest {

    @Test
    void testTokenizeFile() {
        Tokenizer tokenizer = new Tokenizer();
        Path filePath = Path.of("src/test/resources/sample.txt");

        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isPresent();
        assertThat(tokens.get()).contains("Hello", "world", "Java", "Mukund", "microservice");
    }
}
