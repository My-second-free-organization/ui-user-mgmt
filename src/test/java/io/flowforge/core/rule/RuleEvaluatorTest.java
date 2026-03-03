package io.flowforge.core.rule;

import io.flowforge.core.model.Rule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class RuleEvaluatorTest {
    private final RuleEvaluator evaluator = new RuleEvaluator(new ObjectMapper());

    @Test void equalsCondition_True() {
        Rule r = rule("{\"operator\":\"equals\",\"field\":\"status\",\"value\":\"active\"}");
        assertTrue(evaluator.evaluate(r, Map.of("status", "active")));
    }

    @Test void equalsCondition_False() {
        Rule r = rule("{\"operator\":\"equals\",\"field\":\"status\",\"value\":\"active\"}");
        assertFalse(evaluator.evaluate(r, Map.of("status", "inactive")));
    }

    @Test void greaterThan() {
        Rule r = rule("{\"operator\":\"greater_than\",\"field\":\"amount\",\"value\":100}");
        assertTrue(evaluator.evaluate(r, Map.of("amount", 150)));
        assertFalse(evaluator.evaluate(r, Map.of("amount", 50)));
    }

    private Rule rule(String condition) {
        Rule r = new Rule(); r.setName("Test"); r.setCondition(condition);
        r.setAction("{\"type\":\"notify\"}"); r.setTenantId(UUID.randomUUID());
        return r;
    }
}
