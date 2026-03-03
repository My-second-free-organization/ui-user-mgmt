package io.flowforge.core.api;

import io.flowforge.core.model.Workflow;
import io.flowforge.core.model.WorkflowInstance;
import io.flowforge.core.service.WorkflowService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController @RequestMapping("/api/v1/workflows")
public class WorkflowController {
    private final WorkflowService workflowService;
    public WorkflowController(WorkflowService ws) { this.workflowService = ws; }

    @PostMapping
    public ResponseEntity<Workflow> create(@Valid @RequestBody Workflow workflow) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workflowService.createWorkflow(workflow));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workflow> get(@PathVariable UUID id) {
        return ResponseEntity.ok(workflowService.getWorkflow(id));
    }

    @GetMapping
    public ResponseEntity<Page<Workflow>> list(@RequestParam UUID tenantId, Pageable pageable) {
        return ResponseEntity.ok(workflowService.listWorkflows(tenantId, pageable));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<Workflow> activate(@PathVariable UUID id) {
        return ResponseEntity.ok(workflowService.activateWorkflow(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        workflowService.deleteWorkflow(id); return ResponseEntity.noContent().build();
    }
}
