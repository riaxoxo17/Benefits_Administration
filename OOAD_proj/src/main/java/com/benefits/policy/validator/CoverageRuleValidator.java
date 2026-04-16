package com.benefits.policy.validator;

import com.benefits.policy.exception.PolicyValidationException;
import com.benefits.policy.model.BenefitPolicy;

/**
 * Validates that the maximum coverage defined in a policy is valid.
 *
 * BEHAVIOURAL PATTERN - Chain of Responsibility (Concrete Link 4):
 * Fourth and final link in the validation chain. Checks coverage
 * rule constraints. Being the last link, it does not pass further.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Solely responsible for coverage rule validation. Nothing else.
 *
 * GRASP - Information Expert:
 * Expert on what constitutes a valid coverage configuration
 * for a benefit policy.
 */
public class CoverageRuleValidator implements PolicyValidator {

    /** The next validator in the chain. Null for the final link. */
    private PolicyValidator next;

    // ── Coverage Constraints ──────────────────────────────────────────────────

    /**
     * Minimum meaningful coverage amount.
     * A policy with coverage below this threshold is likely
     * a data entry error and provides no real benefit.
     */
    private static final double MIN_COVERAGE_AMOUNT = 1000.0;

    /**
     * Maximum allowable coverage per organizational rules.
     * Coverage beyond this amount requires board-level approval
     * and cannot be set via standard HR configuration.
     */
    private static final double MAX_COVERAGE_AMOUNT = 10_000_000.0;

    // ── Chain Setup ───────────────────────────────────────────────────────────

    @Override
    public PolicyValidator setNext(PolicyValidator next) {
        this.next = next;
        return next;
    }

    // ── Validation Logic ──────────────────────────────────────────────────────

    /**
     * Validates that the policy's maximum coverage allowed falls
     * within organizational bounds (1,000 to 10,000,000).
     *
     * @param policy The BenefitPolicy to validate.
     * @throws PolicyValidationException if coverage amount is invalid.
     */
    @Override
    public void validate(BenefitPolicy policy)
            throws PolicyValidationException {

        System.out.println("[CoverageRuleValidator] Checking coverage rules "
                + "for policy: " + policy.getPolicyId());

        double maxCoverage = policy.getMaxCoverageAllowed();

        // Rule 1: Coverage must meet minimum threshold
        if (maxCoverage < MIN_COVERAGE_AMOUNT) {
            throw new PolicyValidationException(
                    "Policy [" + policy.getPolicyId() + "] max coverage of "
                    + maxCoverage + " is below the minimum allowed of "
                    + MIN_COVERAGE_AMOUNT + ".",
                    "COVERAGE_BELOW_MINIMUM");
        }

        // Rule 2: Coverage must not exceed organizational maximum
        if (maxCoverage > MAX_COVERAGE_AMOUNT) {
            throw new PolicyValidationException(
                    "Policy [" + policy.getPolicyId() + "] max coverage of "
                    + maxCoverage + " exceeds the organizational maximum of "
                    + MAX_COVERAGE_AMOUNT
                    + ". Board approval required.",
                    "COVERAGE_EXCEEDS_MAXIMUM");
        }

        System.out.println("[CoverageRuleValidator] PASSED — Max coverage: "
                + maxCoverage);

        // Final link — no further delegation needed
        if (next != null) {
            next.validate(policy);
        }
    }
}
