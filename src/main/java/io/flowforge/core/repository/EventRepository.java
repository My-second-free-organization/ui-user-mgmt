package io.flowforge.core.repository;

import io.flowforge.core.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findByProcessedFalseOrderByCreatedAtAsc();
    List<Event> findByCorrelationId(String correlationId);
    List<Event> findByType(String type);
}
