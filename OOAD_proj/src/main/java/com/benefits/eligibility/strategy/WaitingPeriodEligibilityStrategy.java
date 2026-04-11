package main.java.com.benefits.eligibility.strategy;

import main.java.com.benefits.model.EmployeeProfile;
import main.java.com.benefits.catalog.model.BenefitPlan;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Eligibility strategy based on waiting period from date of joining.
 *
 * RULE: An employee must have been with the company for at least
 * a minimum number of months before they can enroll in a plan.
 *
 * Plans define their waiting period in their eligibilityCriteria
 * field using the format: "WAITING_PERIOD:6" — meaning the employee
 * must have joined at least 6 months ago to be eligible.
 *
 * If the plan does not specify a waiting period, this strategy
 * automatically passes.
 *
 * The employee's dateOfJoining is expected in the format: yyyy-MM-dd
 * (e.g. "2023-06-15"). If it cannot be parsed, this check passes
 * with a warning — we do not block enrollment due to a data format
 * issue, since that would be a data quality problem, not a rule
 * violation.
 *
 * BEHAVIOURAL PATTERN - Strategy (Concrete Implementation):
 * A pluggable rule. Changing waiting period logic only requires
 * modifying this class — the service and other strategies are
 * completely unaffected.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This class only handles the waiting period rule.
 *
 * GRASP - Information Expert:
 * This class is the expert on tenure-based eligibility.
 */
public class WaitingPeriodEligibilityStrategy implements EligibilityStrategy {

    // ── Prefix used in plan's eligibilityCriteria field ─────────────────────
    private static final String WAITING_PREFIX = "WAITING_PERIOD:";

    // ── Expected date format for dateOfJoining ───────────────────────────────
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Checks whether the employee has completed the required waiting
     * period before enrolling in this plan.
     *
     * @param profile The employee profile to check.
     * @param plan    The benefit plan whose criteria to evaluate.
     * @return true if the employee has served the waiting period.
     */
    @Override
    public boolean isEligible(EmployeeProfile profile, BenefitPlan plan) {
        String criteria = plan.getEligibilityCriteria();

        // If the plan has no waiting period requirement, auto-pass
        if (criteria == null || !criteria.contains(WAITING_PREFIX)) {
            System.out.println("  [" + getStrategyName() + "] "
                    + "No waiting period requirement on this plan → PASS");
            return true;
        }

        // Extract required months from criteria string
        int requiredMonths = extractRequiredMonths(criteria);

        // Parse the employee's date of joining
        String joiningDateStr = profile.getDateOfJoining();
        if (joiningDateStr == null || joiningDateStr.isBlank()) {
            System.out.println("  [" + getStrategyName() + "] "
                    + "Date of joining not set — skipping waiting period check → PASS");
            return true;
        }

        try {
            LocalDate joiningDate = LocalDate.parse(joiningDateStr, DATE_FORMAT);
            LocalDate today = LocalDate.now();
            int monthsWorked = Period.between(joiningDate, today).toTotalMonths() > Integer.MAX_VALUE
                    ? Integer.MAX_VALUE
                    : (int) Period.between(joiningDate, today).toTotalMonths();

            boolean eligible = monthsWorked >= requiredMonths;

            System.out.println("  [" + getStrategyName() + "] "
                    + profile.getName()
                    + " joined: " + joiningDateStr
                    + " | Months worked: " + monthsWorked
                    + " | Required: " + requiredMonths
                    + " → " + (eligible ? "PASS" : "FAIL"));

            return eligible;

        } catch (DateTimeParseException e) {
            // Data quality issue — log and pass gracefully
            System.out.println("  [" + getStrategyName() + "] "
                    + "WARNING: Could not parse dateOfJoining '"
                    + joiningDateStr + "'. Skipping check → PASS");
            return true;
        }
    }

    /**
     * Returns the name of this strategy for logging and reporting.
     *
     * @return Strategy name as a string.
     */
    @Override
    public String getStrategyName() {
        return "WaitingPeriodEligibilityStrategy";
    }

    // ── Private Helper ───────────────────────────────────────────────────────

    /**
     * Extracts the required months from the plan's eligibility criteria.
     * e.g. "FULL_TIME, WAITING_PERIOD:6" → 6
     *
     * @param criteria The eligibility criteria string from the plan.
     * @return The required waiting period in months.
     */
    private int extractRequiredMonths(String criteria) {
        int start = criteria.indexOf(WAITING_PREFIX) + WAITING_PREFIX.length();
        int end = criteria.indexOf(",", start);
        if (end == -1) {
            end = criteria.length();
        }
        try {
            return Integer.parseInt(criteria.substring(start, end).trim());
        } catch (NumberFormatException e) {
            // If we cannot parse the number, default to 0 (no waiting period)
            System.out.println("  [" + getStrategyName() + "] "
                    + "WARNING: Could not parse waiting period value — defaulting to 0");
            return 0;
        }
    }
}
