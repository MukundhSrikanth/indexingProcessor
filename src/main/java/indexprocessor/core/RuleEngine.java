package indexprocessor.core;

import indexprocessor.model.RuleResult;
import indexprocessor.rules.BusinessRule;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RuleEngine applies a list of BusinessRule instances to a list of tokens.
 */
public class RuleEngine {

    /**
     * Applies all provided rules to the tokens.
     *
     * @param tokens the list of words to process
     * @param rules  the business rules to apply
     * @return a list of RuleResult objects for each rule
     */
    public List<RuleResult<?>> applyRules(List<String> tokens, List<BusinessRule<?>> rules) {
        return rules.stream()
                .map(rule -> rule.apply(tokens))
                .collect(Collectors.toList());
    }
}
