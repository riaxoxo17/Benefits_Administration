package com.benefits.catalog.controller;

import com.benefits.catalog.exception.PlanNotFoundException;
import com.benefits.catalog.facade.BenefitsCatalogFacade;
import com.benefits.catalog.model.BenefitPlan;

import java.util.Collection;

/**
 * Controller for the Benefits Plan Catalog subsystem.
 *
 * GRASP - Controller:
 * This class is the designated handler for all incoming
 * requests related to benefit plans. It receives requests
 * from the outside world (UI, API, other subsystems) and
 * delegates them to the Facade — it never performs business
 * logic or data logic itself.
 *
 * Think of this as the "traffic director" for the catalog —
 * it receives, validates, and routes. Nothing more.
 *
 * GRASP - Low Coupling:
 * The controller only knows about the Facade. Zero knowledge
 * of the factory, DAO, strategy, or plan implementations.
 *
 * GRASP - High Cohesion:
 * Each method handles exactly one type of incoming request.
 * Clean, focused, and independently testable.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Only responsible for receiving requests, validating inputs,
 * and delegating to the facade. No business logic here.
 *
 * SOLID - Open/Closed Principle (OCP):
 * New request types are added as new methods without
 * modifying any existing handler methods.
 */
public class CatalogController {

    // ── Facade Reference ──────────────────────────────────────────────────────

    /**
     * The facade this controller delegates all requests to.
     * The controller never bypasses the facade.
     */
    private final BenefitsCatalogFacade catalogFacade;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * Constructs the controller with the catalog facade.
     *
     * @param catalogFacade The BenefitsCatalogFacade instance.
     */
    public CatalogController(BenefitsCatalogFacade catalogFacade) {
        this.catalogFacade = catalogFacade;
        System.out.println("[CatalogController] CatalogController initialized.");
    }

    // ── Request Handlers ──────────────────────────────────────────────────────

    /**
     * Handles an incoming request to add a new benefit plan
     * to the catalog.
     *
     * Validates required fields before delegating to the facade.
     * The facade internally uses the factory to create the
     * correct plan type.
     *
     * @param planType            Type of plan to create.
     * @param planId              Unique plan identifier.
     * @param planName            Display name of the plan.
     * @param coverageDetails     What the plan covers.
     * @param coverageLimit       Maximum coverage amount.
     * @param baseCost            Base premium cost.
     * @param providerName        Name of the provider.
     * @param eligibilityCriteria Who qualifies for this plan.
     * @param planDurationMonths  Duration in months.
     */
    public void handleAddPlan(
            BenefitPlan.PlanType planType,
            String planId,
            String planName,
            String coverageDetails,
            double coverageLimit,
            double baseCost,
            String providerName,
            String eligibilityCriteria,
            int planDurationMonths) {

        System.out.println("\n[CatalogController] Handling → ADD plan "
                + "| Type: " + planType
                + " | ID: " + planId);

        // ── Input Validation ─────────────────────────────────────────────────
        if (planId == null || planId.isBlank()) {
            System.out.println("[CatalogController] ERROR: Plan ID is required.");
            return;
        }
        if (planName == null || planName.isBlank()) {
            System.out.println("[CatalogController] ERROR: Plan name is required.");
            return;
        }
        if (planType == null) {
            System.out.println("[CatalogController] ERROR: Plan type is required.");
            return;
        }
        if (baseCost <= 0) {
            System.out.println("[CatalogController] ERROR: Base cost must "
                    + "be greater than zero.");
            return;
        }
        if (coverageLimit <= 0) {
            System.out.println("[CatalogController] ERROR: Coverage limit must "
                    + "be greater than zero.");
            return;
        }

        try {
            // Delegate to facade — factory handles plan creation internally
            catalogFacade.addPlan(
                    planType, planId, planName, coverageDetails,
                    coverageLimit, baseCost, providerName,
                    eligibilityCriteria, planDurationMonths);

            System.out.println("[CatalogController] SUCCESS: Plan added "
                    + "| ID: " + planId);

        } catch (IllegalArgumentException e) {
            System.out.println("[CatalogController] FAILED: " + e.getMessage());
        }
    }

    /**
     * Handles an incoming request to retrieve a plan by ID.
     *
     * @param planId The unique plan ID to look up.
     * @return The matching BenefitPlan, or null if not found.
     */
    public BenefitPlan handleGetPlan(String planId) {

        System.out.println("\n[CatalogController] Handling → GET plan "
                + "for ID: " + planId);

        // ── Input Validation ─────────────────────────────────────────────────
        if (planId == null || planId.isBlank()) {
            System.out.println("[CatalogController] ERROR: Plan ID is required.");
            return null;
        }

        try {
            BenefitPlan plan = catalogFacade.getPlan(planId);
            System.out.println("[CatalogController] SUCCESS: Plan retrieved "
                    + "| ID: " + planId);
            return plan;

        } catch (PlanNotFoundException e) {
            System.out.println("[CatalogController] FAILED: " + e.toString());
            return null;
        }
    }

    /**
     * Handles an incoming request to retrieve all plans
     * of a specific type from the catalog.
     *
     * @param planType The type of plans to retrieve.
     * @return A collection of matching BenefitPlan objects.
     */
    public Collection<BenefitPlan> handleGetPlansByType(
            BenefitPlan.PlanType planType) {

        System.out.println("\n[CatalogController] Handling → GET plans "
                + "by type: " + planType);

        // ── Input Validation ─────────────────────────────────────────────────
        if (planType == null) {
            System.out.println("[CatalogController] ERROR: Plan type is required.");
            return null;
        }

        Collection<BenefitPlan> plans = catalogFacade.getPlansByType(planType);

        System.out.println("[CatalogController] SUCCESS: Retrieved "
                + plans.size() + " plan(s) of type: " + planType);

        return plans;
    }

    /**
     * Handles an incoming request to retrieve all plans
     * currently available in the catalog.
     *
     * @return A collection of all BenefitPlan objects.
     */
    public Collection<BenefitPlan> handleGetAllPlans() {

        System.out.println("\n[CatalogController] Handling → GET ALL plans");

        Collection<BenefitPlan> plans = catalogFacade.getAllPlans();

        System.out.println("[CatalogController] SUCCESS: Retrieved "
                + plans.size() + " plan(s) total.");

        return plans;
    }

    /**
     * Handles an incoming request to delete a plan by ID.
     *
     * @param planId The unique plan ID to delete.
     */
    public void handleDeletePlan(String planId) {

        System.out.println("\n[CatalogController] Handling → DELETE plan "
                + "for ID: " + planId);

        // ── Input Validation ─────────────────────────────────────────────────
        if (planId == null || planId.isBlank()) {
            System.out.println("[CatalogController] ERROR: Plan ID is required.");
            return;
        }

        try {
            catalogFacade.deletePlan(planId);
            System.out.println("[CatalogController] SUCCESS: Plan deleted "
                    + "| ID: " + planId);

        } catch (PlanNotFoundException e) {
            System.out.println("[CatalogController] FAILED: " + e.toString());
        }
    }

    /**
     * Handles an incoming request to calculate the final
     * cost of a specific benefit plan.
     *
     * @param planId The unique plan ID to calculate cost for.
     * @return The final calculated cost, or -1 on failure.
     */
    public double handleCalculatePlanCost(String planId) {

        System.out.println("\n[CatalogController] Handling → CALCULATE cost "
                + "for Plan ID: " + planId);

        // ── Input Validation ─────────────────────────────────────────────────
        if (planId == null || planId.isBlank()) {
            System.out.println("[CatalogController] ERROR: Plan ID is required.");
            return -1;
        }

        try {
            double cost = catalogFacade.calculatePlanCost(planId);
            System.out.println("[CatalogController] SUCCESS: Final cost for "
                    + planId + " = " + cost);
            return cost;

        } catch (PlanNotFoundException e) {
            System.out.println("[CatalogController] FAILED: " + e.toString());
            return -1;
        }
    }
}
