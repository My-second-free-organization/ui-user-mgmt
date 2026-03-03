package io.flowforge.core.service;

import io.flowforge.core.model.Rule;
import io.flowforge.core.repository.RuleRepository;
import io.flowforge.core.rule.RuleEvaluator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service @Transactional
public class RuleService {
    private final RuleRepository ruleRepository;
    private final RuleEvaluator ruleEvaluator;

    public RuleService(RuleRepository rr, RuleEvaluator re) {
        this.ruleRepository = rr; this.ruleEvaluator = re;
    }

    public Rule createRule(Rule rule) { return ruleRepository.save(rule); }

    public List<Rule> evaluateRules(UUID tenantId, Map<String, Object> context) {
        return ruleRepository.findByTenantIdAndEnabledTrueOrderByPriorityDesc(tenantId).stream()
            .filter(r -> ruleEvaluator.evaluate(r, context)).toList();
    }
}
