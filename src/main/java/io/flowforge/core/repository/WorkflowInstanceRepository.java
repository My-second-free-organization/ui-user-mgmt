package io.flowforge.core.repository;

import io.flowforge.core.model.InstanceStatus;
import io.flowforge.core.model.WorkflowInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface WorkflowInstanceRepository extends JpaRepository<WorkflowInstance, UUID> {
    Page<WorkflowInstance> findByWorkflowId(UUID workflowId, Pageable pageable);
    List<WorkflowInstance> findByStatus(InstanceStatus status);
    long countByWorkflowIdAndStatus(UUID workflowId, InstanceStatus status);
}
