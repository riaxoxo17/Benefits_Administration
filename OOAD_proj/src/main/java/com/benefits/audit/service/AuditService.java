package com.benefits.audit.service;

import com.benefits.audit.model.AuditLog;
import java.util.Collection;

/**
 * Contract for the Audit & Compliance Module.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * All modules (enrollment, payroll, claims, approval) depend on this
 * interface — not on the concrete AuditServiceImpl. This is the key
 * principle for  cross-cutting integration ownership.
 *
 * GRASP - Pure Fabrication:
 * AuditService has no domain meaning (no "audit" in the business domain),
 * but is an essential infrastructure service created to achieve low coupling
 * and high cohesion.
 *
 * GRASP - Indirection:
 * This interface acts as an intermediary — source modules (enrollment,
 * claims, payroll) do not talk to the AuditServiceImpl directly. They
 * talk through this abstraction.
 *
 * Integration Contract: Defined by, consumed by EVERYONE.
 */
public interface AuditService {

    /**
     * Logs any action performed in the system.
     *
     * @param employeeId    The employee affected by the action.
     * @param actionType    The type of action (ENROLLMENT, APPROVAL, etc.)
     * @param performedBy   The user or system that performed the action.
     * @param actionDetails Human-readable details of what happened.
     */
    void logAction(String employeeId,
                   AuditLog.ActionType actionType,
                   String performedBy,
                   String actionDetails);

    /**
     * Runs a compliance check for a given action.
     * Throws ComplianceViolationException if a rule is violated.
     *
     * @param employeeId  The employee to check.
     * @param actionType  The action being performed.
     * @param context     Additional context for rule evaluation.
     */
    void checkCompliance(String employeeId,
                         AuditLog.ActionType actionType,
                         String context);

    /**
     * Generates an audit report for a specific employee.
     *
     * @param employeeId The employee whose audit trail to generate.
     * @return Collection of all AuditLog entries for that employee.
     */
    Collection<AuditLog> generateReportForEmployee(String employeeId);

    /**
     * Generates a full system audit report.
     *
     * @return Collection of all AuditLog entries in the system.
     */
    Collection<AuditLog> generateFullReport();
}
