package io.flowforge.core.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity @Table(name = "tasks")
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "instance_id", nullable = false)
    private WorkflowInstance instance;
    @Column(nullable = false) private String name;
    @Column(nullable = false) private String type;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private TaskStatus status = TaskStatus.PENDING;
    private String assignee;
    @Column(name = "input_data", columnDefinition = "jsonb") private String inputData;
    @Column(name = "output_data", columnDefinition = "jsonb") private String outputData;
    @Column(name = "created_at") private OffsetDateTime createdAt;
    @Column(name = "completed_at") private OffsetDateTime completedAt;
    @Column(name = "due_date") private OffsetDateTime dueDate;
    private int priority;

    @PrePersist protected void onCreate() { createdAt = OffsetDateTime.now(); }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public WorkflowInstance getInstance() { return instance; }
    public void setInstance(WorkflowInstance i) { this.instance = i; }
    public String getName() { return name; }
    public void setName(String n) { this.name = n; }
    public String getType() { return type; }
    public void setType(String t) { this.type = t; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus s) { this.status = s; }
    public String getAssignee() { return assignee; }
    public void setAssignee(String a) { this.assignee = a; }
    public String getInputData() { return inputData; }
    public void setInputData(String d) { this.inputData = d; }
    public String getOutputData() { return outputData; }
    public void setOutputData(String d) { this.outputData = d; }
    public int getPriority() { return priority; }
    public void setPriority(int p) { this.priority = p; }
}
