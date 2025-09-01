package indexprocessor.rules;

import indexprocessor.model.RuleResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Returns all words longer than 5 characters.
 */
public class LongWordsRule implements BusinessRule<List<String>> {

    @Override
    public RuleResult<List<String>> apply(List<String> tokens) {
        List<String> longWords = tokens.stream()
                .filter(word -> word.length() > 5)
                .collect(Collectors.toList());

        return new RuleResult<>(
                "LongWordsRule",
                longWords,
                "Words longer than 5 characters"
        );
    }
}
