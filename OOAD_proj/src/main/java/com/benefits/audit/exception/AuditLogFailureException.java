package com.benefits.audit.exception;

/**
 * Exception thrown when an audit log entry cannot be persisted.
 *
 * Error Code: AUDIT_LOG_FAILURE
 * Category  : WARNING — retry logging; store in backup log system.
 *
 * SOLID - SRP: One class, one exception type.
 */
public class AuditLogFailureException extends RuntimeException {

    private final String employeeId;
    private final String action;

    public AuditLogFailureException(String employeeId, String action, Throwable cause) {
        super("[AUDIT_LOG_FAILURE] Failed to record audit log for Employee ID: "
                + employeeId + " | Action: " + action, cause);
        this.employeeId = employeeId;
        this.action     = action;
    }

    public AuditLogFailureException(String message) {
        super("[AUDIT_LOG_FAILURE] " + message);
        this.employeeId = "UNKNOWN";
        this.action     = "UNKNOWN";
    }

    public String getEmployeeId() { return employeeId; }
    public String getAction()     { return action; }
}
