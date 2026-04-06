package main.java.com.benefits.catalog.model;

import main.java.com.benefits.catalog.strategy.CostCalculationStrategy;
import main.java.com.benefits.catalog.strategy.RetirementCostStrategy;

/**
 * Concrete implementation of BenefitPlan for Retirement plans.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Solely responsible for representing a retirement benefit plan.
 *
 * GRASP - Information Expert:
 * Owns all retirement plan data and delegates cost
 * calculation to RetirementCostStrategy.
 *
 * BEHAVIOURAL PATTERN - Strategy (usage):
 * Uses RetirementCostStrategy by default.
 */
public class RetirementPlan implements BenefitPlan {

    // ── Fields ────────────────────────────────────────────────────────────────

    private final String planId;
    private final String planName;
    private final String coverageDetails;
    private final double coverageLimit;
    private final double baseCost;
    private final String providerName;
    private final String eligibilityCriteria;
    private final int planDurationMonths;

    /** Cost calculation strategy — defaults to RetirementCostStrategy. */
    private CostCalculationStrategy costStrategy;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * Constructs a RetirementPlan with all required attributes.
     * Assigns RetirementCostStrategy as the default cost strategy.
     *
     * @param planId              Unique plan identifier.
     * @param planName            Display name of the plan.
     * @param coverageDetails     Description of what is covered.
     * @param coverageLimit       Maximum coverage amount.
     * @param baseCost            Base premium cost.
     * @param providerName        Name of the plan provider.
     * @param eligibilityCriteria Criteria employees must meet.
     * @param planDurationMonths  Duration of the plan in months.
     */
    public RetirementPlan(String planId,
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

        // Default strategy for retirement plans
        this.costStrategy = new RetirementCostStrategy();
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
        return PlanType.RETIREMENT;
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
     * Calculates final cost using the assigned retirement strategy.
     * Duration is the key factor in retirement plan pricing.
     *
     * @return Final calculated cost for this retirement plan.
     */
    @Override
    public double calculateFinalCost() {
        return costStrategy.calculate(
                baseCost, coverageLimit, planDurationMonths);
    }

    // ── Display ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "RetirementPlan {" +
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
