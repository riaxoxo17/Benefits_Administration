package com.benefits.catalog.strategy;

/**
 * Cost calculation strategy for Retirement benefit plans.
 *
 * BEHAVIOURAL PATTERN - Strategy (Concrete Implementation):
 * Implements cost calculation for retirement plans.
 * Retirement plans factor in duration heavily — longer
 * plans accumulate more employer contribution over time.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Solely responsible for retirement plan cost calculation.
 *
 * GRASP - Information Expert:
 * Expert on retirement plan pricing rules and logic.
 */
public class RetirementCostStrategy implements CostCalculationStrategy {

        // ── Retirement Plan Pricing Constants ─────────────────────────────────────

        /**
         * Monthly contribution rate applied to retirement plans.
         * Represents the percentage of base cost contributed
         * per month of plan duration.
         */
        private static final double MONTHLY_CONTRIBUTION_RATE = 0.08;

        // ── Strategy Implementation ───────────────────────────────────────────────

        /**
         * Calculates the final cost for a retirement benefit plan.
         *
         * Formula:
         * finalCost = baseCost
         * + (baseCost * MONTHLY_CONTRIBUTION_RATE
         * * durationMonths)
         *
         * Duration is central to retirement plan costs —
         * the longer the plan, the higher the total contribution.
         *
         * @param baseCost       The base premium of the plan.
         * @param coverageLimit  The maximum coverage offered.
         * @param durationMonths The duration of the plan in months.
         * @return The final calculated cost for this retirement plan.
         */
        @Override
        public double calculate(double baseCost,
                        double coverageLimit,
                        int durationMonths) {

                double monthlyContribution = baseCost
                                * MONTHLY_CONTRIBUTION_RATE
                                * durationMonths;
                double finalCost = baseCost + monthlyContribution;

                System.out.println("[RetirementCostStrategy] Base: " + baseCost
                                + " | Monthly Contribution: " + monthlyContribution
                                + " | Duration: " + durationMonths + " months"
                                + " | Final: " + finalCost);

                return finalCost;
        }
}
