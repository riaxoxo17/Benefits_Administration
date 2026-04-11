package com.benefits.policy.controller;

import com.benefits.policy.exception.PolicyValidationException;
import com.benefits.policy.facade.BenefitsPolicyFacade;
import com.benefits.policy.model.BenefitPolicy;
import com.benefits.policy.model.BenefitPolicy.EligibleEmploymentType;

import java.util.Collection;
import java.util.List;

/**
 * Controller for the Benefits Policy Management subsystem.
 *
 * GRASP - Controller:
 * Designated handler for all incoming requests related to
 * benefit policies. Receives requests from the outside world
 * (HR admin UI, API, other subsystems), validates inputs,
 * and delegates to the Facade. Never performs logic itself.
 *
 * GRASP - Low Coupling:
 * Only knows about the Facade. Zero knowledge of builder,
 * validators, DAO, or policy internals.
 *
 * GRASP - High Cohesion:
 * Each method handles exactly one type of request.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Only responsible for receiving, validating inputs, and
 * delegating. No business or persistence logic here.
 *
 * SOLID - Open/Closed Principle (OCP):
 * New request types added as new methods — no modifications
 * to existing handlers ever needed.
 */
public class PolicyController {

    // ── Facade Reference ──────────────────────────────────────────────────────

    /** The facade this controller delegates all requests to. */
    private final BenefitsPolicyFacade policyFacade;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * Constructs the controller with the policy facade.
     *
     * @param policyFacade The BenefitsPolicyFacade instance.
     */
    public PolicyController(BenefitsPolicyFacade policyFacade) {
        this.policyFacade = policyFacade;
        System.out.println("[PolicyController] PolicyController initialized.");
    }

    // ── Request Handlers ──────────────────────────────────────────────────────

    /**
     * Handles an incoming request to create a new benefit policy.
     * Validates required fields, then delegates to the facade which
     * uses the Builder and runs the validation chain before saving.
     *
     * @param policyId            Unique policy identifier.
     * @param policyName          Display name of the policy.
     * @param eligibleSalaryBands Salary bands eligible under this policy.
     * @param eligibleEmpTypes    Employment types eligible under this policy.
     * @param waitingPeriodMonths Waiting period in months.
     * @param maxCoverageAllowed  Maximum coverage allowed.
     * @param eligibilityRules    Human-readable eligibility rules.
     * @param effectiveDate       Date policy becomes effective.
     * @param lastUpdatedDate     Date policy was last updated.
     * @param hrConfiguration     HR configuration notes.
     */
    public void handleCreatePolicy(
            String policyId,
            String policyName,
            List<String> eligibleSalaryBands,
            List<EligibleEmploymentType> eligibleEmpTypes,
            int waitingPeriodMonths,
            double maxCoverageAllowed,
            String eligibilityRules,
            String effectiveDate,
            String lastUpdatedDate,
            String hrConfiguration) {

        System.out.println("\n[PolicyController] Handling → CREATE policy "
                + "request for ID: " + policyId);

        // ── Input Validation ─────────────────────────────────────────────────
        if (policyId == null || policyId.isBlank()) {
            System.out.println("[PolicyController] ERROR: Policy ID is required.");
            return;
        }
        if (policyName == null || policyName.isBlank()) {
            System.out.println("[PolicyController] ERROR: Policy name is required.");
            return;
        }
        if (eligibleSalaryBands == null || eligibleSalaryBands.isEmpty()) {
            System.out.println("[PolicyController] ERROR: At least one salary "
                    + "band is required.");
            return;
        }
        if (eligibleEmpTypes == null || eligibleEmpTypes.isEmpty()) {
            System.out.println("[PolicyController] ERROR: At least one employment "
                    + "type is required.");
            return;
        }

        try {
            // Delegate to facade — builder + validation chain runs inside
            policyFacade.createPolicy(
                    policyId, policyName, eligibleSalaryBands,
                    eligibleEmpTypes, waitingPeriodMonths,
                    maxCoverageAllowed, eligibilityRules,
                    effectiveDate, lastUpdatedDate, hrConfiguration);

            System.out.println("[PolicyController] SUCCESS: Policy created "
                    + "for ID: " + policyId);

        } catch (PolicyValidationException e) {
            // Validation chain rejected the policy — report the specific rule
            System.out.println("[PolicyController] VALIDATION FAILED: "
                    + e.toString());
        } catch (IllegalArgumentException e) {
            System.out.println("[PolicyController] FAILED: " + e.getMessage());
        }
    }

    /**
     * Handles an incoming request to retrieve a policy by ID.
     *
     * @param policyId The unique policy ID to look up.
     * @return The matching BenefitPolicy, or null if not found.
     */
    public BenefitPolicy handleGetPolicy(String policyId) {

        System.out.println("\n[PolicyController] Handling → GET policy "
                + "request for ID: " + policyId);

        if (policyId == null || policyId.isBlank()) {
            System.out.println("[PolicyController] ERROR: Policy ID is required.");
            return null;
        }

        try {
            BenefitPolicy policy = policyFacade.getPolicy(policyId);
            System.out.println("[PolicyController] SUCCESS: Policy retrieved "
                    + "for ID: " + policyId);
            return policy;

        } catch (IllegalArgumentException e) {
            System.out.println("[PolicyController] FAILED: " + e.getMessage());
            return null;
        }
    }

    /**
     * Handles an incoming request to retrieve all policies.
     *
     * @return A collection of all BenefitPolicy objects.
     */
    public Collection<BenefitPolicy> handleGetAllPolicies() {

        System.out.println("\n[PolicyController] Handling → GET ALL policies");

        Collection<BenefitPolicy> policies = policyFacade.getAllPolicies();

        System.out.println("[PolicyController] SUCCESS: Retrieved "
                + policies.size() + " policy/policies.");

        return policies;
    }

    /**
     * Handles an incoming request to retrieve all active policies.
     *
     * @return A collection of active BenefitPolicy objects.
     */
    public Collection<BenefitPolicy> handleGetActivePolicies() {

        System.out.println("\n[PolicyController] Handling → GET ACTIVE policies");

        Collection<BenefitPolicy> policies = policyFacade.getActivePolicies();

        System.out.println("[PolicyController] SUCCESS: Retrieved "
                + policies.size() + " active policy/policies.");

        return policies;
    }

    /**
     * Handles an incoming request to deactivate a policy.
     *
     * @param policyId The policy ID to deactivate.
     */
    public void handleDeactivatePolicy(String policyId) {

        System.out.println("\n[PolicyController] Handling → DEACTIVATE policy "
                + "for ID: " + policyId);

        if (policyId == null || policyId.isBlank()) {
            System.out.println("[PolicyController] ERROR: Policy ID is required.");
            return;
        }

        try {
            policyFacade.deactivatePolicy(policyId);
            System.out.println("[PolicyController] SUCCESS: Policy deactivated "
                    + "for ID: " + policyId);

        } catch (IllegalArgumentException e) {
            System.out.println("[PolicyController] FAILED: " + e.getMessage());
        }
    }

    /**
     * Handles an incoming request to delete a policy by ID.
     *
     * @param policyId The unique policy ID to delete.
     */
    public void handleDeletePolicy(String policyId) {

        System.out.println("\n[PolicyController] Handling → DELETE policy "
                + "for ID: " + policyId);

        if (policyId == null || policyId.isBlank()) {
            System.out.println("[PolicyController] ERROR: Policy ID is required.");
            return;
        }

        try {
            policyFacade.deletePolicy(policyId);
            System.out.println("[PolicyController] SUCCESS: Policy deleted "
                    + "for ID: " + policyId);

        } catch (IllegalArgumentException e) {
            System.out.println("[PolicyController] FAILED: " + e.getMessage());
        }
    }
}
