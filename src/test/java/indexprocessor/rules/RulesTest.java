package indexprocessor.rules;

import indexprocessor.model.RuleResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RulesTest {

    @Test
    void testStartsWithMRule_normalTokens() {
        StartsWithMRule rule = new StartsWithMRule();
        List<String> tokens = List.of("Mukund", "java", "microservice", "Test");
        RuleResult<Long> result = rule.apply(tokens);

        assertThat(result.result()).isEqualTo(2L);
    }

    @Test
    void testStartsWithMRule_emptyTokens() {
        StartsWithMRule rule = new StartsWithMRule();
        List<String> tokens = List.of();
        RuleResult<Long> result = rule.apply(tokens);

        assertThat(result.result()).isEqualTo(0L);
    }

    @Test
    void testStartsWithMRule_noMatchingTokens() {
        StartsWithMRule rule = new StartsWithMRule();
        List<String> tokens = List.of("java", "python", "hello");
        RuleResult<Long> result = rule.apply(tokens);

        assertThat(result.result()).isEqualTo(0L);
    }

    @Test
    void testLongWordsRule_normalTokens() {
        LongWordsRule rule = new LongWordsRule();
        List<String> tokens = List.of("Mukund", "java", "microservice", "Test");
        RuleResult<List<String>> result = rule.apply(tokens);

        assertThat(result.result()).containsExactly("Mukund", "microservice");
    }

    @Test
    void testLongWordsRule_emptyTokens() {
        LongWordsRule rule = new LongWordsRule();
        List<String> tokens = List.of();
        RuleResult<List<String>> result = rule.apply(tokens);

        assertThat(result.result()).isEmpty();
    }

    @Test
    void testLongWordsRule_noLongWords() {
        LongWordsRule rule = new LongWordsRule();
        List<String> tokens = List.of("hi", "java", "ok");
        RuleResult<List<String>> result = rule.apply(tokens);

        assertThat(result.result()).isEmpty();
    }
}
