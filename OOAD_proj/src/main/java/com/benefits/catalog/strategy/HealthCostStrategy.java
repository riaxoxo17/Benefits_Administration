package main.java.com.benefits.catalog.strategy;

/**
 * Cost calculation strategy for Health benefit plans.
 *
 * BEHAVIOURAL PATTERN - Strategy (Concrete Implementation):
 * Implements the cost calculation algorithm specific to
 * health plans. Health plans factor in a risk multiplier
 * and a coverage utilization rate on top of the base cost.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This class is solely responsible for health plan
 * cost calculation. Nothing else lives here.
 *
 * GRASP - Information Expert:
 * This class knows everything about how health plan
 * costs are calculated — it is the expert on this logic.
 */
public class HealthCostStrategy implements CostCalculationStrategy {

    // ── Health Plan Pricing Constants ─────────────────────────────────────────

    /**
     * Risk multiplier applied to health plans.
     * Health coverage carries higher risk, so costs are
     * adjusted upward by this factor.
     */
    private static final double HEALTH_RISK_MULTIPLIER = 1.25;

    /**
     * Coverage utilization rate for health plans.
     * Represents the percentage of coverage limit factored
     * into the final premium calculation.
     */
    private static final double COVERAGE_UTILIZATION_RATE = 0.05;

    // ── Strategy Implementation ───────────────────────────────────────────────

    /**
     * Calculates the final cost for a health benefit plan.
     *
     * Formula:
     * finalCost = (baseCost * HEALTH_RISK_MULTIPLIER)
     * + (coverageLimit * COVERAGE_UTILIZATION_RATE)
     *
     * The risk multiplier increases the base cost to account
     * for health-related risk. The utilization rate adds a
     * small premium based on how much coverage is offered.
     *
     * @param baseCost       The base premium of the plan.
     * @param coverageLimit  The maximum coverage offered.
     * @param durationMonths The duration of the plan in months.
     * @return The final calculated cost for this health plan.
     */
    @Override
    public double calculate(double baseCost,
            double coverageLimit,
            int durationMonths) {

        double riskAdjustedCost = baseCost * HEALTH_RISK_MULTIPLIER;
        double coverageComponent = coverageLimit * COVERAGE_UTILIZATION_RATE;
        double finalCost = riskAdjustedCost + coverageComponent;

        System.out.println("[HealthCostStrategy] Base: " + baseCost
                + " | Risk Adjusted: " + riskAdjustedCost
                + " | Coverage Component: " + coverageComponent
                + " | Final: " + finalCost);

        return finalCost;
    }
}
