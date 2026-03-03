package io.flowforge.core.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean public NewTopic workflowTopic() { return TopicBuilder.name("flowforge.events.workflow").partitions(6).replicas(3).build(); }
    @Bean public NewTopic taskTopic() { return TopicBuilder.name("flowforge.events.task").partitions(6).replicas(3).build(); }
    @Bean public NewTopic integrationTopic() { return TopicBuilder.name("flowforge.events.integration").partitions(6).replicas(3).build(); }
}
