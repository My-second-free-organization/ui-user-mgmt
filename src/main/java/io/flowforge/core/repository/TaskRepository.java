package io.flowforge.core.repository;

import io.flowforge.core.model.Task;
import io.flowforge.core.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByInstanceId(UUID instanceId);
    Page<Task> findByAssigneeAndStatus(String assignee, TaskStatus status, Pageable pageable);
    long countByAssigneeAndStatus(String assignee, TaskStatus status);
}
