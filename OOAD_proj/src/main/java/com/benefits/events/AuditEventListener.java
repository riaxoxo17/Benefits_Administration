package com.benefits.events;

import com.benefits.audit.model.AuditLog;
import com.benefits.audit.service.AuditService;

/**
 * OBSERVER PATTERN — Concrete Subscriber for the Audit Module.
 *
 * Subscribes to the BenefitsEventPublisher and reacts to every major
 * system event by writing an entry to the audit trail.
 *
 * GRASP - Pure Fabrication: Infrastructure listener — no domain role.
 * GRASP - Low Coupling: Does not know about enrollment, payroll, or claims.
 *                       It only knows about events and AuditService.
 * SOLID - SRP: Only responsible for translating events into audit log entries.
 * SOLID - DIP: Depends on AuditService interface, not the concrete impl.
 */
public class AuditEventListener implements BenefitsEventListener {

    private final AuditService auditService;

    public AuditEventListener(AuditService auditService) {
        this.auditService = auditService;
        System.out.println("[AuditEventListener] Registered and listening.");
    }

    @Override
    public void onEvent(BenefitsEvent event) {
        System.out.println("[AuditEventListener] Received: "
                + event.getClass().getSimpleName()
                + " for Employee: " + event.getEmployeeId());

        AuditLog.ActionType actionType = resolveActionType(event);

        auditService.logAction(
                event.getEmployeeId(),
                actionType,
                event.getPerformedBy(),
                event.getEventDescription()
        );
    }

    /**
     * Maps event type to the appropriate AuditLog.ActionType.
     * SOLID - OCP: Add new event-to-action mappings here as system grows.
     */
    private AuditLog.ActionType resolveActionType(BenefitsEvent event) {
        if (event instanceof EnrollmentApprovedEvent) return AuditLog.ActionType.APPROVAL;
        if (event instanceof ClaimFiledEvent)         return AuditLog.ActionType.CLAIM;
        if (event instanceof PayrollUpdatedEvent)     return AuditLog.ActionType.PAYROLL_UPDATE;
        return AuditLog.ActionType.ENROLLMENT;
    }
}
