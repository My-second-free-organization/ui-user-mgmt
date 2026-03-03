package io.flowforge.core.engine;

import io.flowforge.core.model.WorkflowInstance;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StepExecutor {
    private static final Logger log = LoggerFactory.getLogger(StepExecutor.class);
    private final ObjectMapper objectMapper;

    public StepExecutor(ObjectMapper om) { this.objectMapper = om; }

    public void executeSteps(WorkflowInstance instance) throws Exception {
        JsonNode steps = objectMapper.readTree(instance.getWorkflow().getDefinition()).get("steps");
        if (steps == null || !steps.isArray())
            throw new IllegalArgumentException("Invalid workflow definition: missing steps");
        for (JsonNode step : steps) {
            String type = step.get("type").asText();
            log.debug("Executing step: {} ({})", step.get("name").asText(), type);
            switch (type) {
                case "service_task" -> log.debug("Service task: {}", step.get("name").asText());
                case "user_task" -> log.debug("User task: {}", step.get("name").asText());
                case "gateway" -> log.debug("Gateway: {}", step.get("name").asText());
                case "timer" -> log.debug("Timer: {}", step.get("name").asText());
                default -> throw new IllegalArgumentException("Unknown step type: " + type);
            }
        }
    }
}
