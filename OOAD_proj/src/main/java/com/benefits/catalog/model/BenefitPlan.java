package main.java.com.benefits.catalog.model;

/**
 * Core interface representing a Benefit Plan in the system.
 *
 * SOLID - Interface Segregation Principle (ISP):
 * This interface is intentionally focused — it only defines
 * what every benefit plan must be able to do. No unnecessary
 * methods are forced onto implementing classes.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * All higher-level components (catalog, factory, controller)
 * depend on this abstraction, never on concrete plan classes.
 * This means we can add new plan types without touching
 * any existing code.
 *
 * SOLID - Open/Closed Principle (OCP):
 * New plan types (e.g., VisionPlan, PetInsurancePlan) can be
 * added by simply implementing this interface — zero changes
 * to existing classes required.
 *
 * GRASP - Low Coupling:
 * By depending on this interface, the rest of the system
 * remains completely decoupled from specific plan types.
 */
public interface BenefitPlan {

    /**
     * Plan types supported by the system.
     *
     * SOLID - OCP: New plan types can be added here
     * without modifying existing plan implementations.
     */
    enum PlanType {
        HEALTH,
        DENTAL,
        RETIREMENT,
        INSURANCE
    }

    // ── Identity ──────────────────────────────────────────────────────────────

    /**
     * Returns the unique identifier for this plan.
     *
     * @return The plan ID.
     */
    String getPlanId();

    /**
     * Returns the display name of this plan.
     *
     * @return The plan name.
     */
    String getPlanName();

    /**
     * Returns the type of this plan.
     *
     * @return The PlanType enum value.
     */
    PlanType getPlanType();

    // ── Coverage ──────────────────────────────────────────────────────────────

    /**
     * Returns a description of what this plan covers.
     *
     * @return Coverage details as a string.
     */
    String getCoverageDetails();

    /**
     * Returns the maximum coverage limit for this plan.
     *
     * @return Coverage limit as a double.
     */
    double getCoverageLimit();

    /**
     * Returns the duration of this plan in months.
     *
     * @return Plan duration in months.
     */
    int getPlanDurationMonths();

    // ── Cost ──────────────────────────────────────────────────────────────────

    /**
     * Returns the base cost/premium for this plan.
     *
     * @return Base cost as a double.
     */
    double getBaseCost();

    /**
     * Calculates and returns the final cost of this plan
     * using its assigned cost calculation strategy.
     *
     * @return Calculated final cost as a double.
     */
    double calculateFinalCost();

    // ── Provider ──────────────────────────────────────────────────────────────

    /**
     * Returns the name of the plan provider.
     *
     * @return Provider name as a string.
     */
    String getProviderName();

    // ── Eligibility ───────────────────────────────────────────────────────────

    /**
     * Returns the eligibility criteria for this plan.
     *
     * @return Eligibility criteria as a string.
     */
    String getEligibilityCriteria();

    // ── Display ───────────────────────────────────────────────────────────────

    /**
     * Returns a human-readable summary of this plan.
     *
     * @return Formatted plan details as a string.
     */
    String toString();
}