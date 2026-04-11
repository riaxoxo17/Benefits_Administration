package com.benefits.policy.facade;

import com.benefits.policy.builder.BenefitPolicyBuilder;
import com.benefits.policy.dao.BenefitPolicyDAO;
import com.benefits.policy.exception.PolicyValidationException;
import com.benefits.policy.model.BenefitPolicy;
import com.benefits.policy.model.BenefitPolicy.EligibleEmploymentType;
import com.benefits.policy.validator.CoverageRuleValidator;
import com.benefits.policy.validator.EmploymentTypeValidator;
import com.benefits.policy.validator.PolicyValidator;
import com.benefits.policy.validator.SalaryBandValidator;
import com.benefits.policy.validator.WaitingPeriodValidator;

import java.util.Collection;
import java.util.List;

/**
 * Facade for the Benefits Policy Management subsystem.
 *
 * STRUCTURAL PATTERN - Facade:
 * Provides a single, clean entry point to the entire policy
 * subsystem. External components only interact with this class —
 * never directly with the builder, validators, or DAO.
 *
 * This class internally wires up:
 *   1. The Builder (to construct policy objects)
 *   2. The Validator Chain (to validate before saving)
 *   3. The DAO (to persist via DB team's implementation)
 *
 * GRASP - Low Coupling:
 * All external components depend only on this facade. The
 * internal builder, chain, and DAO are completely hidden.
 *
 * GRASP - High Cohesion:
 * This class only provides a clean interface to the policy
 * subsystem. All actual logic lives in appropriate classes.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Only responsible for delegating calls correctly. No business
 * logic, no validation logic, no persistence logic lives here.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * Accepts BenefitPolicyDAO interface at construction time —
 * never a concrete class.
 */
public class BenefitsPolicyFacade {

    // ── Internal Dependencies ─────────────────────────────────────────────────

    /** DAO for all policy data operations. Provided by DB team. */
    private final BenefitPolicyDAO policyDAO;

    /**
     * Head of the validation chain.
     * All policies pass through this chain before being saved.
     *
     * Chain order:
     *   SalaryBandValidator
     *       → EmploymentTypeValidator
     *           → WaitingPeriodValidator
     *               → CoverageRuleValidator
     */
    private final PolicyValidator validationChain;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * Initializes the facade with the DAO implementation from
     * the DB team. Internally builds the validation chain.
     *
     * @param policyDAO The BenefitPolicyDAO implementation from DB team.
     */
    public BenefitsPolicyFacade(BenefitPolicyDAO policyDAO) {
        this.policyDAO = policyDAO;
        this.validationChain = buildValidationChain();

        System.out.println("[PolicyFacade] BenefitsPolicyFacade initialized.");
        System.out.println("[PolicyFacade] Validation chain ready: "
                + "SalaryBand → EmploymentType → WaitingPeriod → Coverage");
        System.out.println("[PolicyFacade] Connected to DAO: "
                + policyDAO.getClass().getSimpleName());
        System.out.println("[PolicyFacade] Ready to accept requests.\n");
    }

    // ── Validation Chain Builder ──────────────────────────────────────────────

    /**
     * Constructs and wires the Chain of Responsibility for
     * policy validation.
     *
     * BEHAVIOURAL PATTERN - Chain of Responsibility:
     * Each validator is a link. setNext() connects them.
     * The chain is built once here and reused for every
     * policy validation request.
     *
     * @return The head of the validation chain.
     */
    private PolicyValidator buildValidationChain() {
        PolicyValidator salaryBandValidator    = new SalaryBandValidator();
        PolicyValidator employmentTypeValidator = new EmploymentTypeValidator();
        PolicyValidator waitingPeriodValidator  = new WaitingPeriodValidator();
        PolicyValidator coverageRuleValidator   = new CoverageRuleValidator();

        // Wire the chain: salary → employment → waiting → coverage
        salaryBandValidator
                .setNext(employmentTypeValidator)
                .setNext(waitingPeriodValidator)
                .setNext(coverageRuleValidator);

        return salaryBandValidator; // return the head of the chain
    }

    // ── Public API ────────────────────────────────────────────────────────────

    /**
     * Creates a new benefit policy using the Builder pattern,
     * validates it through the Chain of Responsibility,
     * and persists it via the DAO if all checks pass.
     *
     * @param policyId             Unique policy identifier.
     * @param policyName           Display name of the policy.
     * @param eligibleSalaryBands  Salary bands covered by this policy.
     * @param eligibleEmpTypes     Employment types covered.
     * @param waitingPeriodMonths  Waiting period in months.
     * @param maxCoverageAllowed   Maximum coverage allowed.
     * @param eligibilityRules     Human-readable eligibility rules.
     * @param effectiveDate        Date policy becomes effective.
     * @param lastUpdatedDate      Date policy was last updated.
     * @param hrConfiguration      HR configuration notes.
     * @throws PolicyValidationException if policy fails any validation rule.
     */
    public void createPolicy(
            String policyId,
            String policyName,
            List<String> eligibleSalaryBands,
            List<EligibleEmploymentType> eligibleEmpTypes,
            int waitingPeriodMonths,
            double maxCoverageAllowed,
            String eligibilityRules,
            String effectiveDate,
            String lastUpdatedDate,
            String hrConfiguration)
            throws PolicyValidationException {

        System.out.println("[PolicyFacade] Received → createPolicy() "
                + "for ID: " + policyId);

        // ── Step 1: Build the policy using Builder pattern ────────────────────
        BenefitPolicy policy = new BenefitPolicyBuilder(policyId, policyName)
                .eligibleSalaryBands(eligibleSalaryBands)
                .eligibleEmploymentTypes(eligibleEmpTypes)
                .waitingPeriodMonths(waitingPeriodMonths)
                .maxCoverageAllowed(maxCoverageAllowed)
                .eligibilityRules(eligibilityRules)
                .effectiveDate(effectiveDate)
                .lastUpdatedDate(lastUpdatedDate)
                .hrConfiguration(hrConfiguration)
                .build();

        // ── Step 2: Validate through Chain of Responsibility ──────────────────
        System.out.println("[PolicyFacade] Running validation chain "
                + "for policy: " + policyId);
        validationChain.validate(policy);

        // ── Step 3: Persist via DAO if all validations pass ───────────────────
        policyDAO.save(policy);
        System.out.println("[PolicyFacade] Policy saved successfully: "
                + policyId);
    }

    /**
     * Retrieves a benefit policy by its unique ID.
     *
     * @param policyId The unique policy ID to look up.
     * @return The matching BenefitPolicy.
     * @throws IllegalArgumentException if no policy exists for the ID.
     */
    public BenefitPolicy getPolicy(String policyId) {

        System.out.println("[PolicyFacade] Received → getPolicy() "
                + "for ID: " + policyId);

        BenefitPolicy policy = policyDAO.findById(policyId);

        if (policy == null) {
            throw new IllegalArgumentException(
                    "No policy found for Policy ID: " + policyId);
        }

        return policy;
    }

    /**
     * Retrieves all benefit policies.
     *
     * @return A collection of all BenefitPolicy objects.
     */
    public Collection<BenefitPolicy> getAllPolicies() {
        System.out.println("[PolicyFacade] Received → getAllPolicies()");
        return policyDAO.findAll();
    }

    /**
     * Retrieves all currently active benefit policies.
     *
     * @return A collection of active BenefitPolicy objects.
     */
    public Collection<BenefitPolicy> getActivePolicies() {
        System.out.println("[PolicyFacade] Received → getActivePolicies()");
        return policyDAO.findAllActive();
    }

    /**
     * Deactivates a policy by setting its active flag to false
     * and updating it via the DAO.
     *
     * @param policyId The policy ID to deactivate.
     */
    public void deactivatePolicy(String policyId) {

        System.out.println("[PolicyFacade] Received → deactivatePolicy() "
                + "for ID: " + policyId);

        BenefitPolicy policy = getPolicy(policyId);
        policy.setActive(false);
        policyDAO.update(policy);

        System.out.println("[PolicyFacade] Policy deactivated: " + policyId);
    }

    /**
     * Deletes a benefit policy from the system.
     *
     * @param policyId The unique policy ID to delete.
     */
    public void deletePolicy(String policyId) {

        System.out.println("[PolicyFacade] Received → deletePolicy() "
                + "for ID: " + policyId);

        if (!policyDAO.existsById(policyId)) {
            throw new IllegalArgumentException(
                    "Cannot delete. No policy found for ID: " + policyId);
        }

        policyDAO.delete(policyId);
        System.out.println("[PolicyFacade] Policy deleted: " + policyId);
    }
}
