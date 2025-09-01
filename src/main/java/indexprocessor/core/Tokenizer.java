package indexprocessor.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tokenizer class that reads a text file and splits its contents into words (tokens).
 */
public class Tokenizer {

    /**
     * Reads a file and returns a list of words.
     *
     * @param filePath path to the input file
     * @return Optional containing the list of words, empty if file cannot be read
     */
    public Optional<List<String>> tokenize(Path filePath) {
        try (Stream<String> lines = Files.lines(filePath)) {
            List<String> tokens = lines
                    .flatMap(line -> Stream.of(line.split("\\s+"))) // split by whitespace
                    .map(String::trim)
                    .filter(word -> !word.isEmpty())
                    .collect(Collectors.toList());

            return Optional.of(tokens);
        } catch (IOException e) {
            System.err.println("Failed to read file: " + e.getMessage());
            return Optional.empty();
        }
    }
}
