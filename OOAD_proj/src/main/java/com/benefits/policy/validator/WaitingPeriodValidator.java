package com.benefits.policy.validator;

import com.benefits.policy.exception.PolicyValidationException;
import com.benefits.policy.model.BenefitPolicy;

/**
 * Validates that the waiting period defined in a policy is reasonable.
 *
 * BEHAVIOURAL PATTERN - Chain of Responsibility (Concrete Link 3):
 * Third link in the validation chain. Checks waiting period rules
 * and passes to the next validator if this check passes.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Solely responsible for waiting period validation. Nothing else.
 *
 * GRASP - Information Expert:
 * Expert on what constitutes a valid waiting period for
 * a benefit policy according to organizational rules.
 */
public class WaitingPeriodValidator implements PolicyValidator {

    /** The next validator in the chain. */
    private PolicyValidator next;

    // ── Waiting Period Constraints ────────────────────────────────────────────

    /**
     * Maximum allowed waiting period in months.
     * Organizational rule: waiting period cannot exceed 24 months.
     * Beyond this, the policy is considered unreasonable and
     * likely a data entry error.
     */
    private static final int MAX_WAITING_PERIOD_MONTHS = 24;

    // ── Chain Setup ───────────────────────────────────────────────────────────

    @Override
    public PolicyValidator setNext(PolicyValidator next) {
        this.next = next;
        return next;
    }

    // ── Validation Logic ──────────────────────────────────────────────────────

    /**
     * Validates that the policy's waiting period is within
     * acceptable bounds (0 to 24 months).
     *
     * A negative waiting period is a data error.
     * A waiting period above 24 months is considered unreasonable
     * per organizational HR policy.
     *
     * @param policy The BenefitPolicy to validate.
     * @throws PolicyValidationException if waiting period is invalid.
     */
    @Override
    public void validate(BenefitPolicy policy)
            throws PolicyValidationException {

        System.out.println("[WaitingPeriodValidator] Checking waiting period "
                + "for policy: " + policy.getPolicyId());

        int waitingPeriod = policy.getWaitingPeriodMonths();

        // Rule 1: Waiting period cannot be negative
        if (waitingPeriod < 0) {
            throw new PolicyValidationException(
                    "Policy [" + policy.getPolicyId() + "] has an invalid "
                    + "negative waiting period: " + waitingPeriod + " months.",
                    "INVALID_WAITING_PERIOD");
        }

        // Rule 2: Waiting period cannot exceed organizational maximum
        if (waitingPeriod > MAX_WAITING_PERIOD_MONTHS) {
            throw new PolicyValidationException(
                    "Policy [" + policy.getPolicyId() + "] waiting period "
                    + "of " + waitingPeriod + " months exceeds the maximum "
                    + "allowed of " + MAX_WAITING_PERIOD_MONTHS + " months.",
                    "WAITING_PERIOD_EXCEEDED");
        }

        System.out.println("[WaitingPeriodValidator] PASSED — Waiting period: "
                + waitingPeriod + " months");

        // Pass to next validator in chain
        if (next != null) {
            next.validate(policy);
        }
    }
}
