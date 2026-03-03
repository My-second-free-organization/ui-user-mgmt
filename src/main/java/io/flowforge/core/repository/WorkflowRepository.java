package io.flowforge.core.repository;

import io.flowforge.core.model.Workflow;
import io.flowforge.core.model.WorkflowStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, UUID> {
    Page<Workflow> findByTenantId(UUID tenantId, Pageable pageable);
    List<Workflow> findByTenantIdAndStatus(UUID tenantId, WorkflowStatus status);
    @Query("SELECT w FROM Workflow w WHERE w.tenantId = :tenantId AND w.name LIKE %:name%")
    Page<Workflow> searchByName(UUID tenantId, String name, Pageable pageable);
    boolean existsByNameAndTenantId(String name, UUID tenantId);
}
