package io.flowforge.core.api;

import io.flowforge.core.model.Task;
import io.flowforge.core.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController @RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;
    public TaskController(TaskService ts) { this.taskService = ts; }

    @GetMapping("/{id}")
    public ResponseEntity<Task> get(@PathVariable UUID id) { return ResponseEntity.ok(taskService.getTask(id)); }

    @GetMapping("/assigned/{assignee}")
    public ResponseEntity<Page<Task>> assigned(@PathVariable String assignee, Pageable pageable) {
        return ResponseEntity.ok(taskService.getAssignedTasks(assignee, pageable));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Task> complete(@PathVariable UUID id, @RequestBody String output) {
        return ResponseEntity.ok(taskService.completeTask(id, output));
    }

    @GetMapping("/instance/{instanceId}")
    public ResponseEntity<List<Task>> byInstance(@PathVariable UUID instanceId) {
        return ResponseEntity.ok(taskService.getTasksByInstance(instanceId));
    }
}
