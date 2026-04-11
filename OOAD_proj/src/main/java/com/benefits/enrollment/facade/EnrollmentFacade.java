package main.java.com.benefits.enrollment.facade;

import main.java.com.benefits.enrollment.dao.EnrollmentDAO;
import main.java.com.benefits.enrollment.dao.EnrollmentDAOImpl;
import main.java.com.benefits.enrollment.model.Enrollment;
import main.java.com.benefits.enrollment.service.EnrollmentService;
import main.java.com.benefits.enrollment.service.EnrollmentServiceImpl;
import main.java.com.benefits.enrollment.exception.DuplicateEnrollmentException;
import main.java.com.benefits.enrollment.exception.InvalidDependentDataException;
import main.java.com.benefits.eligibility.service.EligibilityService;
import main.java.com.benefits.eligibility.service.EligibilityServiceImpl;
import main.java.com.benefits.eligibility.strategy.EmploymentTypeEligibilityStrategy;
import main.java.com.benefits.eligibility.strategy.SalaryBandEligibilityStrategy;
import main.java.com.benefits.eligibility.strategy.WaitingPeriodEligibilityStrategy;
import main.java.com.benefits.eligibility.exception.EligibilityCheckFailedException;
import main.java.com.benefits.eligibility.exception.NotEligibleForPlanException;
import main.java.com.benefits.facade.BenefitsProfileFacade;
import main.java.com.benefits.catalog.facade.BenefitsCatalogFacade;
import com.benefits.events.BenefitsEventPublisher;

import java.util.Collection;

/**
 * Facade for the entire Eligibility + Enrollment subsystem.
 *
 * This is Prerana's main entry point. External components —
 * Ria's Approval Engine, the frontend, or any other module —
 * should ONLY interact with this class.
 *
 * They never touch:
 * - EligibilityServiceImpl directly
 * - EnrollmentServiceImpl directly
 * - EnrollmentDAO directly
 * - Individual eligibility strategies
 *
 * This class wires everything together internally and exposes
 * a clean, simple API.
 *
 * STRUCTURAL PATTERN - Facade:
 * Hides the complexity of the eligibility + enrollment subsystem
 * behind a single clean interface.
 *
 * Inside, this facade coordinates:
 * - BenefitsProfileFacade (Poorav) — to fetch employee data
 * - BenefitsCatalogFacade (Poorav) — to fetch plan data
 * - EligibilityServiceImpl + strategies — to verify eligibility
 * - EnrollmentServiceImpl — to submit and store the enrollment
 * - BenefitsEventPublisher (Vraj) — to fire events to Audit/Notification
 *
 * GRASP - Controller:
 * Acts as the system controller for the Enrollment use case.
 * Receives inputs from the outside world and delegates to the
 * appropriate internal components.
 *
 * GRASP - Low Coupling:
 * All external modules depend only on this facade.
 * Internal details are fully hidden.
 *
 * GRASP - High Cohesion:
 * This class only does one thing: provide a clean API to the
 * eligibility and enrollment subsystem. No logic lives here —
 * just delegation.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Only responsible for wiring the subsystem and delegating calls.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * Accepts Poorav's facades and Vraj's publisher as constructor
 * arguments — never creates them internally. This ensures
 * the entire system can be tested and wired cleanly at startup.
 */
public class EnrollmentFacade {

    // ── Internal Services ─────────────────────────────────────────────────────

    /**
     * Prerana's eligibility service — runs all eligibility strategies.
     */
    private final EligibilityService eligibilityService;

    /**
     * Prerana's enrollment service — handles submission and storage.
     */
    private final EnrollmentService enrollmentService;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * Wires up the entire Eligibility + Enrollment subsystem.
     *
     * All eligibility strategies are registered here.
     * To add a new rule, add it to the EligibilityServiceImpl
     * constructor call below — no other code changes needed.
     *
     * @param profileFacade  Poorav's BenefitsProfileFacade.
     * @param catalogFacade  Poorav's BenefitsCatalogFacade.
     * @param eventPublisher Vraj's BenefitsEventPublisher.
     */
    public EnrollmentFacade(BenefitsProfileFacade profileFacade,
                             BenefitsCatalogFacade catalogFacade,
                             BenefitsEventPublisher eventPublisher) {

        // ── Wire eligibility strategies ───────────────────────────────────────
        // BEHAVIOURAL PATTERN - Strategy:
        // All three strategies are injected here.
        // To add a new rule, just add a new strategy to this list.
        this.eligibilityService = new EligibilityServiceImpl(
                profileFacade,
                catalogFacade,
                new EmploymentTypeEligibilityStrategy(),
                new SalaryBandEligibilityStrategy(),
                new WaitingPeriodEligibilityStrategy()
        );

        // ── Wire enrollment DAO and service ───────────────────────────────────
        EnrollmentDAO enrollmentDAO = new EnrollmentDAOImpl();

        this.enrollmentService = new EnrollmentServiceImpl(
                enrollmentDAO,
                eligibilityService,
                eventPublisher
        );

        System.out.println("[EnrollmentFacade] Initialized.");
        System.out.println("[EnrollmentFacade] Eligibility + Enrollment subsystem ready.\n");
    }

    // ── Public API ────────────────────────────────────────────────────────────

    /**
     * Submits an enrollment request for an employee into a benefit plan.
     *
     * This is the primary entry point for the entire enrollment flow.
     * Internally validates dependent info, checks for duplicates,
     * runs eligibility checks, saves the enrollment, and fires events.
     *
     * @param employeeId    The ID of the employee enrolling.
     * @param planId        The ID of the benefit plan to enroll in.
     * @param dependentInfo Dependent names/relationships, or "None".
     * @param submittedBy   Who is submitting (employee ID or "SYSTEM").
     * @return The created Enrollment with status PENDING.
     * @throws InvalidDependentDataException  if dependent info is invalid.
     * @throws DuplicateEnrollmentException   if already enrolled in this plan.
     * @throws EligibilityCheckFailedException if eligibility check errors.
     * @throws NotEligibleForPlanException     if not eligible for the plan.
     */
    public Enrollment submitEnrollment(String employeeId, String planId,
                                        String dependentInfo, String submittedBy)
            throws InvalidDependentDataException,
                   DuplicateEnrollmentException,
                   EligibilityCheckFailedException,
                   NotEligibleForPlanException {

        System.out.println("[EnrollmentFacade] Received → submitEnrollment() "
                + "| Employee: " + employeeId
                + " | Plan: " + planId);

        return enrollmentService.submitEnrollment(
                employeeId, planId, dependentInfo, submittedBy);
    }

    /**
     * Checks whether an employee is eligible for a plan without enrolling.
     * Useful for a "check before apply" flow in the UI.
     *
     * @param employeeId The ID of the employee to check.
     * @param planId     The ID of the plan to check eligibility for.
     * @return true if eligible.
     * @throws EligibilityCheckFailedException if the check cannot run.
     * @throws NotEligibleForPlanException     if not eligible.
     */
    public boolean checkEligibility(String employeeId, String planId)
            throws EligibilityCheckFailedException,
                   NotEligibleForPlanException {

        System.out.println("[EnrollmentFacade] Received → checkEligibility() "
                + "| Employee: " + employeeId
                + " | Plan: " + planId);

        return eligibilityService.checkEligibility(employeeId, planId);
    }

    /**
     * Retrieves all enrollments for a specific employee.
     *
     * @param employeeId The employee whose enrollments to retrieve.
     * @return Collection of Enrollment records for that employee.
     */
    public Collection<Enrollment> getEnrollmentsForEmployee(String employeeId) {
        System.out.println("[EnrollmentFacade] Received → getEnrollmentsForEmployee() "
                + "| Employee: " + employeeId);
        return enrollmentService.getEnrollmentsForEmployee(employeeId);
    }

    /**
     * Retrieves all enrollments in the system.
     *
     * @return Collection of all Enrollment records.
     */
    public Collection<Enrollment> getAllEnrollments() {
        System.out.println("[EnrollmentFacade] Received → getAllEnrollments()");
        return enrollmentService.getAllEnrollments();
    }

    /**
     * Retrieves a specific enrollment by its ID.
     *
     * @param enrollmentId The unique enrollment ID.
     * @return The matching Enrollment, or null if not found.
     */
    public Enrollment getEnrollmentById(String enrollmentId) {
        System.out.println("[EnrollmentFacade] Received → getEnrollmentById() "
                + "| ID: " + enrollmentId);
        return enrollmentService.getEnrollmentById(enrollmentId);
    }
}
