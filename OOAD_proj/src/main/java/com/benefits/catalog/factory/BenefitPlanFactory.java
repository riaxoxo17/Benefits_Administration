package main.java.com.benefits.catalog.factory;

import main.java.com.benefits.catalog.model.BenefitPlan;

/**
 * Factory interface for creating BenefitPlan objects.
 *
 * CREATIONAL PATTERN - Factory Method:
 * Defines the contract for creating benefit plans without
 * exposing the instantiation logic to the outside world.
 * Callers request a plan by type — they never directly
 * instantiate HealthPlan, DentalPlan, etc. themselves.
 *
 * This decouples plan creation from plan usage entirely.
 * If we rename, restructure, or replace a plan class,
 * nothing outside the factory needs to change.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * High-level components depend on this factory interface,
 * not on the concrete factory implementation. The concrete
 * factory can be swapped freely (e.g., for testing with
 * a mock factory).
 *
 * SOLID - Open/Closed Principle (OCP):
 * Adding a new plan type only requires updating the
 * factory implementation — not this interface, and not
 * any class that uses it.
 *
 * GRASP - Creator:
 * The factory is the designated creator of BenefitPlan
 * objects. Centralizing creation here ensures consistency
 * and makes the codebase easy to maintain.
 */
public interface BenefitPlanFactory {

    /**
     * Creates and returns a BenefitPlan of the specified type
     * with the provided attributes.
     *
     * The factory decides which concrete class to instantiate
     * based on the planType — callers don't need to know.
     *
     * @param planType            The type of plan to create
     *                            (HEALTH, DENTAL, RETIREMENT, INSURANCE).
     * @param planId              Unique identifier for the plan.
     * @param planName            Display name of the plan.
     * @param coverageDetails     Description of coverage.
     * @param coverageLimit       Maximum coverage amount.
     * @param baseCost            Base premium cost.
     * @param providerName        Name of the plan provider.
     * @param eligibilityCriteria Eligibility criteria for the plan.
     * @param planDurationMonths  Duration of the plan in months.
     * @return A fully constructed BenefitPlan instance.
     * @throws IllegalArgumentException if planType is unknown.
     */
    BenefitPlan createPlan(
            BenefitPlan.PlanType planType,
            String planId,
            String planName,
            String coverageDetails,
            double coverageLimit,
            double baseCost,
            String providerName,
            String eligibilityCriteria,
            int planDurationMonths);
}
