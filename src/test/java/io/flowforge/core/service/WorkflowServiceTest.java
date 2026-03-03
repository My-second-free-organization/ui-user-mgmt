package io.flowforge.core.service;

import io.flowforge.core.model.*;
import io.flowforge.core.repository.WorkflowRepository;
import io.flowforge.core.repository.WorkflowInstanceRepository;
import io.flowforge.core.engine.WorkflowEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkflowServiceTest {
    @Mock WorkflowRepository workflowRepository;
    @Mock WorkflowInstanceRepository instanceRepository;
    @Mock WorkflowEngine workflowEngine;
    @InjectMocks WorkflowService workflowService;

    private Workflow testWorkflow;

    @BeforeEach void setUp() {
        testWorkflow = new Workflow();
        testWorkflow.setId(UUID.randomUUID());
        testWorkflow.setName("Test Workflow");
        testWorkflow.setDefinition("{\"steps\": []}");
        testWorkflow.setCreatedBy("test-user");
        testWorkflow.setTenantId(UUID.randomUUID());
    }

    @Test void createWorkflow_Success() {
        when(workflowRepository.existsByNameAndTenantId(any(), any())).thenReturn(false);
        when(workflowRepository.save(any())).thenReturn(testWorkflow);
        Workflow result = workflowService.createWorkflow(testWorkflow);
        assertNotNull(result);
        assertEquals("Test Workflow", result.getName());
    }

    @Test void createWorkflow_Duplicate_Throws() {
        when(workflowRepository.existsByNameAndTenantId(any(), any())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> workflowService.createWorkflow(testWorkflow));
    }

    @Test void deleteWorkflow_WithRunning_Throws() {
        UUID id = UUID.randomUUID();
        when(instanceRepository.countByWorkflowIdAndStatus(id, InstanceStatus.RUNNING)).thenReturn(5L);
        assertThrows(IllegalStateException.class, () -> workflowService.deleteWorkflow(id));
    }
}
