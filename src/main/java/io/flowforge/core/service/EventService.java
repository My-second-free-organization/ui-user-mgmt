package io.flowforge.core.service;

import io.flowforge.core.model.Event;
import io.flowforge.core.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service @Transactional
public class EventService {
    private static final Logger log = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EventService(EventRepository er, KafkaTemplate<String, Object> kt) {
        this.eventRepository = er; this.kafkaTemplate = kt;
    }

    public Event publishEvent(String type, String source, String payload, String correlationId) {
        Event event = new Event();
        event.setType(type); event.setSource(source);
        event.setPayload(payload); event.setCorrelationId(correlationId);
        event = eventRepository.save(event);
        kafkaTemplate.send("flowforge.events." + type, correlationId, event);
        return event;
    }
}
