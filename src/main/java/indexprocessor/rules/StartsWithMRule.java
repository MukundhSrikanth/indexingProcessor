package indexprocessor.rules;

import indexprocessor.model.RuleResult;

import java.util.List;

/**
 * Counts the number of words that start with "M" or "m".
 */
public class StartsWithMRule implements BusinessRule<Long> {

    @Override
    public RuleResult<Long> apply(List<String> tokens) {
        long count = tokens.stream()
                .filter(word -> !word.isEmpty())
                .filter(word -> word.toLowerCase().startsWith("m"))
                .count();

        return new RuleResult<>(
                "StartsWithMRule",
                count,
                "Counts words starting with M or m"
        );
    }
}
