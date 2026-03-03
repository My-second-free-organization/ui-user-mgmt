package io.flowforge.core.engine;

import io.flowforge.core.model.WorkflowInstance;
import io.flowforge.core.model.InstanceStatus;
import io.flowforge.core.repository.WorkflowInstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class WorkflowEngine {
    private static final Logger log = LoggerFactory.getLogger(WorkflowEngine.class);
    private final WorkflowInstanceRepository instanceRepository;
    private final StepExecutor stepExecutor;

    public WorkflowEngine(WorkflowInstanceRepository ir, StepExecutor se) {
        this.instanceRepository = ir; this.stepExecutor = se;
    }

    @Async
    public void execute(WorkflowInstance instance) {
        log.info("Executing workflow instance: {}", instance.getId());
        try {
            stepExecutor.executeSteps(instance);
            instance.setStatus(InstanceStatus.COMPLETED);
        } catch (Exception e) {
            log.error("Workflow instance failed: {}", instance.getId(), e);
            instance.setStatus(InstanceStatus.FAILED);
            instance.setErrorMessage(e.getMessage());
        }
        instanceRepository.save(instance);
    }
}
