package com.benefits.audit.model;

import java.time.LocalDateTime;

/**
 * Represents a single audit log entry in the system.
 *
 * GRASP - Information Expert:
 * This class owns all audit log data and knows how to represent it.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Solely responsible for holding audit log data. No logic lives here.
 *
 * Data Entity: Audit_Log
 * Fields: logId, employeeId, actionType, timestamp, performedBy, actionDetails
 */
public class AuditLog {

    // ── Action Type Enum ───────────────────────────────────────────────────────

    /**
     * SOLID - OCP: New action types can be added without changing existing logic.
     */
    public enum ActionType {
        ENROLLMENT,
        APPROVAL,
        CLAIM,
        PAYROLL_UPDATE,
        PROFILE_CREATED,
        PROFILE_UPDATED,
        PROFILE_DELETED,
        PLAN_ADDED,
        PLAN_DELETED,
        COMPLIANCE_CHECK
    }

    // ── Fields ────────────────────────────────────────────────────────────────

    private final String logId;
    private final String employeeId;
    private final ActionType actionType;
    private final LocalDateTime timestamp;
    private final String performedBy;
    private final String actionDetails;

    // ── Constructor ───────────────────────────────────────────────────────────

    public AuditLog(String logId, String employeeId, ActionType actionType,
                    String performedBy, String actionDetails) {
        this.logId        = logId;
        this.employeeId   = employeeId;
        this.actionType   = actionType;
        this.timestamp    = LocalDateTime.now();
        this.performedBy  = performedBy;
        this.actionDetails = actionDetails;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public String getLogId()           { return logId; }
    public String getEmployeeId()      { return employeeId; }
    public ActionType getActionType()  { return actionType; }
    public LocalDateTime getTimestamp(){ return timestamp; }
    public String getPerformedBy()     { return performedBy; }
    public String getActionDetails()   { return actionDetails; }

    // ── Display ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "AuditLog {" +
                "\n  Log ID       : " + logId +
                "\n  Employee ID  : " + employeeId +
                "\n  Action       : " + actionType +
                "\n  Timestamp    : " + timestamp +
                "\n  Performed By : " + performedBy +
                "\n  Details      : " + actionDetails +
                "\n}";
    }
}
