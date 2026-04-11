package main.java.com.benefits.enrollment.controller;

import main.java.com.benefits.enrollment.facade.EnrollmentFacade;
import main.java.com.benefits.enrollment.model.Enrollment;
import main.java.com.benefits.enrollment.exception.DuplicateEnrollmentException;
import main.java.com.benefits.enrollment.exception.InvalidDependentDataException;
import main.java.com.benefits.eligibility.exception.EligibilityCheckFailedException;
import main.java.com.benefits.eligibility.exception.NotEligibleForPlanException;
import main.java.com.benefits.facade.BenefitsProfileFacade;
import main.java.com.benefits.catalog.facade.BenefitsCatalogFacade;
import com.benefits.events.BenefitsEventPublisher;

import java.util.Collection;

/**
 * Controller for the Enrollment and Eligibility subsystem.
 *
 * This is the outermost layer — it handles incoming requests
 * (from a REST endpoint, CLI, or another module) and delegates
 * everything to the EnrollmentFacade.
 *
 * The controller:
 * - Receives raw inputs (employee ID, plan ID, etc.)
 * - Calls the facade
 * - Catches exceptions and prints meaningful responses
 * - Does NOT contain any business logic itself
 *
 * GRASP - Controller:
 * This class is the named controller for all enrollment-related
 * system operations. It is the first object beyond the UI layer
 * that handles enrollment requests.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Only responsible for receiving input and responding.
 * All logic is in the facade and services below.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * Depends on EnrollmentFacade — never on service/DAO internals.
 *
 * Consistent with the style of ProfileController (Poorav)
 * and CatalogController (Poorav) already in the codebase.
 */
public class EnrollmentController {

    // ── Facade Reference ──────────────────────────────────────────────────────

    /**
     * The single entry point into Prerana's subsystem.
     * The controller only talks to the facade — nothing else.
     */
    private final EnrollmentFacade enrollmentFacade;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * Constructs the EnrollmentController.
     * Internally builds the EnrollmentFacade by wiring all dependencies.
     *
     * @param profileFacade  Poorav's profile facade.
     * @param catalogFacade  Poorav's catalog facade.
     * @param eventPublisher Vraj's event publisher.
     */
    public EnrollmentController(BenefitsProfileFacade profileFacade,
                                  BenefitsCatalogFacade catalogFacade,
                                  BenefitsEventPublisher eventPublisher) {
        this.enrollmentFacade = new EnrollmentFacade(
                profileFacade, catalogFacade, eventPublisher);

        System.out.println("[EnrollmentController] Ready.\n");
    }

    // ── Submit Enrollment ─────────────────────────────────────────────────────

    /**
     * Handles an enrollment submission request.
     * Delegates to the facade and catches all known exceptions.
     *
     * @param employeeId    The employee enrolling.
     * @param planId        The plan to enroll in.
     * @param dependentInfo Dependents, or "None".
     * @param submittedBy   The actor submitting the request.
     */
    public void handleEnrollmentRequest(String employeeId, String planId,
                                         String dependentInfo,
                                         String submittedBy) {

        System.out.println("\n══════════════════════════════════════════");
        System.out.println("[EnrollmentController] Enrollment Request");
        System.out.println("  Employee : " + employeeId);
        System.out.println("  Plan     : " + planId);
        System.out.println("  Dependents: " + dependentInfo);
        System.out.println("══════════════════════════════════════════");

        try {
            Enrollment enrollment = enrollmentFacade.submitEnrollment(
                    employeeId, planId, dependentInfo, submittedBy);

            System.out.println("\n[EnrollmentController] ✓ SUCCESS");
            System.out.println(enrollment);

        } catch (InvalidDependentDataException e) {
            System.out.println("\n[EnrollmentController] ✗ INVALID DEPENDENT DATA");
            System.out.println("  → " + e.getMessage());

        } catch (DuplicateEnrollmentException e) {
            System.out.println("\n[EnrollmentController] ✗ DUPLICATE ENROLLMENT");
            System.out.println("  → " + e.getMessage());

        } catch (NotEligibleForPlanException e) {
            System.out.println("\n[EnrollmentController] ✗ NOT ELIGIBLE");
            System.out.println("  → " + e.getMessage());

        } catch (EligibilityCheckFailedException e) {
            System.out.println("\n[EnrollmentController] ✗ ELIGIBILITY CHECK FAILED");
            System.out.println("  → " + e.getMessage());

        } catch (Exception e) {
            System.out.println("\n[EnrollmentController] ✗ UNEXPECTED ERROR");
            System.out.println("  → " + e.getMessage());
        }
    }

    // ── Check Eligibility ─────────────────────────────────────────────────────

    /**
     * Handles an eligibility check request (without enrolling).
     * Useful for letting an employee check before they apply.
     *
     * @param employeeId The employee to check.
     * @param planId     The plan to check eligibility for.
     */
    public void handleEligibilityCheck(String employeeId, String planId) {

        System.out.println("\n══════════════════════════════════════════");
        System.out.println("[EnrollmentController] Eligibility Check");
        System.out.println("  Employee : " + employeeId);
        System.out.println("  Plan     : " + planId);
        System.out.println("══════════════════════════════════════════");

        try {
            boolean eligible = enrollmentFacade.checkEligibility(
                    employeeId, planId);

            if (eligible) {
                System.out.println("\n[EnrollmentController] ✓ ELIGIBLE for plan "
                        + planId);
            }

        } catch (NotEligibleForPlanException e) {
            System.out.println("\n[EnrollmentController] ✗ NOT ELIGIBLE");
            System.out.println("  → " + e.getMessage());

        } catch (EligibilityCheckFailedException e) {
            System.out.println("\n[EnrollmentController] ✗ CHECK FAILED");
            System.out.println("  → " + e.getMessage());
        }
    }

    // ── View Enrollments ──────────────────────────────────────────────────────

    /**
     * Prints all enrollments for a given employee.
     *
     * @param employeeId The employee whose enrollments to display.
     */
    public void displayEnrollmentsForEmployee(String employeeId) {
        System.out.println("\n[EnrollmentController] Enrollments for: "
                + employeeId);

        Collection<Enrollment> enrollments =
                enrollmentFacade.getEnrollmentsForEmployee(employeeId);

        if (enrollments.isEmpty()) {
            System.out.println("  → No enrollments found.");
        } else {
            enrollments.forEach(System.out::println);
        }
    }

    /**
     * Prints all enrollments in the system.
     * Useful for HR admin or Ria's approval engine.
     */
    public void displayAllEnrollments() {
        System.out.println("\n[EnrollmentController] All Enrollments:");

        Collection<Enrollment> enrollments =
                enrollmentFacade.getAllEnrollments();

        if (enrollments.isEmpty()) {
            System.out.println("  → No enrollments in the system.");
        } else {
            enrollments.forEach(System.out::println);
        }
    }
}
