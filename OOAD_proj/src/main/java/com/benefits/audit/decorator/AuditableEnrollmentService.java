package com.benefits.audit.decorator;

import com.benefits.audit.model.AuditLog;
import com.benefits.audit.service.AuditService;

/**
 * DECORATOR PATTERN — Wraps any enrollment-related service call
 * and transparently adds audit logging around it.
 *
 * ─────────────────────────────────────────────────────────────────
 * STRUCTURAL PATTERN — Decorator:
 * AuditableEnrollmentService wraps an EnrollmentOperation (a functional
 * interface representing any enrollment action). Callers simply wrap their
 * call with this decorator — audit logging happens automatically,
 * without modifying the original service at all.
 *
 * This implements the Decorator intent:
 *   "Attach additional responsibilities to an object dynamically.
 *    Decorators provide a flexible alternative to subclassing."
 *
 * SOLID - Open/Closed Principle (OCP):
 * The underlying service doesn't change — we extend behavior
 * by wrapping, not by modifying.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This decorator's only job is to add auditing. No business logic.
 *
 * GRASP - Indirection:
 * Instead of every service calling auditService.logAction() manually,
 * this decorator centralizes that responsibility.
 * ─────────────────────────────────────────────────────────────────
 *
 * Usage Example:
 *   AuditableEnrollmentService auditable = new AuditableEnrollmentService(auditService);
 *   auditable.execute("EMP001", "ENROLLMENT", () -> enrollmentService.enroll(request));
 */
public class AuditableEnrollmentService {

    // ── Functional interface for any wrappable operation ──────────────────────

    /**
     * Represents any service operation that can be wrapped with audit logging.
     * Implement as a lambda or anonymous class.
     */
    @FunctionalInterface
    public interface EnrollmentOperation {
        /** Executes the wrapped operation. */
        void execute() throws Exception;
    }

    // ── Core Dependencies ─────────────────────────────────────────────────────

    private final AuditService auditService;

    // ── Constructor ───────────────────────────────────────────────────────────

    public AuditableEnrollmentService(AuditService auditService) {
        this.auditService = auditService;
        System.out.println("[AuditDecorator] AuditableEnrollmentService initialized.");
    }

    // ── Decorated Execute ─────────────────────────────────────────────────────

    /**
     * Executes any EnrollmentOperation while transparently logging the
     * action before AND after (or on failure).
     *
     * @param employeeId    The employee affected.
     * @param actionName    Description of the action for the log entry.
     * @param operation     The actual service operation to execute.
     */
    public void execute(String employeeId,
                        String actionName,
                        EnrollmentOperation operation) {

        System.out.println("[AuditDecorator] Pre-execution audit for: "
                + actionName + " | Employee: " + employeeId);

        // Log "started" before executing
        auditService.logAction(
                employeeId,
                AuditLog.ActionType.ENROLLMENT,
                "SYSTEM",
                actionName + " — started"
        );

        try {
            // Execute the actual wrapped operation
            operation.execute();

            // Log "completed" after success
            auditService.logAction(
                    employeeId,
                    AuditLog.ActionType.ENROLLMENT,
                    "SYSTEM",
                    actionName + " — completed successfully"
            );

            System.out.println("[AuditDecorator] Post-execution audit recorded for: "
                    + actionName);

        } catch (Exception e) {
            // Log failure details
            auditService.logAction(
                    employeeId,
                    AuditLog.ActionType.ENROLLMENT,
                    "SYSTEM",
                    actionName + " — FAILED: " + e.getMessage()
            );

            System.out.println("[AuditDecorator] Failure audit recorded for: "
                    + actionName + " | Error: " + e.getMessage());

            // Re-throw so the caller still knows it failed
            throw new RuntimeException("[AuditDecorator] Operation failed: "
                    + actionName, e);
        }
    }

    /**
     * Convenience method: wrap any action with audit logging by action type.
     * Use this for non-enrollment actions (payroll, claims, approvals).
     *
     * @param employeeId    The employee affected.
     * @param actionType    The audit action type enum value.
     * @param performedBy   Who triggered this action.
     * @param details       Details for the log entry.
     * @param operation     The actual service call to wrap.
     */
    public void executeWithType(String employeeId,
                                AuditLog.ActionType actionType,
                                String performedBy,
                                String details,
                                EnrollmentOperation operation) {

        System.out.println("[AuditDecorator] Wrapping [" + actionType
                + "] for Employee: " + employeeId);

        auditService.logAction(employeeId, actionType, performedBy, details + " — started");

        try {
            operation.execute();
            auditService.logAction(employeeId, actionType, performedBy, details + " — SUCCESS");
        } catch (Exception e) {
            auditService.logAction(employeeId, actionType, performedBy,
                    details + " — FAILED: " + e.getMessage());
            throw new RuntimeException("[AuditDecorator] Wrapped operation failed.", e);
        }
    }
}
