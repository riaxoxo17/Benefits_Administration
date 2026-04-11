package main.java.com.benefits.enrollment.model;

import java.time.LocalDateTime;

/**
 * Represents a single employee's enrollment in a benefit plan.
 *
 * This is the core data entity for Prerana's module.
 * It tracks who enrolled, in which plan, when, what their
 * dependent information is, and the current status of the
 * enrollment in the approval workflow.
 *
 * GRASP - Information Expert:
 * This class owns and manages all enrollment data. Any logic
 * that needs enrollment information asks this object.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Solely responsible for holding and representing enrollment data.
 * No business logic lives here — just data and its toString().
 *
 * Data Entity: Enrollment
 * Fields: enrollmentId, employeeId, planId, status,
 *         dependentInfo, enrollmentDate, submittedBy
 */
public class Enrollment {

    // ── Status Enum ──────────────────────────────────────────────────────────

    /**
     * The possible states of an enrollment in the approval workflow.
     *
     * PENDING  → submitted, awaiting manager review
     * APPROVED → manager approved, payroll deductions will begin
     * REJECTED → manager rejected, no deductions will occur
     *
     * SOLID - OCP: New states can be added without changing existing logic.
     * NOTE: The workflow transitions (PENDING → APPROVED/REJECTED) are
     * owned by Ria's Approval Workflow Engine. This enum just holds the
     * values — the state machine is in Ria's module.
     */
    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

    // ── Fields ───────────────────────────────────────────────────────────────

    private final String enrollmentId;
    private final String employeeId;
    private final String planId;
    private Status status;
    private final String dependentInfo;
    private final LocalDateTime enrollmentDate;
    private final String submittedBy;

    // ── Constructor ──────────────────────────────────────────────────────────

    /**
     * Creates a new Enrollment.
     * Status defaults to PENDING — all enrollments start as pending
     * and must go through Ria's approval workflow.
     *
     * @param enrollmentId  Unique identifier for this enrollment.
     * @param employeeId    The employee who is enrolling.
     * @param planId        The plan they are enrolling in.
     * @param dependentInfo Comma-separated dependent names/relationships,
     *                      or "None" if enrolling without dependents.
     * @param submittedBy   The actor who submitted the enrollment
     *                      (usually the employee's own ID, or "SYSTEM").
     */
    public Enrollment(String enrollmentId, String employeeId,
                      String planId, String dependentInfo,
                      String submittedBy) {
        this.enrollmentId  = enrollmentId;
        this.employeeId    = employeeId;
        this.planId        = planId;
        this.dependentInfo = dependentInfo;
        this.submittedBy   = submittedBy;
        this.enrollmentDate = LocalDateTime.now();
        this.status        = Status.PENDING; // always starts PENDING
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public String getEnrollmentId()   { return enrollmentId; }
    public String getEmployeeId()     { return employeeId; }
    public String getPlanId()         { return planId; }
    public Status getStatus()         { return status; }
    public String getDependentInfo()  { return dependentInfo; }
    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public String getSubmittedBy()    { return submittedBy; }

    // ── Setter for status ────────────────────────────────────────────────────

    /**
     * Updates the enrollment status.
     * Called by Ria's Approval Workflow Engine when a decision is made.
     *
     * @param status The new status (APPROVED or REJECTED).
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    // ── Display ──────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Enrollment {" +
                "\n  Enrollment ID  : " + enrollmentId +
                "\n  Employee ID    : " + employeeId +
                "\n  Plan ID        : " + planId +
                "\n  Status         : " + status +
                "\n  Dependents     : " + dependentInfo +
                "\n  Submitted By   : " + submittedBy +
                "\n  Enrolled At    : " + enrollmentDate +
                "\n}";
    }
}
