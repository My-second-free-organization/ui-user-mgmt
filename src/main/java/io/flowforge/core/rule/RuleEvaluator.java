package io.flowforge.core.rule;

import io.flowforge.core.model.Rule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class RuleEvaluator {
    private static final Logger log = LoggerFactory.getLogger(RuleEvaluator.class);
    private final ObjectMapper objectMapper;

    public RuleEvaluator(ObjectMapper om) { this.objectMapper = om; }

    public boolean evaluate(Rule rule, Map<String, Object> context) {
        try {
            JsonNode cond = objectMapper.readTree(rule.getCondition());
            String op = cond.get("operator").asText();
            String field = cond.get("field").asText();
            Object val = context.get(field);
            if (val == null) return false;
            return switch (op) {
                case "equals" -> val.toString().equals(cond.get("value").asText());
                case "not_equals" -> !val.toString().equals(cond.get("value").asText());
                case "contains" -> val.toString().contains(cond.get("value").asText());
                case "greater_than" -> Double.parseDouble(val.toString()) > cond.get("value").asDouble();
                case "less_than" -> Double.parseDouble(val.toString()) < cond.get("value").asDouble();
                default -> false;
            };
        } catch (Exception e) {
            log.error("Error evaluating rule: {}", rule.getName(), e);
            return false;
        }
    }
}
