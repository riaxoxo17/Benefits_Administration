package main.java.com.benefits.catalog.facade;

import main.java.com.benefits.catalog.dao.BenefitPlanDAO;
import main.java.com.benefits.catalog.exception.PlanNotFoundException;
import main.java.com.benefits.catalog.factory.BenefitPlanFactory;
import main.java.com.benefits.catalog.factory.BenefitPlanFactoryImpl;
import main.java.com.benefits.catalog.model.BenefitPlan;

import java.util.Collection;

/**
 * Facade for the Benefits Plan Catalog subsystem.
 *
 * STRUCTURAL PATTERN - Facade:
 * Provides a single, clean entry point to the entire catalog
 * subsystem. All external components — other subsystems, UI,
 * APIs, or the Eligibility Engine — interact ONLY with this
 * class. They never touch the factory, DAO, or plan models
 * directly.
 *
 * This is the "front desk" for the catalog — callers simply
 * say what they want, and the facade handles the rest.
 *
 * GRASP - Low Coupling:
 * External components depend only on this facade. Internal
 * details (factory, DAO, strategy) are completely hidden.
 * Swapping any internal component requires zero changes
 * outside this facade.
 *
 * GRASP - High Cohesion:
 * This class only does one thing — provide a clean interface
 * to the catalog subsystem. All actual logic lives in the
 * appropriate internal classes.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Only responsible for delegating calls to the right internal
 * components. No business logic or data logic lives here.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * Accepts BenefitPlanDAO interface at construction time —
 * never a concrete class. The DB team's implementation is
 * injected from outside.
 */
public class BenefitsCatalogFacade {

    // ── Internal Dependencies ─────────────────────────────────────────────────

    /**
     * DAO for all plan data operations.
     * Provided by the DB team at runtime via constructor injection.
     */
    private final BenefitPlanDAO planDAO;

    /**
     * Factory for creating BenefitPlan objects by type.
     * Encapsulates all plan instantiation logic.
     */
    private final BenefitPlanFactory planFactory;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * Initializes the facade with the DAO implementation
     * provided by the database team.
     * Internally wires up the factory — callers don't need
     * to know about it.
     *
     * @param planDAO The BenefitPlanDAO implementation from DB team.
     */
    public BenefitsCatalogFacade(BenefitPlanDAO planDAO) {
        this.planDAO = planDAO;
        this.planFactory = new BenefitPlanFactoryImpl();

        System.out.println("[CatalogFacade] BenefitsCatalogFacade initialized.");
        System.out.println("[CatalogFacade] Connected to DAO: "
                + planDAO.getClass().getSimpleName());
        System.out.println("[CatalogFacade] Ready to accept requests.\n");
    }

    // ── Public API ────────────────────────────────────────────────────────────

    /**
     * Creates a new benefit plan of the specified type and
     * persists it via the DAO.
     *
     * Uses the factory to instantiate the correct plan type
     * with the right cost strategy — caller doesn't need to
     * know which concrete class is being created.
     *
     * @param planType            The type of plan to create.
     * @param planId              Unique plan identifier.
     * @param planName            Display name of the plan.
     * @param coverageDetails     Description of coverage.
     * @param coverageLimit       Maximum coverage amount.
     * @param baseCost            Base premium cost.
     * @param providerName        Name of the plan provider.
     * @param eligibilityCriteria Eligibility criteria.
     * @param planDurationMonths  Duration in months.
     * @throws IllegalArgumentException if plan type is unknown.
     */
    public void addPlan(
            BenefitPlan.PlanType planType,
            String planId,
            String planName,
            String coverageDetails,
            double coverageLimit,
            double baseCost,
            String providerName,
            String eligibilityCriteria,
            int planDurationMonths) {

        System.out.println("[CatalogFacade] Received → addPlan() "
                + "| Type: " + planType
                + " | ID: " + planId);

        // Use factory to create the correct plan type
        BenefitPlan plan = planFactory.createPlan(
                planType, planId, planName, coverageDetails,
                coverageLimit, baseCost, providerName,
                eligibilityCriteria, planDurationMonths);

        // Persist via DAO (DB team's implementation)
        planDAO.save(plan);
    }

    /**
     * Retrieves a benefit plan by its unique ID.
     * Delegates to the DAO to fetch from the database.
     *
     * @param planId The unique plan ID to look up.
     * @return The matching BenefitPlan.
     * @throws PlanNotFoundException if no plan exists for the ID.
     */
    public BenefitPlan getPlan(String planId)
            throws PlanNotFoundException {

        System.out.println("[CatalogFacade] Received → getPlan() "
                + "for ID: " + planId);

        BenefitPlan plan = planDAO.findById(planId);

        if (plan == null) {
            throw new PlanNotFoundException(
                    "No plan found for Plan ID: " + planId);
        }

        return plan;
    }

    /**
     * Retrieves all benefit plans from the catalog.
     *
     * @return A collection of all BenefitPlan objects.
     */
    public Collection<BenefitPlan> getAllPlans() {
        System.out.println("[CatalogFacade] Received → getAllPlans()");
        return planDAO.findAll();
    }

    /**
     * Retrieves all benefit plans of a specific type.
     * For example, all HEALTH plans or all RETIREMENT plans.
     *
     * @param planType The type of plans to retrieve.
     * @return A collection of matching BenefitPlan objects.
     */
    public Collection<BenefitPlan> getPlansByType(
            BenefitPlan.PlanType planType) {

        System.out.println("[CatalogFacade] Received → getPlansByType() "
                + "| Type: " + planType);
        return planDAO.findByType(planType);
    }

    /**
     * Updates an existing benefit plan in the catalog.
     *
     * @param plan The updated BenefitPlan to persist.
     * @throws PlanNotFoundException if the plan doesn't exist.
     */
    public void updatePlan(BenefitPlan plan)
            throws PlanNotFoundException {

        System.out.println("[CatalogFacade] Received → updatePlan() "
                + "for ID: " + plan.getPlanId());

        if (!planDAO.existsById(plan.getPlanId())) {
            throw new PlanNotFoundException(
                    "Cannot update. No plan found for Plan ID: "
                            + plan.getPlanId());
        }

        planDAO.update(plan);
    }

    /**
     * Deletes a benefit plan from the catalog by ID.
     *
     * @param planId The unique plan ID to delete.
     * @throws PlanNotFoundException if the plan doesn't exist.
     */
    public void deletePlan(String planId)
            throws PlanNotFoundException {

        System.out.println("[CatalogFacade] Received → deletePlan() "
                + "for ID: " + planId);

        if (!planDAO.existsById(planId)) {
            throw new PlanNotFoundException(
                    "Cannot delete. No plan found for Plan ID: "
                            + planId);
        }

        planDAO.delete(planId);
    }

    /**
     * Calculates and returns the final cost of a specific plan.
     * Fetches the plan first, then triggers cost calculation
     * using the plan's assigned strategy.
     *
     * @param planId The unique plan ID to calculate cost for.
     * @return The final calculated cost as a double.
     * @throws PlanNotFoundException if the plan doesn't exist.
     */
    public double calculatePlanCost(String planId)
            throws PlanNotFoundException {

        System.out.println("[CatalogFacade] Received → calculatePlanCost() "
                + "for ID: " + planId);

        BenefitPlan plan = getPlan(planId);
        double finalCost = plan.calculateFinalCost();

        System.out.println("[CatalogFacade] Final cost for Plan "
                + planId + ": " + finalCost);

        return finalCost;
    }
}
