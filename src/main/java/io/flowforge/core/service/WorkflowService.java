package io.flowforge.core.service;

import io.flowforge.core.model.*;
import io.flowforge.core.repository.WorkflowRepository;
import io.flowforge.core.repository.WorkflowInstanceRepository;
import io.flowforge.core.engine.WorkflowEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service @Transactional
public class WorkflowService {
    private static final Logger log = LoggerFactory.getLogger(WorkflowService.class);
    private final WorkflowRepository workflowRepository;
    private final WorkflowInstanceRepository instanceRepository;
    private final WorkflowEngine workflowEngine;

    public WorkflowService(WorkflowRepository wr, WorkflowInstanceRepository ir, WorkflowEngine we) {
        this.workflowRepository = wr; this.instanceRepository = ir; this.workflowEngine = we;
    }

    public Workflow createWorkflow(Workflow workflow) {
        log.info("Creating workflow: {} for tenant: {}", workflow.getName(), workflow.getTenantId());
        if (workflowRepository.existsByNameAndTenantId(workflow.getName(), workflow.getTenantId()))
            throw new IllegalArgumentException("Workflow '" + workflow.getName() + "' already exists");
        return workflowRepository.save(workflow);
    }

    public Workflow getWorkflow(UUID id) {
        return workflowRepository.findById(id).orElseThrow(() -> new RuntimeException("Workflow not found: " + id));
    }

    public Page<Workflow> listWorkflows(UUID tenantId, Pageable pageable) {
        return workflowRepository.findByTenantId(tenantId, pageable);
    }

    public WorkflowInstance startWorkflow(UUID workflowId, String variables, String correlationId) {
        Workflow workflow = getWorkflow(workflowId);
        if (workflow.getStatus() != WorkflowStatus.ACTIVE)
            throw new IllegalStateException("Cannot start inactive workflow: " + workflowId);
        WorkflowInstance instance = new WorkflowInstance();
        instance.setWorkflow(workflow);
        instance.setVariables(variables);
        instance.setCorrelationId(correlationId);
        instance.setTenantId(workflow.getTenantId());
        instance = instanceRepository.save(instance);
        workflowEngine.execute(instance);
        return instance;
    }

    public Workflow activateWorkflow(UUID id) {
        Workflow w = getWorkflow(id); w.setStatus(WorkflowStatus.ACTIVE);
        return workflowRepository.save(w);
    }

    public void deleteWorkflow(UUID id) {
        if (instanceRepository.countByWorkflowIdAndStatus(id, InstanceStatus.RUNNING) > 0)
            throw new IllegalStateException("Cannot delete workflow with running instances");
        workflowRepository.deleteById(id);
    }
}
