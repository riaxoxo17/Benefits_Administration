package main.java.com.benefits.eligibility.strategy;

import main.java.com.benefits.model.EmployeeProfile;
import main.java.com.benefits.catalog.model.BenefitPlan;

/**
 * Eligibility strategy based on employment type.
 *
 * RULE: Only FULL_TIME employees are eligible for benefits.
 * CONTRACT and INTERN employees are not eligible.
 *
 * BEHAVIOURAL PATTERN - Strategy (Concrete Implementation):
 * This is one pluggable rule in the eligibility engine.
 * Swapping this rule out (e.g. allowing CONTRACT employees)
 * only requires replacing or adding a strategy — no changes
 * to EligibilityServiceImpl or any other class.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This class only knows about employment-type-based eligibility.
 * Nothing else lives here.
 *
 * GRASP - Information Expert:
 * This class is the expert on the employment-type rule because
 * it holds and applies only that rule's logic.
 */
public class EmploymentTypeEligibilityStrategy implements EligibilityStrategy {

    /**
     * Checks whether the employee's employment type qualifies
     * them for benefits.
     *
     * FULL_TIME → eligible
     * CONTRACT  → not eligible
     * INTERN    → not eligible
     *
     * @param profile The employee profile to check.
     * @param plan    The plan being applied for (not used in this rule).
     * @return true if employment type is FULL_TIME, false otherwise.
     */
    @Override
    public boolean isEligible(EmployeeProfile profile, BenefitPlan plan) {
        boolean eligible =
                profile.getEmploymentType()
                == EmployeeProfile.EmploymentType.FULL_TIME;

        System.out.println("  [" + getStrategyName() + "] "
                + profile.getName()
                + " (" + profile.getEmploymentType() + ") → "
                + (eligible ? "PASS" : "FAIL"));

        return eligible;
    }

    /**
     * Returns the name of this strategy for logging and reporting.
     *
     * @return Strategy name as a string.
     */
    @Override
    public String getStrategyName() {
        return "EmploymentTypeEligibilityStrategy";
    }
}
