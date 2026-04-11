package com.benefits.catalog.model;

import com.benefits.catalog.strategy.CostCalculationStrategy;
import com.benefits.catalog.strategy.DentalCostStrategy;

/**
 * Concrete implementation of BenefitPlan for Dental plans.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Solely responsible for representing a dental benefit plan.
 *
 * GRASP - Information Expert:
 * Owns all dental plan data and delegates cost
 * calculation to DentalCostStrategy.
 *
 * BEHAVIOURAL PATTERN - Strategy (usage):
 * Uses DentalCostStrategy by default.
 */
public class DentalPlan implements BenefitPlan {

    // ── Fields ────────────────────────────────────────────────────────────────

    private final String planId;
    private final String planName;
    private final String coverageDetails;
    private final double coverageLimit;
    private final double baseCost;
    private final String providerName;
    private final String eligibilityCriteria;
    private final int planDurationMonths;

    /** Cost calculation strategy — defaults to DentalCostStrategy. */
    private CostCalculationStrategy costStrategy;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * Constructs a DentalPlan with all required attributes.
     * Assigns DentalCostStrategy as the default cost strategy.
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
    public DentalPlan(String planId,
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

        // Default strategy for dental plans
        this.costStrategy = new DentalCostStrategy();
    }

    // ── Strategy Setter ───────────────────────────────────────────────────────

    /**
     * Allows swapping the cost strategy at runtime.
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
        return PlanType.DENTAL;
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
     * Calculates final cost using the assigned dental strategy.
     *
     * @return Final calculated cost for this dental plan.
     */
    @Override
    public double calculateFinalCost() {
        return costStrategy.calculate(
                baseCost, coverageLimit, planDurationMonths);
    }

    // ── Display ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "DentalPlan {" +
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
