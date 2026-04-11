package com.benefits.policy.model;

import java.util.List;

/**
 * Represents an organizational benefit policy.
 *
 * CREATIONAL PATTERN - Builder (product class):
 * This class is constructed exclusively via BenefitPolicyBuilder.
 * The constructor is package-private — no one can instantiate
 * this directly. All creation flows through the builder.
 *
 * This design is necessary because policies have many optional
 * fields. A telescoping constructor would be unreadable and
 * error-prone. The builder solves this cleanly.
 *
 * GRASP - Information Expert:
 * This class owns and manages all policy rule data. It is the
 * single source of truth for what a policy contains.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This class is solely responsible for holding and representing
 * benefit policy data. No validation or persistence logic here.
 */
public class BenefitPolicy {

    // ── Identity ──────────────────────────────────────────────────────────────

    private final String policyId;
    private final String policyName;

    // ── Eligibility Rules ─────────────────────────────────────────────────────

    /** Salary bands eligible under this policy. e.g. ["L1","L2","L3"] */
    private final List<String> eligibleSalaryBands;

    /** Employment types eligible under this policy. */
    private final List<EligibleEmploymentType> eligibleEmploymentTypes;

    /** Minimum months of tenure before employee can claim benefits. */
    private final int waitingPeriodMonths;

    /** Maximum coverage amount allowed under this policy. */
    private final double maxCoverageAllowed;

    /** Human-readable description of eligibility rules. */
    private final String eligibilityRules;

    // ── Policy Metadata ───────────────────────────────────────────────────────

    private final String effectiveDate; // Format: YYYY-MM-DD
    private final String lastUpdatedDate; // Format: YYYY-MM-DD

    /** HR configuration notes attached to this policy. */
    private final String hrConfiguration;

    /** Whether this policy is currently active. */
    private boolean active;

    // ── Enum ──────────────────────────────────────────────────────────────────

    /**
     * Employment types recognized by the policy engine.
     *
     * SOLID - OCP: New employment types can be added here
     * without modifying any existing policy or validator logic.
     */
    public enum EligibleEmploymentType {
        FULL_TIME,
        CONTRACT,
        INTERN,
        ALL
    }

    // ── Package-Private Constructor (Builder only) ────────────────────────────

    /**
     * Constructor is package-private — only BenefitPolicyBuilder
     * can call this. This enforces the Builder pattern strictly.
     *
     * @param builder The builder instance carrying all field values.
     */
    BenefitPolicy(BenefitPolicyBuilder builder) {
        this.policyId = builder.policyId;
        this.policyName = builder.policyName;
        this.eligibleSalaryBands = builder.eligibleSalaryBands;
        this.eligibleEmploymentTypes = builder.eligibleEmploymentTypes;
        this.waitingPeriodMonths = builder.waitingPeriodMonths;
        this.maxCoverageAllowed = builder.maxCoverageAllowed;
        this.eligibilityRules = builder.eligibilityRules;
        this.effectiveDate = builder.effectiveDate;
        this.lastUpdatedDate = builder.lastUpdatedDate;
        this.hrConfiguration = builder.hrConfiguration;
        this.active = true; // default: active on creation
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public String getPolicyId() {
        return policyId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public List<String> getEligibleSalaryBands() {
        return eligibleSalaryBands;
    }

    public List<EligibleEmploymentType> getEligibleEmploymentTypes() {
        return eligibleEmploymentTypes;
    }

    public int getWaitingPeriodMonths() {
        return waitingPeriodMonths;
    }

    public double getMaxCoverageAllowed() {
        return maxCoverageAllowed;
    }

    public String getEligibilityRules() {
        return eligibilityRules;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public String getHrConfiguration() {
        return hrConfiguration;
    }

    public boolean isActive() {
        return active;
    }

    // ── Setters (limited) ─────────────────────────────────────────────────────

    /** Allows HR admin to activate or deactivate a policy. */
    public void setActive(boolean active) {
        this.active = active;
    }

    // ── Display ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "BenefitPolicy {" +
                "\n  Policy ID          : " + policyId +
                "\n  Policy Name        : " + policyName +
                "\n  Eligible Bands     : " + eligibleSalaryBands +
                "\n  Eligible Emp Types : " + eligibleEmploymentTypes +
                "\n  Waiting Period     : " + waitingPeriodMonths + " months" +
                "\n  Max Coverage       : " + maxCoverageAllowed +
                "\n  Eligibility Rules  : " + eligibilityRules +
                "\n  Effective Date     : " + effectiveDate +
                "\n  Last Updated       : " + lastUpdatedDate +
                "\n  HR Config          : " + hrConfiguration +
                "\n  Active             : " + active +
                "\n}";
    }
}
