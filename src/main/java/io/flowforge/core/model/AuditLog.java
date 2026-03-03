package io.flowforge.core.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity @Table(name = "audit_log")
public class AuditLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "entity_type", nullable = false) private String entityType;
    @Column(name = "entity_id", nullable = false) private UUID entityId;
    @Column(nullable = false) private String action;
    @Column(nullable = false) private String actor;
    @Column(columnDefinition = "jsonb") private String details;
    @Column(name = "ip_address") private String ipAddress;
    @Column(name = "created_at") private OffsetDateTime createdAt;

    @PrePersist protected void onCreate() { createdAt = OffsetDateTime.now(); }

    public Long getId() { return id; }
    public String getEntityType() { return entityType; }
    public void setEntityType(String t) { this.entityType = t; }
    public UUID getEntityId() { return entityId; }
    public void setEntityId(UUID id) { this.entityId = id; }
    public String getAction() { return action; }
    public void setAction(String a) { this.action = a; }
    public String getActor() { return actor; }
    public void setActor(String a) { this.actor = a; }
}
