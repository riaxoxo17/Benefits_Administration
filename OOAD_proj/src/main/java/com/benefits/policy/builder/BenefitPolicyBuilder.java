package com.benefits.policy.builder;

import com.benefits.policy.model.BenefitPolicy;
import com.benefits.policy.model.BenefitPolicy.EligibleEmploymentType;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for constructing BenefitPolicy objects.
 *
 * CREATIONAL PATTERN - Builder:
 * Benefit policies have many fields — some required, many optional.
 * Without the Builder pattern, we would need multiple overloaded
 * constructors (telescoping constructor anti-pattern) or force
 * callers to pass nulls for fields they don't need.
 *
 * The Builder solves this by letting callers set only what they
 * need, in any order, with a readable fluent API:
 *
 *   BenefitPolicy policy = new BenefitPolicyBuilder("POL001", "Basic Policy")
 *       .eligibleSalaryBands(List.of("L1", "L2"))
 *       .waitingPeriodMonths(3)
 *       .maxCoverageAllowed(100000.0)
 *       .build();
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This class is solely responsible for constructing BenefitPolicy
 * objects. Validation is handled by the validator chain separately.
 *
 * SOLID - Open/Closed Principle (OCP):
 * New optional fields can be added to the builder without changing
 * any existing builder calls in the codebase.
 *
 * GRASP - Creator:
 * The builder is the designated creator of BenefitPolicy objects.
 * BenefitPolicy's constructor is package-private — only this
 * builder can call it.
 */
public class BenefitPolicyBuilder {

    // ── Required Fields ───────────────────────────────────────────────────────

    /** Unique identifier for this policy. Required. */
    final String policyId;

    /** Display name for this policy. Required. */
    final String policyName;

    // ── Optional Fields (with safe defaults) ─────────────────────────────────

    /** Salary bands eligible under this policy. */
    List<String> eligibleSalaryBands = new ArrayList<>();

    /** Employment types eligible under this policy. */
    List<EligibleEmploymentType> eligibleEmploymentTypes = new ArrayList<>();

    /** Waiting period in months before benefits can be claimed. Default: 0 */
    int waitingPeriodMonths = 0;

    /** Maximum coverage allowed under this policy. Default: unlimited */
    double maxCoverageAllowed = Double.MAX_VALUE;

    /** Human-readable eligibility rule description. */
    String eligibilityRules = "No specific rules defined.";

    /** Date this policy becomes effective. */
    String effectiveDate = "N/A";

    /** Date this policy was last updated. */
    String lastUpdatedDate = "N/A";

    /** HR configuration notes for this policy. */
    String hrConfiguration = "No HR configuration specified.";

    // ── Constructor (Required Fields) ─────────────────────────────────────────

    /**
     * Initializes the builder with the two required fields.
     * All other fields are optional and have safe defaults.
     *
     * @param policyId   Unique identifier for the policy.
     * @param policyName Display name for the policy.
     * @throws IllegalArgumentException if required fields are null or blank.
     */
    public BenefitPolicyBuilder(String policyId, String policyName) {
        if (policyId == null || policyId.isBlank()) {
            throw new IllegalArgumentException(
                    "[Builder] Policy ID is required and cannot be blank.");
        }
        if (policyName == null || policyName.isBlank()) {
            throw new IllegalArgumentException(
                    "[Builder] Policy Name is required and cannot be blank.");
        }
        this.policyId   = policyId;
        this.policyName = policyName;
    }

    // ── Fluent Setters ────────────────────────────────────────────────────────

    /**
     * Sets the salary bands eligible under this policy.
     *
     * @param bands List of salary band codes e.g. ["L1", "L2", "L3"].
     * @return This builder instance for chaining.
     */
    public BenefitPolicyBuilder eligibleSalaryBands(List<String> bands) {
        this.eligibleSalaryBands = bands;
        return this;
    }

    /**
     * Sets the employment types eligible under this policy.
     *
     * @param types List of EligibleEmploymentType values.
     * @return This builder instance for chaining.
     */
    public BenefitPolicyBuilder eligibleEmploymentTypes(
            List<EligibleEmploymentType> types) {
        this.eligibleEmploymentTypes = types;
        return this;
    }

    /**
     * Sets the waiting period in months before benefits apply.
     *
     * @param months Number of months employee must wait.
     * @return This builder instance for chaining.
     */
    public BenefitPolicyBuilder waitingPeriodMonths(int months) {
        this.waitingPeriodMonths = months;
        return this;
    }

    /**
     * Sets the maximum coverage amount allowed under this policy.
     *
     * @param maxCoverage Maximum coverage as a double.
     * @return This builder instance for chaining.
     */
    public BenefitPolicyBuilder maxCoverageAllowed(double maxCoverage) {
        this.maxCoverageAllowed = maxCoverage;
        return this;
    }

    /**
     * Sets a human-readable description of the eligibility rules.
     *
     * @param rules Eligibility rule description.
     * @return This builder instance for chaining.
     */
    public BenefitPolicyBuilder eligibilityRules(String rules) {
        this.eligibilityRules = rules;
        return this;
    }

    /**
     * Sets the date this policy becomes effective.
     *
     * @param date Effective date in YYYY-MM-DD format.
     * @return This builder instance for chaining.
     */
    public BenefitPolicyBuilder effectiveDate(String date) {
        this.effectiveDate = date;
        return this;
    }

    /**
     * Sets the date this policy was last updated.
     *
     * @param date Last updated date in YYYY-MM-DD format.
     * @return This builder instance for chaining.
     */
    public BenefitPolicyBuilder lastUpdatedDate(String date) {
        this.lastUpdatedDate = date;
        return this;
    }

    /**
     * Sets the HR configuration notes for this policy.
     *
     * @param config HR configuration string.
     * @return This builder instance for chaining.
     */
    public BenefitPolicyBuilder hrConfiguration(String config) {
        this.hrConfiguration = config;
        return this;
    }

    // ── Build ─────────────────────────────────────────────────────────────────

    /**
     * Constructs and returns the final BenefitPolicy object.
     * Passes this builder instance to BenefitPolicy's
     * package-private constructor.
     *
     * @return A fully constructed BenefitPolicy instance.
     */
    public BenefitPolicy build() {
        System.out.println("[Builder] Building policy: "
                + policyId + " — " + policyName);
        return new BenefitPolicy(this);
    }
}
