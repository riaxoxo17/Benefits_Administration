package main.java.com.benefits.eligibility.service;

import main.java.com.benefits.eligibility.exception.EligibilityCheckFailedException;
import main.java.com.benefits.eligibility.exception.NotEligibleForPlanException;

/**
 * Contract for the Eligibility Verification Engine.
 *
 * This interface defines what the eligibility module can do.
 * All other modules (EnrollmentFacade, EnrollmentService) depend
 * on this interface — never on the concrete implementation.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * High-level modules (Enrollment) depend on this abstraction.
 * Low-level module (EligibilityServiceImpl with its strategies)
 * implements it. The two are fully decoupled.
 *
 * SOLID - Interface Segregation Principle (ISP):
 * Only the methods that consumers actually need are defined here.
 * No unnecessary methods forced on implementors.
 *
 * GRASP - Controller:
 * This service acts as the use-case controller for all
 * eligibility verification operations.
 *
 * GRASP - Indirection:
 * The EnrollmentFacade talks to this interface rather than to
 * the eligibility strategies directly. This keeps coupling low
 * and makes the eligibility engine swappable.
 *
 * Integration Contract:
 * Defined by Prerana, consumed by EnrollmentFacade (Prerana)
 * and optionally by Ria's claims pre-check.
 */
public interface EligibilityService {

    /**
     * Verifies whether an employee is eligible for a specific plan.
     *
     * Runs ALL registered eligibility strategies in sequence.
     * If any strategy fails, throws NotEligibleForPlanException.
     * If the employee or plan cannot be found, throws
     * EligibilityCheckFailedException.
     *
     * On success, returns true — meaning the employee passed
     * ALL eligibility rules for the requested plan.
     *
     * @param employeeId The ID of the employee to check.
     * @param planId     The ID of the plan to check eligibility for.
     * @return true if the employee is eligible for the plan.
     * @throws EligibilityCheckFailedException if the check cannot run
     *         (employee/plan not found, or system error).
     * @throws NotEligibleForPlanException if the employee fails any
     *         eligibility rule for the plan.
     */
    boolean checkEligibility(String employeeId, String planId)
            throws EligibilityCheckFailedException,
                   NotEligibleForPlanException;
}
