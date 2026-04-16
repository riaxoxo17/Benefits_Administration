package com.benefits.catalog.dao;

import com.benefits.catalog.model.BenefitPlan;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BenefitPlanDAOImpl implements BenefitPlanDAO {

    private final Map<String, BenefitPlan> planStore = new HashMap<>();

    @Override
    public void save(BenefitPlan plan) {
        planStore.put(plan.getPlanId(), plan);
        System.out.println("[DAO MOCK] Saved plan: " + plan.getPlanId());
    }

    @Override
    public BenefitPlan findById(String planId) {
        return planStore.get(planId);
    }

    @Override
    public Collection<BenefitPlan> findAll() {
        return planStore.values();
    }

    @Override
    public Collection<BenefitPlan> findByType(BenefitPlan.PlanType planType) {
        return planStore.values()
                .stream()
                .filter(p -> p.getPlanType() == planType)
                .collect(Collectors.toList());
    }

    @Override
    public void update(BenefitPlan plan) {
        planStore.put(plan.getPlanId(), plan);
    }

    @Override
    public void delete(String planId) {
        planStore.remove(planId);
    }

    @Override
    public boolean existsById(String planId) {
        return planStore.containsKey(planId);
    }
}