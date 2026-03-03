package io.flowforge.core.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity @Table(name = "workflow_instances")
public class WorkflowInstance {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "workflow_id", nullable = false)
    private Workflow workflow;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private InstanceStatus status = InstanceStatus.RUNNING;
    @Column(columnDefinition = "jsonb") private String variables;
    @Column(name = "started_at") private OffsetDateTime startedAt;
    @Column(name = "completed_at") private OffsetDateTime completedAt;
    @Column(name = "error_message") private String errorMessage;
    @Column(name = "correlation_id") private String correlationId;
    @Column(name = "tenant_id", nullable = false) private UUID tenantId;

    @PrePersist protected void onCreate() { startedAt = OffsetDateTime.now(); }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Workflow getWorkflow() { return workflow; }
    public void setWorkflow(Workflow w) { this.workflow = w; }
    public InstanceStatus getStatus() { return status; }
    public void setStatus(InstanceStatus s) { this.status = s; }
    public String getVariables() { return variables; }
    public void setVariables(String v) { this.variables = v; }
    public OffsetDateTime getStartedAt() { return startedAt; }
    public OffsetDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(OffsetDateTime c) { this.completedAt = c; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String e) { this.errorMessage = e; }
    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String c) { this.correlationId = c; }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID t) { this.tenantId = t; }
}
