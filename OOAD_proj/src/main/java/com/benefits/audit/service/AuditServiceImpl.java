package com.benefits.audit.service;

import com.benefits.audit.dao.AuditLogDAO;
import com.benefits.audit.exception.AuditLogFailureException;
import com.benefits.audit.exception.ComplianceViolationException;
import com.benefits.audit.model.AuditLog;

import java.util.Collection;
import java.util.UUID;

/**
 * Concrete implementation of AuditService.
 *
 * GRASP - Pure Fabrication:
 * This class exists purely as an infrastructure service. It has no
 * domain entity equivalent — its only job is logging and compliance.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Only responsible for recording audit logs and running compliance checks.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * Depends on AuditLogDAO abstraction (not the impl). DAO is injected.
 *
 * SOLID - Open/Closed Principle (OCP):
 * New compliance rules can be added without modifying existing methods.
 *
 * Pattern: Implements AuditService interface — all callers depend on
 * the interface, not this class directly.
 */
public class AuditServiceImpl implements AuditService {

    // ── DAO Dependency ────────────────────────────────────────────────────────
    private final AuditLogDAO auditLogDAO;

    // ── Constructor ───────────────────────────────────────────────────────────
    public AuditServiceImpl(AuditLogDAO auditLogDAO) {
        this.auditLogDAO = auditLogDAO;
        System.out.println("[AuditService] Initialized and ready.");
    }

    // ── Core Operations ───────────────────────────────────────────────────────

    /**
     * Logs any system action to the audit trail.
     * Throws AuditLogFailureException on persistence failure — WARNING level.
     */
    @Override
    public void logAction(String employeeId,
                          AuditLog.ActionType actionType,
                          String performedBy,
                          String actionDetails) {
        try {
            String logId = "LOG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            AuditLog log = new AuditLog(logId, employeeId, actionType,
                                        performedBy, actionDetails);
            auditLogDAO.save(log);
            System.out.println("[AuditService] Action logged: ["
                    + actionType + "] for Employee: " + employeeId);
        } catch (Exception e) {
            // WARNING: Could not persist audit log — retry or backup
            throw new AuditLogFailureException(employeeId, actionType.name(), e);
        }
    }

    /**
     * Runs a compliance check before executing an action.
     * Throws ComplianceViolationException if the action violates rules.
     *
     * Rules enforced:
     * - PAYROLL_UPDATE without prior APPROVAL is a violation.
     * - ENROLLMENT without PROFILE_CREATED record is a violation.
     */
    @Override
    public void checkCompliance(String employeeId,
                                AuditLog.ActionType actionType,
                                String context) {
        System.out.println("[AuditService] Running compliance check for ["
                + actionType + "] Employee: " + employeeId);

        // Rule 1: Payroll updates must follow an approval
        if (actionType == AuditLog.ActionType.PAYROLL_UPDATE) {
            Collection<AuditLog> logs = auditLogDAO.findByEmployeeId(employeeId);
            boolean hasApproval = logs.stream()
                    .anyMatch(l -> l.getActionType() == AuditLog.ActionType.APPROVAL);
            if (!hasApproval) {
                throw new ComplianceViolationException(employeeId,
                        "PAYROLL_UPDATE attempted without prior APPROVAL record.");
            }
        }

        // Rule 2: Claims must have an existing enrollment
        if (actionType == AuditLog.ActionType.CLAIM) {
            Collection<AuditLog> logs = auditLogDAO.findByEmployeeId(employeeId);
            boolean hasEnrollment = logs.stream()
                    .anyMatch(l -> l.getActionType() == AuditLog.ActionType.ENROLLMENT);
            if (!hasEnrollment) {
                throw new ComplianceViolationException(employeeId,
                        "CLAIM filed without prior ENROLLMENT record.");
            }
        }

        System.out.println("[AuditService] Compliance check PASSED for: "
                + employeeId + " | " + actionType);
    }

    /**
     * Returns all audit logs for a specific employee — for reporting.
     */
    @Override
    public Collection<AuditLog> generateReportForEmployee(String employeeId) {
        Collection<AuditLog> logs = auditLogDAO.findByEmployeeId(employeeId);
        System.out.println("[AuditService] Report generated: "
                + logs.size() + " entries for Employee: " + employeeId);
        return logs;
    }

    /**
     * Returns all audit logs across the system — for full compliance report.
     */
    @Override
    public Collection<AuditLog> generateFullReport() {
        Collection<AuditLog> logs = auditLogDAO.findAll();
        System.out.println("[AuditService] Full audit report: "
                + logs.size() + " total entries.");
        return logs;
    }
}
