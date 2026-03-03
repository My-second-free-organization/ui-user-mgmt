package io.flowforge.core.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity @Table(name = "events")
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(nullable = false) private String type;
    @Column(nullable = false) private String source;
    @Column(columnDefinition = "jsonb", nullable = false) private String payload;
    @Column(name = "correlation_id") private String correlationId;
    @Column(name = "created_at") private OffsetDateTime createdAt;
    private boolean processed;

    @PrePersist protected void onCreate() { createdAt = OffsetDateTime.now(); }

    public UUID getId() { return id; }
    public String getType() { return type; }
    public void setType(String t) { this.type = t; }
    public String getSource() { return source; }
    public void setSource(String s) { this.source = s; }
    public String getPayload() { return payload; }
    public void setPayload(String p) { this.payload = p; }
    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String c) { this.correlationId = c; }
    public boolean isProcessed() { return processed; }
    public void setProcessed(boolean p) { this.processed = p; }
}
