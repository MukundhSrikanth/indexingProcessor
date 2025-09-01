package indexprocessor.core;

import indexprocessor.config.ConfigLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenizerTest {

    private Tokenizer tokenizer;

    @BeforeEach
    void setUp() {
        ConfigLoader config = new ConfigLoader();
        tokenizer = new Tokenizer(config);
    }

    // ------------------- TXT FILE TESTS -------------------

    @Test
    void testTxtFile_NormalContent_TokensCorrect() {
        Path filePath = Path.of("src/test/resources/testFiles/sample.txt"); // Hello world\nJava Mukund micro-services
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isPresent();
        assertThat(tokens.get()).containsExactly("Hello", "world", "Java", "Mukund", "micro-services");
    }

    @Test
    void testTxtFile_WithPunctuation_TokensCorrect() {
        Path filePath = Path.of("src/test/resources/testFiles/special.txt"); // micro-services! Java-17, hello. world?
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isPresent();
        assertThat(tokens.get()).contains("micro-services", "Java-17", "Hello", "world");
    }

    @Test
    void testTxtFile_EmptyFile_TokensEmpty() {
        Path filePath = Path.of("src/test/resources/testFiles/empty.txt");
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);
        assertThat(tokens).isPresent();
        assertThat(tokens.get()).isEmpty();
    }

    @Test
    void testTxtFile_OnlyWhitespace_TokensEmpty() {
        Path filePath = Path.of("src/test/resources/testFiles/whitespace.txt");
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);
        assertThat(tokens).isPresent();
        assertThat(tokens.get()).isEmpty();
    }

    @Test
    void testTxtFile_WithNumbers_TokensCorrect() {
        Path filePath = Path.of("src/test/resources/testFiles/numbers.txt"); // 123 4567 test Mukund
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isPresent();
        assertThat(tokens.get()).containsExactly("123", "4567", "test", "Mukund");
    }

    @Test
    void testTxtFile_LeadingTrailingWhitespace_TokensTrimmed() {
        Path filePath = Path.of("src/test/resources/testFiles/leadingTrailingWhitespace.txt"); // " Hello world "
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isPresent();
        assertThat(tokens.get()).containsExactly("Hello", "world");
    }

    // ------------------- CSV FILE TESTS -------------------

    @Test
    void testCsvFile_NormalContent_TokensCorrect() {
        Path filePath = Path.of("src/test/resources/testFiles/sample.csv"); // Apple,Banana,Cherry,Date
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isPresent();
        assertThat(tokens.get()).containsExactly("Apple", "Banana", "Cherry", "Date");
    }

    @Test
    void testCsvFile_WithEmptyCells_TokensCorrect() {
        Path filePath = Path.of("src/test/resources/testFiles/sampleWithEmpty.csv"); // Apple,Banana,,Cherry
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isPresent();
        assertThat(tokens.get()).containsExactly("Apple", "Banana", "Cherry");
    }

    // ------------------- JSON FILE TESTS -------------------

    @Test
    void testJsonFile_NormalContent_TokensCorrect() {
        Path filePath = Path.of("src/test/resources/testFiles/sample.json"); 
        // {"example": "Standard Generalized Markup", "glossary": "Language GML XML"}
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isPresent();
        assertThat(tokens.get()).containsExactlyInAnyOrder(
                "example", "Standard", "Generalized", "Markup",
                "glossary", "Language", "GML", "XML"
        );
    }

    @Test
    void testJsonFile_WithNestedArray_TokensCorrect() {
        Path filePath = Path.of("src/test/resources/testFiles/nestedArray.json"); 
        // {"numbers": ["one", "two", "three"]}
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isPresent();
        assertThat(tokens.get()).containsExactlyInAnyOrder("numbers", "one", "two", "three");
    }

    @Test
    void testJsonFile_WithEmptyValues_TokensEmpty() {
        Path filePath = Path.of("src/test/resources/testFiles/emptyValues.json"); 
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isPresent();
        assertThat(tokens.get()).isEmpty();
    }

    @Test
    void testJsonFile_WithNestedObjects_TokensCorrect() {
        Path filePath = Path.of("src/test/resources/testFiles/nestedObjects.json"); 
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);

        assertThat(tokens).isPresent();
        assertThat(tokens.get()).containsExactlyInAnyOrder("person", "name", "Alice", "city", "Paris");
    }

    // ------------------- ERROR HANDLING -------------------

    @Test
    void testFile_NotFound_ReturnsEmpty() {
        Path filePath = Path.of("src/test/resources/testFiles/nonexistent.txt");
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);
        assertThat(tokens).isEmpty();
    }

    @Test
    void testFile_UnsupportedFileType_ReturnsEmpty() {
        Path filePath = Path.of("src/test/resources/testFiles/file.unsupported");
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);
        assertThat(tokens).isEmpty();
    }

    @Test
    void testFile_NoExtension_ReturnsEmpty() {
        Path filePath = Path.of("src/test/resources/testFiles/noextensionfile");
        Optional<List<String>> tokens = tokenizer.tokenize(filePath);
        assertThat(tokens).isEmpty();
    }

}
