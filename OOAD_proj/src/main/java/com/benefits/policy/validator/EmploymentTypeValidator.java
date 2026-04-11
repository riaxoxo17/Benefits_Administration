package com.benefits.policy.validator;

import com.benefits.policy.exception.PolicyValidationException;
import com.benefits.policy.model.BenefitPolicy;

/**
 * Validates that a policy specifies at least one eligible employment type.
 *
 * BEHAVIOURAL PATTERN - Chain of Responsibility (Concrete Link 2):
 * Second link in the validation chain. Checks employment type
 * rules and passes to the next validator if this check passes.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Solely responsible for employment type validation. Nothing else.
 *
 * GRASP - Information Expert:
 * Expert on what constitutes valid employment type configuration
 * for a benefit policy.
 */
public class EmploymentTypeValidator implements PolicyValidator {

    /** The next validator in the chain. */
    private PolicyValidator next;

    // ── Chain Setup ───────────────────────────────────────────────────────────

    @Override
    public PolicyValidator setNext(PolicyValidator next) {
        this.next = next;
        return next;
    }

    // ── Validation Logic ──────────────────────────────────────────────────────

    /**
     * Validates that the policy has at least one eligible employment type.
     * A policy that applies to no employment type cannot be enforced
     * against any employee — this is a critical configuration error.
     *
     * @param policy The BenefitPolicy to validate.
     * @throws PolicyValidationException if no employment types are defined.
     */
    @Override
    public void validate(BenefitPolicy policy)
            throws PolicyValidationException {

        System.out.println("[EmploymentTypeValidator] Checking employment "
                + "types for policy: " + policy.getPolicyId());

        // Rule: Policy must specify at least one employment type
        if (policy.getEligibleEmploymentTypes() == null
                || policy.getEligibleEmploymentTypes().isEmpty()) {
            throw new PolicyValidationException(
                    "Policy [" + policy.getPolicyId() + "] must define "
                    + "at least one eligible employment type.",
                    "INVALID_EMPLOYMENT_TYPE_CONFIG");
        }

        System.out.println("[EmploymentTypeValidator] PASSED — Types: "
                + policy.getEligibleEmploymentTypes());

        // Pass to next validator in chain
        if (next != null) {
            next.validate(policy);
        }
    }
}
