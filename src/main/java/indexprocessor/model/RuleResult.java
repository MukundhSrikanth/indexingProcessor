package indexprocessor.model;

/**
 * Immutable record representing the result of a business rule.
 *
 * @param <T> the type of the result produced by the rule
 */
public record RuleResult<T>(
        String ruleName,
        T result,
        String description
) {}
