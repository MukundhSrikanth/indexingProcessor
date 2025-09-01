package indexprocessor;

import indexprocessor.config.ConfigLoader;
import indexprocessor.core.RuleEngine;
import indexprocessor.core.Tokenizer;
import indexprocessor.model.RuleResult;
import indexprocessor.rules.BusinessRule;
import indexprocessor.rules.LongWordsRule;
import indexprocessor.rules.StartsWithMRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Main class for the indexing processor application.
 */
public class IndexingProcessorApp {

    private static final Logger logger = LoggerFactory.getLogger(IndexingProcessorApp.class);

    public static void main(String[] args) {

        if (args.length == 0) {
            logger.error("Please provide the path to the input file as an argument.");
            return;
        }

        Path filePath = Path.of(args[0]);

        // Load configuration
        ConfigLoader config = new ConfigLoader();

        // Initialize tokenizer with configuration
        Tokenizer tokenizer = new Tokenizer(config);

        // Tokenize file
        Optional<List<String>> optionalTokens = tokenizer.tokenize(filePath);

        optionalTokens
            .filter(tokens -> !tokens.isEmpty())
            .ifPresentOrElse(tokens -> {

                // Define rules
                List<BusinessRule<?>> rules = List.of(
                        new StartsWithMRule(),
                        new LongWordsRule()
                );

                // Apply rules
                RuleEngine engine = new RuleEngine();
                List<RuleResult<?>> results = engine.applyRules(tokens, rules);

                // Print results
                results.forEach(result -> {
                    System.out.println("Rule: " + result.ruleName());
                    System.out.println("Description: " + result.description());
                    System.out.println("Result: " + result.result());
                    System.out.println("--------------------------------------------------");
                });

            }, () -> logger.warn("No tokens could be extracted from the file."));
    }
}
