package indexprocessor;

import indexprocessor.core.RuleEngine;
import indexprocessor.core.Tokenizer;
import indexprocessor.model.RuleResult;
import indexprocessor.rules.BusinessRule;
import indexprocessor.rules.LongWordsRule;
import indexprocessor.rules.StartsWithMRule;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Main class for the indexing processor application.
 */
public class IndexingProcessorApp {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Please provide the path to the input file as an argument.");
            return;
        }

        Path filePath = Path.of(args[0]);
        Tokenizer tokenizer = new Tokenizer();
        Optional<List<String>> optionalTokens = tokenizer.tokenize(filePath);

        optionalTokens
            .filter(tokens -> !tokens.isEmpty())
            .ifPresentOrElse(tokens -> {
                
                List<BusinessRule<?>> rules = List.of(
                        new StartsWithMRule(),
                        new LongWordsRule()
                );

                RuleEngine engine = new RuleEngine();
                List<RuleResult<?>> results = engine.applyRules(tokens, rules);

                results.forEach(result -> {
                    System.out.println("Rule: " + result.ruleName());
                    System.out.println("Description: " + result.description());
                    System.out.println("Result: " + result.result());
                    System.out.println("--------------------------------------------------");
                });

            }, () -> System.out.println("No tokens could be extracted from the file."));
    }
}
