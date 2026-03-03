package io.flowforge.core.service;

import io.flowforge.core.model.Task;
import io.flowforge.core.model.TaskStatus;
import io.flowforge.core.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service @Transactional
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository tr) { this.taskRepository = tr; }

    public Task getTask(UUID id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found: " + id));
    }

    public Page<Task> getAssignedTasks(String assignee, Pageable pageable) {
        return taskRepository.findByAssigneeAndStatus(assignee, TaskStatus.ASSIGNED, pageable);
    }

    public Task completeTask(UUID taskId, String outputData) {
        Task task = getTask(taskId);
        task.setStatus(TaskStatus.COMPLETED);
        task.setOutputData(outputData);
        return taskRepository.save(task);
    }

    public List<Task> getTasksByInstance(UUID instanceId) {
        return taskRepository.findByInstanceId(instanceId);
    }
}
