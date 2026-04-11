package com.benefits.catalog.factory;

import com.benefits.catalog.model.BenefitPlan;
import com.benefits.catalog.model.DentalPlan;
import com.benefits.catalog.model.HealthPlan;
import com.benefits.catalog.model.InsurancePlan;
import com.benefits.catalog.model.RetirementPlan;

/**
 * Concrete implementation of BenefitPlanFactory.
 *
 * CREATIONAL PATTERN - Factory Method (Concrete Factory):
 * This class is the single place in the entire system
 * where BenefitPlan objects are instantiated. It reads
 * the requested plan type and constructs the correct
 * concrete class with the correct default strategy.
 *
 * No other class in the system should ever call
 * "new HealthPlan(...)" or "new DentalPlan(...)" directly.
 * All plan creation flows through here.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This class has exactly one job — create BenefitPlan
 * objects based on the requested type. Nothing else.
 *
 * SOLID - Open/Closed Principle (OCP):
 * To add a new plan type (e.g., VisionPlan):
 * 1. Create VisionPlan.java implementing BenefitPlan
 * 2. Add VISION to the PlanType enum
 * 3. Add a case here in the switch statement
 * Zero changes needed anywhere else in the system.
 *
 * GRASP - Creator:
 * Centralized creation ensures every plan is built
 * consistently with the right strategy assigned.
 *
 * GRASP - Low Coupling:
 * The rest of the system never imports concrete plan
 * classes directly — only this factory does.
 */
public class BenefitPlanFactoryImpl implements BenefitPlanFactory {

    /**
     * Creates and returns the correct BenefitPlan subclass
     * based on the requested plan type.
     *
     * Each case constructs the appropriate concrete plan
     * with its matching default cost strategy already set.
     *
     * @param planType            The type of plan to create.
     * @param planId              Unique identifier for the plan.
     * @param planName            Display name of the plan.
     * @param coverageDetails     Description of coverage.
     * @param coverageLimit       Maximum coverage amount.
     * @param baseCost            Base premium cost.
     * @param providerName        Name of the plan provider.
     * @param eligibilityCriteria Eligibility criteria for the plan.
     * @param planDurationMonths  Duration of the plan in months.
     * @return A fully constructed BenefitPlan instance.
     * @throws IllegalArgumentException if the plan type is unknown.
     */
    @Override
    public BenefitPlan createPlan(
            BenefitPlan.PlanType planType,
            String planId,
            String planName,
            String coverageDetails,
            double coverageLimit,
            double baseCost,
            String providerName,
            String eligibilityCriteria,
            int planDurationMonths) {

        System.out.println("[PlanFactory] Creating plan of type: "
                + planType + " | ID: " + planId);

        // ── Route to correct concrete plan class ─────────────────────────────
        // Each case instantiates the right plan type with its
        // default cost strategy already wired in the constructor.

        switch (planType) {

            case HEALTH:
                // HealthPlan with HealthCostStrategy (risk multiplier + coverage)
                return new HealthPlan(
                        planId, planName, coverageDetails,
                        coverageLimit, baseCost, providerName,
                        eligibilityCriteria, planDurationMonths);

            case DENTAL:
                // DentalPlan with DentalCostStrategy (risk multiplier + flat fee)
                return new DentalPlan(
                        planId, planName, coverageDetails,
                        coverageLimit, baseCost, providerName,
                        eligibilityCriteria, planDurationMonths);

            case RETIREMENT:
                // RetirementPlan with RetirementCostStrategy (duration-based)
                return new RetirementPlan(
                        planId, planName, coverageDetails,
                        coverageLimit, baseCost, providerName,
                        eligibilityCriteria, planDurationMonths);

            case INSURANCE:
                // InsurancePlan with InsuranceCostStrategy (risk + coverage factor)
                return new InsurancePlan(
                        planId, planName, coverageDetails,
                        coverageLimit, baseCost, providerName,
                        eligibilityCriteria, planDurationMonths);

            default:
                // Guard against unknown plan types being passed in
                throw new IllegalArgumentException(
                        "[PlanFactory] ERROR: Unknown plan type → " + planType);
        }
    }
}
