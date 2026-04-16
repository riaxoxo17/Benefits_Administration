package main.java.com.benefits.enrollment.service;

import main.java.com.benefits.enrollment.dao.EnrollmentDAO;
import main.java.com.benefits.enrollment.model.Enrollment;
import main.java.com.benefits.enrollment.exception.DuplicateEnrollmentException;
import main.java.com.benefits.enrollment.exception.InvalidDependentDataException;
import main.java.com.benefits.eligibility.service.EligibilityService;
import main.java.com.benefits.eligibility.exception.EligibilityCheckFailedException;
import main.java.com.benefits.eligibility.exception.NotEligibleForPlanException;
import com.benefits.events.BenefitsEventPublisher;
import com.benefits.events.EnrollmentApprovedEvent;

import java.util.Collection;
import java.util.UUID;

/**
 * Concrete implementation of EnrollmentService.
 *
 * This class is the Employee Self-Enrollment Module.
 * It orchestrates the full enrollment submission flow:
 * validate → check duplicate → check eligibility → save → notify.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This class is solely responsible for the enrollment submission
 * workflow. Eligibility logic lives in EligibilityService.
 * Data storage lives in EnrollmentDAO. Event publishing lives
 * in BenefitsEventPublisher (Vraj's module).
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * This class depends on:
 *   - EnrollmentDAO interface (not the concrete impl)
 *   - EligibilityService interface (not the concrete impl)
 *   - BenefitsEventPublisher (Vraj's observer bus)
 * All injected via constructor — never created internally.
 *
 * SOLID - Open/Closed Principle (OCP):
 * Adding new validation rules or post-enrollment actions only
 * requires extending this class or injecting new collaborators.
 * The core flow is not modified.
 *
 * GRASP - Controller:
 * Coordinates the use case: enrollment submission.
 * Delegates each responsibility to the appropriate expert.
 *
 * GRASP - Creator:
 * Creates Enrollment objects — it directly uses and manages them.
 *
 * BEHAVIOURAL PATTERN - Observer (consumer):
 * After a successful enrollment submission, this class publishes
 * an EnrollmentApprovedEvent to Vraj's BenefitsEventPublisher.
 * Vraj's AuditEventListener and NotificationEventListener react
 * independently — this class never calls them directly.
 * This is the Observer pattern in action.
 */
public class EnrollmentServiceImpl implements EnrollmentService {

    // ── Dependencies ─────────────────────────────────────────────────────────

    private final EnrollmentDAO enrollmentDAO;
    private final EligibilityService eligibilityService;
    private final BenefitsEventPublisher eventPublisher;

    // ── Constructor ──────────────────────────────────────────────────────────

    /**
     * Constructs EnrollmentServiceImpl with all dependencies injected.
     *
     * @param enrollmentDAO      The DAO for enrollment persistence.
     * @param eligibilityService Prerana's eligibility engine.
     * @param eventPublisher     Vraj's Observer event bus.
     */
    public EnrollmentServiceImpl(EnrollmentDAO enrollmentDAO,
                                  EligibilityService eligibilityService,
                                  BenefitsEventPublisher eventPublisher) {
        this.enrollmentDAO = enrollmentDAO;
        this.eligibilityService = eligibilityService;
        this.eventPublisher = eventPublisher;

        System.out.println("[EnrollmentService] Initialized.");
    }

    // ── Submit Enrollment ────────────────────────────────────────────────────

    /**
     * Submits a new enrollment. Full workflow:
     * 1. Validate dependent information.
     * 2. Check for duplicate enrollment.
     * 3. Run eligibility check (calls EligibilityService).
     * 4. Create Enrollment with PENDING status and save.
     * 5. Publish EnrollmentApprovedEvent to Observer bus.
     *
     * Note: We publish an event here to trigger audit logging
     * and notifications. The enrollment is PENDING at this point —
     * the actual APPROVED status change is handled by Ria's engine.
     * The event name is used here to signal "submitted for approval".
     */
    @Override
    public Enrollment submitEnrollment(String employeeId, String planId,
                                        String dependentInfo, String submittedBy)
            throws InvalidDependentDataException,
                   DuplicateEnrollmentException,
                   EligibilityCheckFailedException,
                   NotEligibleForPlanException {

        System.out.println("\n[EnrollmentService] Processing enrollment: "
                + "Employee [" + employeeId
                + "] → Plan [" + planId + "]");

        // ── Step 1: Validate dependent info ──────────────────────────────────
        validateDependentInfo(dependentInfo);

        // ── Step 2: Check for duplicate enrollment ────────────────────────────
        if (enrollmentDAO.existsByEmployeeAndPlan(employeeId, planId)) {
            throw new DuplicateEnrollmentException(employeeId, planId);
        }

        // ── Step 3: Eligibility check ─────────────────────────────────────────
        // This calls EligibilityServiceImpl which runs all strategies.
        // If any strategy fails, an exception is thrown and we stop here.
        eligibilityService.checkEligibility(employeeId, planId);

        // ── Step 4: Create and save the enrollment ────────────────────────────
        String enrollmentId = "ENR-" + UUID.randomUUID()
                .toString().substring(0, 8).toUpperCase();

        Enrollment enrollment = new Enrollment(
                enrollmentId, employeeId, planId, dependentInfo, submittedBy);

        enrollmentDAO.save(enrollment);

        System.out.println("[EnrollmentService] ✓ Enrollment saved: "
                + enrollmentId + " | Status: " + enrollment.getStatus());

        // ── Step 5: Publish event to Observer bus ─────────────────────────────
        // Vraj's AuditEventListener and NotificationEventListener will react.
        // We do NOT call them directly — loose coupling via Observer pattern.
        eventPublisher.publish(new EnrollmentApprovedEvent(
                employeeId,
                enrollmentId,
                planId,
                submittedBy
        ));

        return enrollment;
    }

    // ── Retrieval Methods ────────────────────────────────────────────────────

    @Override
    public Collection<Enrollment> getEnrollmentsForEmployee(String employeeId) {
        System.out.println("[EnrollmentService] Fetching enrollments "
                + "for employee: " + employeeId);
        return enrollmentDAO.findByEmployeeId(employeeId);
    }

    @Override
    public Enrollment getEnrollmentById(String enrollmentId) {
        System.out.println("[EnrollmentService] Fetching enrollment: "
                + enrollmentId);
        return enrollmentDAO.findById(enrollmentId);
    }

    @Override
    public Collection<Enrollment> getAllEnrollments() {
        System.out.println("[EnrollmentService] Fetching all enrollments.");
        return enrollmentDAO.findAll();
    }

    // ── Private Validation ───────────────────────────────────────────────────

    /**
     * Validates the dependent information string submitted with the enrollment.
     *
     * Rules:
     * - dependentInfo must not be null.
     * - If not "None", must be at least 3 characters long (a real name).
     *
     * SOLID - SRP: Validation logic is isolated in this private method.
     *
     * @param dependentInfo The dependent info string to validate.
     * @throws InvalidDependentDataException if validation fails.
     */
    private void validateDependentInfo(String dependentInfo)
            throws InvalidDependentDataException {
        if (dependentInfo == null) {
            throw new InvalidDependentDataException(
                    "Dependent information cannot be null. "
                    + "Pass 'None' if enrolling without dependents.");
        }
        if (!dependentInfo.equalsIgnoreCase("None")
                && dependentInfo.trim().length() < 3) {
            throw new InvalidDependentDataException(
                    "Invalid dependent information: '" + dependentInfo
                    + "'. Provide a valid dependent name/relationship "
                    + "or pass 'None'.");
        }
    }
}
