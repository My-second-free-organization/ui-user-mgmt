package io.flowforge.core.event;

import io.flowforge.core.model.Event;
import io.flowforge.core.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class EventProcessor {
    private static final Logger log = LoggerFactory.getLogger(EventProcessor.class);
    private final EventRepository eventRepository;

    public EventProcessor(EventRepository er) { this.eventRepository = er; }

    @Scheduled(fixedDelay = 500)
    public void processEvents() {
        List<Event> unprocessed = eventRepository.findByProcessedFalseOrderByCreatedAtAsc();
        for (Event event : unprocessed) {
            try {
                log.info("Processing event: {} type={}", event.getId(), event.getType());
                event.setProcessed(true);
                eventRepository.save(event);
            } catch (Exception e) {
                log.error("Failed to process event: {}", event.getId(), e);
            }
        }
    }
}
