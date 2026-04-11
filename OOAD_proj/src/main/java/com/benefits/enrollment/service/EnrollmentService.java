package main.java.com.benefits.enrollment.service;

import main.java.com.benefits.enrollment.model.Enrollment;
import main.java.com.benefits.enrollment.exception.DuplicateEnrollmentException;
import main.java.com.benefits.enrollment.exception.InvalidDependentDataException;
import main.java.com.benefits.eligibility.exception.EligibilityCheckFailedException;
import main.java.com.benefits.eligibility.exception.NotEligibleForPlanException;

import java.util.Collection;

/**
 * Contract for the Employee Self-Enrollment Module.
 *
 * Defines what the enrollment service can do. The EnrollmentFacade
 * depends on this interface — never on the concrete implementation.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * EnrollmentFacade depends on this abstraction. The concrete
 * EnrollmentServiceImpl is injected at construction time.
 *
 * SOLID - Interface Segregation Principle (ISP):
 * Only the methods that callers need are defined here.
 *
 * GRASP - Controller:
 * This service is the use-case controller for all enrollment
 * submission and retrieval operations.
 *
 * Integration Contract:
 * Defined and owned by Prerana.
 * Consumed by EnrollmentFacade (Prerana) and
 * potentially Ria's Approval Engine (to read enrollment records).
 */
public interface EnrollmentService {

    /**
     * Submits a new enrollment for an employee into a benefit plan.
     *
     * This method:
     * 1. Validates dependent information.
     * 2. Checks for duplicate enrollment.
     * 3. Verifies eligibility via EligibilityService.
     * 4. Saves the enrollment with PENDING status.
     * 5. Fires an event to the Observer bus (Vraj's audit + notification).
     *
     * @param employeeId    The employee enrolling.
     * @param planId        The plan they are enrolling in.
     * @param dependentInfo Dependent details (or "None").
     * @param submittedBy   Who submitted (employee ID or "SYSTEM").
     * @return The created Enrollment with status PENDING.
     * @throws InvalidDependentDataException if dependent info is invalid.
     * @throws DuplicateEnrollmentException if already enrolled in this plan.
     * @throws EligibilityCheckFailedException if the eligibility check errors.
     * @throws NotEligibleForPlanException if the employee is not eligible.
     */
    Enrollment submitEnrollment(String employeeId, String planId,
                                String dependentInfo, String submittedBy)
            throws InvalidDependentDataException,
                   DuplicateEnrollmentException,
                   EligibilityCheckFailedException,
                   NotEligibleForPlanException;

    /**
     * Retrieves all enrollments for a specific employee.
     *
     * @param employeeId The employee whose enrollments to retrieve.
     * @return Collection of all Enrollment records for that employee.
     */
    Collection<Enrollment> getEnrollmentsForEmployee(String employeeId);

    /**
     * Retrieves a specific enrollment by its ID.
     *
     * @param enrollmentId The unique enrollment ID.
     * @return The matching Enrollment, or null if not found.
     */
    Enrollment getEnrollmentById(String enrollmentId);

    /**
     * Retrieves all enrollments in the system.
     * Useful for HR admin views or Ria's approval engine.
     *
     * @return Collection of all Enrollment records.
     */
    Collection<Enrollment> getAllEnrollments();
}
