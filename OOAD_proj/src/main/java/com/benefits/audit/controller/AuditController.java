package com.benefits.audit.controller;

import com.benefits.audit.exception.AuditLogFailureException;
import com.benefits.audit.exception.ComplianceViolationException;
import com.benefits.audit.model.AuditLog;
import com.benefits.audit.service.AuditService;

import java.util.Collection;

/**
 * Controller for the Audit & Compliance Module.
 *
 * GRASP - Controller:
 * Designated handler for all incoming requests related to audit logs
 * and compliance checks. Delegates everything to AuditService.
 *
 * GRASP - Low Coupling:
 * Only depends on AuditService interface — never on the concrete impl.
 *
 * SOLID - SRP:
 * Only responsible for receiving requests and routing to AuditService.
 */
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
        System.out.println("[AuditController] Initialized and ready.");
    }

    // ── Request Handlers ──────────────────────────────────────────────────────

    /**
     * Handles a request to log an action.
     */
    public void handleLogAction(String employeeId,
                                AuditLog.ActionType actionType,
                                String performedBy,
                                String details) {
        System.out.println("\n[AuditController] → handleLogAction() for Employee: " + employeeId);
        try {
            auditService.logAction(employeeId, actionType, performedBy, details);
            System.out.println("[AuditController] SUCCESS: Action logged.");
        } catch (AuditLogFailureException e) {
            System.out.println("[AuditController] WARNING: " + e.getMessage());
        }
    }

    /**
     * Handles a compliance check request.
     */
    public boolean handleComplianceCheck(String employeeId,
                                         AuditLog.ActionType actionType,
                                         String context) {
        System.out.println("\n[AuditController] → handleComplianceCheck() for: " + employeeId);
        try {
            auditService.checkCompliance(employeeId, actionType, context);
            System.out.println("[AuditController] Compliance check: PASSED");
            return true;
        } catch (ComplianceViolationException e) {
            System.out.println("[AuditController] COMPLIANCE VIOLATION: " + e.getMessage());
            return false;
        }
    }

    /**
     * Generates and prints an employee audit report.
     */
    public Collection<AuditLog> handleGenerateEmployeeReport(String employeeId) {
        System.out.println("\n[AuditController] → generateReport() for Employee: " + employeeId);
        Collection<AuditLog> logs = auditService.generateReportForEmployee(employeeId);
        System.out.println("[AuditController] Report contains " + logs.size() + " entries.");
        for (AuditLog log : logs) {
            System.out.println("  → " + log.getLogId()
                    + " | " + log.getActionType()
                    + " | " + log.getTimestamp());
        }
        return logs;
    }

    /**
     * Generates and prints the full system audit report.
     */
    public Collection<AuditLog> handleGenerateFullReport() {
        System.out.println("\n[AuditController] → generateFullReport()");
        Collection<AuditLog> logs = auditService.generateFullReport();
        System.out.println("[AuditController] Full report: " + logs.size() + " total entries.");
        for (AuditLog log : logs) {
            System.out.println("  → " + log.getLogId()
                    + " | EMP: " + log.getEmployeeId()
                    + " | " + log.getActionType()
                    + " | " + log.getTimestamp());
        }
        return logs;
    }
}
