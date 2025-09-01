package indexprocessor.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import indexprocessor.config.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tokenizer class that reads a file and splits its contents into tokens.
 * Supports TXT, CSV, and JSON files.
 */
public class Tokenizer {

    private static final Logger logger = LoggerFactory.getLogger(Tokenizer.class);
    private final ConfigLoader config;

    public Tokenizer(ConfigLoader config) {
        this.config = config;
    }

    public Optional<List<String>> tokenize(Path filePath) {
        if (!Files.exists(filePath)) {
            logger.error("File not found: " + filePath);
            return Optional.empty();
        }

        Optional<String> fileTypeOpt = getFileType(filePath);
        if (fileTypeOpt.isEmpty()) return Optional.empty();

        String fileType = fileTypeOpt.get();

        try {
            switch (fileType) {
                case "txt":
                    return Optional.of(tokenizeText(filePath)); // TXT logic unchanged
                case "csv":
                    return Optional.of(tokenizeCsv(filePath, config.getDelimiter("csv"))); // CSV fix
                case "json":
                    return Optional.of(tokenizeJson(filePath)); // JSON with keys + nested + empty filtering
                default:
                    logger.error("Unsupported file type: " + fileType);
                    return Optional.empty();
            }
        } catch (IOException e) {
            logger.error("Failed to read file: " + e.getMessage(), e);
            return Optional.empty();
        }
    }

    private Optional<String> getFileType(Path filePath) {
        String fileName = filePath.getFileName().toString();
        int idx = fileName.lastIndexOf('.');
        if (idx > 0 && idx < fileName.length() - 1) {
            String ext = fileName.substring(idx + 1).toLowerCase();
            if (config.getAllowedFileTypes().contains(ext)) {
                return Optional.of(ext);
            } else {
                logger.error("Unsupported file type: " + ext);
            }
        } else {
            logger.error("File has no extension: " + fileName);
        }
        return Optional.empty();
    }

    // ---------------- TXT ----------------
    private List<String> tokenizeText(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines
                    .flatMap(line -> Stream.of(line.split("\\s+"))) // split on any whitespace
                    .map(this::cleanToken)
                    .filter(word -> !word.isEmpty())
                    .collect(Collectors.toList());
        }
    }
    
    // ---------------- CSV ----------------
    private List<String> tokenizeCsv(Path filePath, String delimiter) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines
                    .flatMap(line -> Arrays.stream(line.split(delimiter, -1))) // keep empty cells
                    .map(String::trim)
                    .filter(word -> !word.isEmpty())
                    .map(this::cleanToken)
                    .filter(word -> !word.isEmpty())
                    .collect(Collectors.toList());
        }
    }

    // ---------------- JSON ----------------
    private List<String> tokenizeJson(Path filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(filePath.toFile());
        List<String> tokens = new ArrayList<>();
        extractStrings(root, tokens);
        return tokens;
    }

    private void extractStrings(JsonNode node, List<String> tokens) {
        if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                JsonNode valueNode = entry.getValue();
                if (!isEmptyNode(valueNode)) {
                    String keyToken = cleanToken(entry.getKey());
                    if (!keyToken.isEmpty()) tokens.add(keyToken);
                    extractStrings(valueNode, tokens);
                }
            });
        } else if (node.isArray()) {
            node.forEach(element -> extractStrings(element, tokens));
        } else if (node.isTextual()) {
            String[] words = node.asText().split("\\s+");
            for (String word : words) {
                word = cleanToken(word);
                if (!word.isEmpty()) tokens.add(word);
            }
        }
    }

    private boolean isEmptyNode(JsonNode node) {
        return (node.isTextual() && node.asText().isBlank())
                || (node.isArray() && node.size() == 0)
                || (node.isObject() && node.size() == 0)
                || node.isNull();
    }

    // ---------------- CLEAN TOKEN ----------------
    /**
     * Remove all punctuation except hyphens and trim whitespace.
     * Example: "micro-services!" -> "micro-services"
     */
    private String cleanToken(String token) {
        if (token == null) return "";
        return token.replaceAll("[\\p{Punct}&&[^-]]+", "").trim();
    }
}
