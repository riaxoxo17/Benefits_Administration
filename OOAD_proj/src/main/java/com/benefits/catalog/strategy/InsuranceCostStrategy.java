package main.java.com.benefits.catalog.strategy;

/**
 * Cost calculation strategy for Insurance benefit plans.
 *
 * BEHAVIOURAL PATTERN - Strategy (Concrete Implementation):
 * Implements cost calculation for insurance plans.
 * Insurance plans factor in both coverage limit and
 * duration — higher coverage over longer periods
 * results in higher premiums.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Solely responsible for insurance plan cost calculation.
 *
 * GRASP - Information Expert:
 * Expert on insurance plan pricing rules and logic.
 */
public class InsuranceCostStrategy implements CostCalculationStrategy {

    // ── Insurance Plan Pricing Constants ──────────────────────────────────────

    /**
     * Risk multiplier for insurance plans.
     * Higher than dental, lower than health.
     */
    private static final double INSURANCE_RISK_MULTIPLIER = 1.15;

    /**
     * Coverage factor applied to insurance plans.
     * Scales cost based on how much coverage is provided.
     */
    private static final double COVERAGE_FACTOR = 0.03;

    // ── Strategy Implementation ───────────────────────────────────────────────

    /**
     * Calculates the final cost for an insurance benefit plan.
     *
     * Formula:
     * finalCost = (baseCost * INSURANCE_RISK_MULTIPLIER)
     * + (coverageLimit * COVERAGE_FACTOR)
     *
     * @param baseCost       The base premium of the plan.
     * @param coverageLimit  The maximum coverage offered.
     * @param durationMonths The duration of the plan in months.
     * @return The final calculated cost for this insurance plan.
     */
    @Override
    public double calculate(double baseCost,
            double coverageLimit,
            int durationMonths) {

        double riskAdjustedCost = baseCost * INSURANCE_RISK_MULTIPLIER;
        double coverageComponent = coverageLimit * COVERAGE_FACTOR;
        double finalCost = riskAdjustedCost + coverageComponent;

        System.out.println("[InsuranceCostStrategy] Base: " + baseCost
                + " | Risk Adjusted: " + riskAdjustedCost
                + " | Coverage Component: " + coverageComponent
                + " | Final: " + finalCost);

        return finalCost;
    }
}
