package io.flowforge.core.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity @Table(name = "rules")
public class Rule {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(nullable = false) private String name;
    private String description;
    @Column(columnDefinition = "jsonb", nullable = false) private String condition;
    @Column(columnDefinition = "jsonb", nullable = false) private String action;
    private int priority;
    private boolean enabled = true;
    @Column(name = "tenant_id", nullable = false) private UUID tenantId;
    @Column(name = "created_at") private OffsetDateTime createdAt;

    @PrePersist protected void onCreate() { createdAt = OffsetDateTime.now(); }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String n) { this.name = n; }
    public String getCondition() { return condition; }
    public void setCondition(String c) { this.condition = c; }
    public String getAction() { return action; }
    public void setAction(String a) { this.action = a; }
    public int getPriority() { return priority; }
    public boolean isEnabled() { return enabled; }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID t) { this.tenantId = t; }
}
