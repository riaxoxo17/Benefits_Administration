package com.benefits.policy.validator;

import com.benefits.policy.exception.PolicyValidationException;
import com.benefits.policy.model.BenefitPolicy;

/**
 * Validates that a policy specifies at least one eligible salary band.
 *
 * BEHAVIOURAL PATTERN - Chain of Responsibility (Concrete Link 1):
 * First link in the validation chain. Checks salary band rules
 * and passes to the next validator if this check passes.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This class is solely responsible for salary band validation.
 * Nothing else.
 *
 * GRASP - Information Expert:
 * Knows exactly what constitutes a valid salary band configuration
 * and applies that knowledge here.
 */
public class SalaryBandValidator implements PolicyValidator {

    /** The next validator in the chain. Null if this is the last link. */
    private PolicyValidator next;

    // ── Chain Setup ───────────────────────────────────────────────────────────

    /**
     * Sets the next validator in the chain.
     *
     * @param next The next PolicyValidator to delegate to.
     * @return The next validator (enables fluent chaining).
     */
    @Override
    public PolicyValidator setNext(PolicyValidator next) {
        this.next = next;
        return next;
    }

    // ── Validation Logic ──────────────────────────────────────────────────────

    /**
     * Validates that the policy has at least one eligible salary band.
     * A policy with no salary bands defined cannot be applied to
     * any employee — this is a critical configuration error.
     *
     * @param policy The BenefitPolicy to validate.
     * @throws PolicyValidationException if no salary bands are defined.
     */
    @Override
    public void validate(BenefitPolicy policy)
            throws PolicyValidationException {

        System.out.println("[SalaryBandValidator] Checking salary bands "
                + "for policy: " + policy.getPolicyId());

        // Rule: Policy must have at least one eligible salary band
        if (policy.getEligibleSalaryBands() == null
                || policy.getEligibleSalaryBands().isEmpty()) {
            throw new PolicyValidationException(
                    "Policy [" + policy.getPolicyId() + "] must define "
                    + "at least one eligible salary band.",
                    "INVALID_SALARY_BAND_CONFIG");
        }

        System.out.println("[SalaryBandValidator] PASSED — Bands: "
                + policy.getEligibleSalaryBands());

        // Pass to next validator in chain if one exists
        if (next != null) {
            next.validate(policy);
        }
    }
}
