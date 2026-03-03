package io.flowforge.core.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventListener {
    private static final Logger log = LoggerFactory.getLogger(KafkaEventListener.class);

    @KafkaListener(topics = "flowforge.events.workflow", groupId = "platform-core")
    public void handleWorkflowEvent(String message) { log.info("Workflow event: {}", message); }

    @KafkaListener(topics = "flowforge.events.task", groupId = "platform-core")
    public void handleTaskEvent(String message) { log.info("Task event: {}", message); }
}
