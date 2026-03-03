package io.flowforge.core.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "workflows")
public class Workflow {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false) private String name;
    private String description;
    @Column(nullable = false) private int version = 1;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private WorkflowStatus status = WorkflowStatus.DRAFT;
    @JdbcTypeCode(SqlTypes.JSON) @Column(columnDefinition = "jsonb", nullable = false)
    private String definition;
    @Column(name = "created_by", nullable = false) private String createdBy;
    @Column(name = "created_at") private OffsetDateTime createdAt;
    @Column(name = "updated_at") private OffsetDateTime updatedAt;
    @Column(name = "tenant_id", nullable = false) private UUID tenantId;

    @PrePersist protected void onCreate() { createdAt = updatedAt = OffsetDateTime.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = OffsetDateTime.now(); }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
    public int getVersion() { return version; }
    public void setVersion(int v) { this.version = v; }
    public WorkflowStatus getStatus() { return status; }
    public void setStatus(WorkflowStatus s) { this.status = s; }
    public String getDefinition() { return definition; }
    public void setDefinition(String d) { this.definition = d; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String c) { this.createdBy = c; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID t) { this.tenantId = t; }
}
