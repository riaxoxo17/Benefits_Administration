package com.benefits.catalog.model;

import com.benefits.catalog.strategy.CostCalculationStrategy;
import com.benefits.catalog.strategy.HealthCostStrategy;

/**
 * Concrete implementation of BenefitPlan for Health plans.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This class is solely responsible for representing
 * a health benefit plan and its attributes.
 *
 * SOLID - Open/Closed Principle (OCP):
 * This class is closed for modification. New health
 * plan variations can extend this class without
 * changing its core implementation.
 *
 * GRASP - Information Expert:
 * Owns and manages all health plan specific data.
 * Delegates cost calculation to its strategy.
 *
 * BEHAVIOURAL PATTERN - Strategy (usage):
 * Uses HealthCostStrategy by default but accepts any
 * CostCalculationStrategy — making it flexible and
 * testable with mock strategies.
 */
public class HealthPlan implements BenefitPlan {

    // ── Fields ────────────────────────────────────────────────────────────────

    private final String planId;
    private final String planName;
    private final String coverageDetails;
    private final double coverageLimit;
    private final double baseCost;
    private final String providerName;
    private final String eligibilityCriteria;
    private final int planDurationMonths;

    /**
     * The cost calculation strategy assigned to this plan.
     * Defaults to HealthCostStrategy but can be swapped.
     */
    private CostCalculationStrategy costStrategy;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * Constructs a HealthPlan with all required attributes.
     * Assigns HealthCostStrategy as the default cost strategy.
     *
     * @param planId              Unique plan identifier.
     * @param planName            Display name of the plan.
     * @param coverageDetails     Description of what is covered.
     * @param coverageLimit       Maximum coverage amount.
     * @param baseCost            Base premium cost.
     * @param providerName        Name of the insurance provider.
     * @param eligibilityCriteria Criteria employees must meet.
     * @param planDurationMonths  Duration of the plan in months.
     */
    public HealthPlan(String planId,
            String planName,
            String coverageDetails,
            double coverageLimit,
            double baseCost,
            String providerName,
            String eligibilityCriteria,
            int planDurationMonths) {

        this.planId = planId;
        this.planName = planName;
        this.coverageDetails = coverageDetails;
        this.coverageLimit = coverageLimit;
        this.baseCost = baseCost;
        this.providerName = providerName;
        this.eligibilityCriteria = eligibilityCriteria;
        this.planDurationMonths = planDurationMonths;

        // Default strategy for health plans
        this.costStrategy = new HealthCostStrategy();
    }

    // ── Strategy Setter ───────────────────────────────────────────────────────

    /**
     * Allows swapping the cost calculation strategy at runtime.
     * Useful for testing or applying promotional pricing.
     *
     * @param costStrategy The new strategy to apply.
     */
    public void setCostStrategy(CostCalculationStrategy costStrategy) {
        this.costStrategy = costStrategy;
    }

    // ── BenefitPlan Interface Implementation ──────────────────────────────────

    @Override
    public String getPlanId() {
        return planId;
    }

    @Override
    public String getPlanName() {
        return planName;
    }

    @Override
    public PlanType getPlanType() {
        return PlanType.HEALTH;
    }

    @Override
    public String getCoverageDetails() {
        return coverageDetails;
    }

    @Override
    public double getCoverageLimit() {
        return coverageLimit;
    }

    @Override
    public double getBaseCost() {
        return baseCost;
    }

    @Override
    public String getProviderName() {
        return providerName;
    }

    @Override
    public String getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    @Override
    public int getPlanDurationMonths() {
        return planDurationMonths;
    }

    /**
     * Calculates the final cost using the assigned strategy.
     * Delegates entirely to the strategy — no cost logic here.
     *
     * @return Final calculated cost for this health plan.
     */
    @Override
    public double calculateFinalCost() {
        return costStrategy.calculate(
                baseCost, coverageLimit, planDurationMonths);
    }

    // ── Display ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "HealthPlan {" +
                "\n  Plan ID          : " + planId +
                "\n  Name             : " + planName +
                "\n  Type             : " + getPlanType() +
                "\n  Coverage         : " + coverageDetails +
                "\n  Coverage Limit   : " + coverageLimit +
                "\n  Base Cost        : " + baseCost +
                "\n  Provider         : " + providerName +
                "\n  Eligibility      : " + eligibilityCriteria +
                "\n  Duration (months): " + planDurationMonths +
                "\n}";
    }
}
