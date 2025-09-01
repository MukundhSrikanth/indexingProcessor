package indexprocessor.rules;

import indexprocessor.model.RuleResult;
import java.util.List;

/**
 * Generic interface for a business rule that processes a list of tokens.
 *
 * @param <T> the type of result produced by the rule
 */
public interface BusinessRule<T> {

    /**
     * Applies the rule to the given list of tokens.
     *
     * @param tokens the list of words or tokens to process
     * @return a RuleResult containing the outcome of this rule
     */
    RuleResult<T> apply(List<String> tokens);
}
