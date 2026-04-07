package main.java.com.benefits.catalog.dao;

import main.java.com.benefits.catalog.model.BenefitPlan;
import java.util.Collection;

/**
 * DAO Interface for BenefitPlan
 */
public interface BenefitPlanDAO {

    void save(BenefitPlan plan);

    BenefitPlan findById(String planId);

    Collection<BenefitPlan> findAll();

    Collection<BenefitPlan> findByType(BenefitPlan.PlanType planType);

    void update(BenefitPlan plan);

    void delete(String planId);

    boolean existsById(String planId);
}