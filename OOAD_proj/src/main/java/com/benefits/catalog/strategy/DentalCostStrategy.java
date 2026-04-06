package main.java.com.benefits.catalog.strategy;

/**
 * Cost calculation strategy for Dental benefit plans.
 *
 * BEHAVIOURAL PATTERN - Strategy (Concrete Implementation):
 * Implements the cost calculation algorithm specific to
 * dental plans. Dental plans are lower risk than health
 * plans and use a flat service fee on top of base cost.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This class is solely responsible for dental plan
 * cost calculation. Nothing else lives here.
 *
 * GRASP - Information Expert:
 * This class is the expert on dental plan pricing logic.
 */
public class DentalCostStrategy implements CostCalculationStrategy {

    // ── Dental Plan Pricing Constants ─────────────────────────────────────────

    /**
     * Risk multiplier for dental plans.
     * Lower than health plans as dental risk is more predictable.
     */
    private static final double DENTAL_RISK_MULTIPLIER = 1.10;

    /**
     * Flat service fee added to all dental plans.
     * Covers administrative and provider network costs.
     */
    private static final double FLAT_SERVICE_FEE = 50.0;

    // ── Strategy Implementation ───────────────────────────────────────────────

    /**
     * Calculates the final cost for a dental benefit plan.
     *
     * Formula:
     * finalCost = (baseCost * DENTAL_RISK_MULTIPLIER)
     * + FLAT_SERVICE_FEE
     *
     * @param baseCost       The base premium of the plan.
     * @param coverageLimit  The maximum coverage offered.
     * @param durationMonths The duration of the plan in months.
     * @return The final calculated cost for this dental plan.
     */
    @Override
    public double calculate(double baseCost,
            double coverageLimit,
            int durationMonths) {

        double riskAdjustedCost = baseCost * DENTAL_RISK_MULTIPLIER;
        double finalCost = riskAdjustedCost + FLAT_SERVICE_FEE;

        System.out.println("[DentalCostStrategy] Base: " + baseCost
                + " | Risk Adjusted: " + riskAdjustedCost
                + " | Service Fee: " + FLAT_SERVICE_FEE
                + " | Final: " + finalCost);

        return finalCost;
    }
}
