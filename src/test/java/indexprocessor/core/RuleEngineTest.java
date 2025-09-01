package indexprocessor.core;

import indexprocessor.model.RuleResult;
import indexprocessor.rules.BusinessRule;
import indexprocessor.rules.LongWordsRule;
import indexprocessor.rules.StartsWithMRule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for RuleEngine.
 */
public class RuleEngineTest {

    @Test
    void testApplyRules() {
        // Sample tokens
        List<String> tokens = List.of("Mukund", "java", "microservice", "Test");

        // List of business rules
        List<BusinessRule<?>> rules = List.of(
                new StartsWithMRule(),
                new LongWordsRule()
        );

        // Apply rules using RuleEngine
        RuleEngine engine = new RuleEngine();
        List<RuleResult<?>> results = engine.applyRules(tokens, rules);

        // Assertions
        assertThat(results).hasSize(2);

        // Validate StartsWithMRule result
        RuleResult<?> startsWithMResult = results.stream()
                .filter(r -> r.ruleName().equals("StartsWithMRule"))
                .findFirst()
                .orElseThrow();
        assertThat(startsWithMResult.description()).isEqualTo("Counts words starting with M or m");
        assertThat(startsWithMResult.result()).isEqualTo(2L);

        // Validate LongWordsRule result
        RuleResult<?> longWordsResult = results.stream()
                .filter(r -> r.ruleName().equals("LongWordsRule"))
                .findFirst()
                .orElseThrow();
        assertThat(longWordsResult.description()).isEqualTo("Words longer than 5 characters");
        assertThat(longWordsResult.result()).asList().containsExactly("Mukund", "microservice");
    }

    @Test
    void testStartsWithMRule_withEmptyStrings() {
        StartsWithMRule rule = new StartsWithMRule();
        List<String> tokens = List.of("", "Mukund", "", "microservice", ""); 
        RuleResult<Long> result = rule.apply(tokens);

    // Should count only "Mukund" and "microservice"
    assertThat(result.result()).isEqualTo(2L);
    }

}