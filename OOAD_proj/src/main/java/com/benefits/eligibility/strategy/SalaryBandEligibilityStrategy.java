package main.java.com.benefits.eligibility.strategy;

import main.java.com.benefits.model.EmployeeProfile;
import main.java.com.benefits.catalog.model.BenefitPlan;

/**
 * Eligibility strategy based on salary band.
 *
 * RULE: The employee's salary band must be at or above the minimum
 * required band for the plan. Bands are compared alphabetically
 * (B1 < B2 < B3 < B4 < B5).
 *
 * Plans define their minimum band in their eligibilityCriteria
 * field using the format: "SALARY_BAND:B2" — meaning the employee
 * must be in band B2, B3, B4, or B5 to qualify.
 *
 * If the plan does not specify a salary band requirement, this
 * strategy automatically passes — it only enforces what is declared.
 *
 * BEHAVIOURAL PATTERN - Strategy (Concrete Implementation):
 * A pluggable rule. Changing salary band thresholds only requires
 * changing this class — everything else stays untouched.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This class only handles the salary band rule. Nothing else.
 *
 * SOLID - Open/Closed Principle (OCP):
 * This class is open for modification if band logic changes,
 * but nothing outside this class needs to change with it.
 *
 * GRASP - Information Expert:
 * This class is the expert on salary-band-based eligibility rules.
 */
public class SalaryBandEligibilityStrategy implements EligibilityStrategy {

    // ── Prefix used in plan's eligibilityCriteria field ─────────────────────
    private static final String BAND_PREFIX = "SALARY_BAND:";

    /**
     * Checks whether the employee's salary band meets the plan's
     * minimum salary band requirement.
     *
     * If the plan's eligibilityCriteria does not contain a
     * SALARY_BAND: prefix, this check is skipped (returns true).
     *
     * @param profile The employee profile to check.
     * @param plan    The benefit plan whose criteria to evaluate.
     * @return true if eligible under the salary band rule.
     */
    @Override
    public boolean isEligible(EmployeeProfile profile, BenefitPlan plan) {
        String criteria = plan.getEligibilityCriteria();

        // If the plan has no salary band requirement, auto-pass
        if (criteria == null || !criteria.contains(BAND_PREFIX)) {
            System.out.println("  [" + getStrategyName() + "] "
                    + "No salary band requirement on this plan → PASS");
            return true;
        }

        // Extract the required minimum band from criteria string
        // e.g. "FULL_TIME, SALARY_BAND:B2" → "B2"
        String requiredBand = extractRequiredBand(criteria);
        String employeeBand = profile.getSalaryBand();

        // Compare bands alphabetically: B1 < B2 < B3 < B4 < B5
        boolean eligible = employeeBand.compareToIgnoreCase(requiredBand) >= 0;

        System.out.println("  [" + getStrategyName() + "] "
                + profile.getName()
                + " band: " + employeeBand
                + " | Required: " + requiredBand
                + " → " + (eligible ? "PASS" : "FAIL"));

        return eligible;
    }

    /**
     * Returns the name of this strategy for logging and reporting.
     *
     * @return Strategy name as a string.
     */
    @Override
    public String getStrategyName() {
        return "SalaryBandEligibilityStrategy";
    }

    // ── Private Helper ───────────────────────────────────────────────────────

    /**
     * Extracts the band identifier from the eligibility criteria string.
     * e.g. "FULL_TIME, SALARY_BAND:B2" → "B2"
     *
     * @param criteria The eligibility criteria string from the plan.
     * @return The required band string (e.g. "B2").
     */
    private String extractRequiredBand(String criteria) {
        int start = criteria.indexOf(BAND_PREFIX) + BAND_PREFIX.length();
        // Read until next comma or end of string
        int end = criteria.indexOf(",", start);
        if (end == -1) {
            end = criteria.length();
        }
        return criteria.substring(start, end).trim();
    }
}
