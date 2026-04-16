package com.benefits;

// ───  imports ───────────────────────────────────────────────────────────
import com.benefits.audit.controller.AuditController;
import com.benefits.audit.dao.AuditLogDAO;
import com.benefits.audit.dao.AuditLogDAOImpl;
import com.benefits.audit.decorator.AuditableEnrollmentService;
import com.benefits.audit.model.AuditLog;
import com.benefits.audit.service.AuditService;
import com.benefits.audit.service.AuditServiceImpl;
import com.benefits.events.*;
import com.benefits.notification.controller.NotificationController;
import com.benefits.notification.dao.NotificationDAO;
import com.benefits.notification.dao.NotificationDAOImpl;
import com.benefits.notification.model.Notification;
import com.benefits.notification.service.NotificationService;
import com.benefits.notification.service.NotificationServiceImpl;

/**
 * ─────────────────────────────────────────────────────────────────────────────
 * Components 9 & 10 Demo Runner
 *
 * Copy the body of runDemo() and paste it at the bottom of Main.java
 * (inside the main() method, after Poorav's component demos).
 *
 * OR just run this file standalone to test  components independently.
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * COMPONENTS:
 *   9.  Audit & Compliance Module
 *   10. Notification & Communication Module
 *
 * DESIGN PATTERNS:
 *   Structural  → Decorator  : AuditableEnrollmentService
 *   Behavioural → Observer   : BenefitsEventPublisher + listeners
 *
 * GRASP:
 *   Pure Fabrication : AuditService, NotificationService
 *   Indirection      : Observer decouples source from audit/notification
 *
 * SOLID:
 *   DIP : All modules depend on AuditService / NotificationService interfaces
 *   SRP : Each class has one responsibility
 *   OCP : New events/channels/rules added without modifying existing code
 */
public class MainAuditNotificationDemo {

    public static void main(String[] args) {
        runDemo();
    }

    public static void runDemo() {

        printSectionHeader("COMPONENTS 9 & 10 — ");
        printSectionHeader("Audit & Compliance + Notification & Communication");

        // ════════════════════════════════════════════════════════════
        // SETUP — Wire DAOs, Services, Controllers
        // All depend on interfaces, never on concrete classes (DIP)
        // ════════════════════════════════════════════════════════════

        System.out.println("\n>>> Initializing Component 9: Audit & Compliance Module...\n");
        AuditLogDAO  auditLogDAO  = new AuditLogDAOImpl();
        AuditService auditService = new AuditServiceImpl(auditLogDAO);
        AuditController auditController = new AuditController(auditService);

        System.out.println("\n>>> Initializing Component 10: Notification & Communication Module...\n");
        NotificationDAO     notificationDAO     = new NotificationDAOImpl();
        NotificationService notificationService = new NotificationServiceImpl(notificationDAO);
        NotificationController notificationController = new NotificationController(notificationService);

        // ════════════════════════════════════════════════════════════
        // OBSERVER PATTERN SETUP
        // Publisher is the event bus. Audit + Notification both subscribe.
        // Source modules (enrollment, claims, payroll) only call publish().
        // They never talk to Audit or Notification directly — Indirection.
        // ════════════════════════════════════════════════════════════

        System.out.println("\n>>> Setting up Observer Pattern (Event Publisher)...\n");
        BenefitsEventPublisher eventPublisher = new BenefitsEventPublisher();
        eventPublisher.subscribe(new AuditEventListener(auditService));
        eventPublisher.subscribe(new NotificationEventListener(notificationService));

        // ════════════════════════════════════════════════════════════
        // DEMO 1 — Observer: Enrollment Approved Event
        // Both AuditEventListener AND NotificationEventListener react
        // independently to the same event — zero coupling between them.
        // ════════════════════════════════════════════════════════════

        printDemoHeader(1, "Observer Pattern — Enrollment Approved Event");
        eventPublisher.publish(
                new EnrollmentApprovedEvent("EMP001", "ENR001", "PLAN001", "MGR01"));

        // ════════════════════════════════════════════════════════════
        // DEMO 2 — Observer: Claim Filed Event
        // ════════════════════════════════════════════════════════════

        printDemoHeader(2, "Observer Pattern — Claim Filed Event");
        eventPublisher.publish(
                new ClaimFiledEvent("EMP001", "CLM001", 15000.0, "EMP001"));

        // ════════════════════════════════════════════════════════════
        // DEMO 3 — Observer: Payroll Updated Event
        // ════════════════════════════════════════════════════════════

        printDemoHeader(3, "Observer Pattern — Payroll Updated Event");
        eventPublisher.publish(
                new PayrollUpdatedEvent("EMP001", "DED001", 3000.0, "PAYROLL_SYSTEM"));

        // ════════════════════════════════════════════════════════════
        // DEMO 4 — Decorator Pattern
        // AuditableEnrollmentService wraps any service call and logs
        // audit entries BEFORE and AFTER — transparently.
        // The wrapped operation itself has zero knowledge of auditing.
        // ════════════════════════════════════════════════════════════

        printDemoHeader(4, "Decorator Pattern — Auditable Enrollment Service");
        AuditableEnrollmentService auditableService =
                new AuditableEnrollmentService(auditService);

        auditableService.execute("EMP002", "PLAN_ENROLLMENT_REQUEST", () -> {
            // This is where the real EnrollmentService.enroll() would be called
            System.out.println("  [WrappedOperation] Enrolling EMP002 into PLAN002...");
            System.out.println("  [WrappedOperation] Enrollment logic complete.");
        });

        // ════════════════════════════════════════════════════════════
        // DEMO 5 — Decorator with Failure Handling
        // ════════════════════════════════════════════════════════════

        printDemoHeader(5, "Decorator Pattern — Failure Scenario");
        try {
            auditableService.execute("EMP003", "INVALID_ENROLLMENT_ATTEMPT", () -> {
                System.out.println("  [WrappedOperation] Attempting enrollment...");
                throw new RuntimeException("NOT_ELIGIBLE_FOR_PLAN");
            });
        } catch (RuntimeException e) {
            System.out.println("[Main] Caught decorator exception: " + e.getMessage());
        }

        // ════════════════════════════════════════════════════════════
        // DEMO 6 — Direct Audit: Log an Action
        // ════════════════════════════════════════════════════════════

        printDemoHeader(6, "Audit Module — Direct logAction()");
        auditController.handleLogAction(
                "EMP002",
                AuditLog.ActionType.PROFILE_CREATED,
                "SYSTEM",
                "Employee profile created during onboarding");

        // ════════════════════════════════════════════════════════════
        // DEMO 7 — Compliance Check: PASS
        // EMP001 has APPROVAL in audit trail (from Demo 1), so
        // a PAYROLL_UPDATE compliance check should PASS.
        // ════════════════════════════════════════════════════════════

        printDemoHeader(7, "Compliance Check — PASS (prior approval exists)");
        auditController.handleComplianceCheck(
                "EMP001",
                AuditLog.ActionType.PAYROLL_UPDATE,
                "payroll-cycle-june");

        // ════════════════════════════════════════════════════════════
        // DEMO 8 — Compliance Check: VIOLATION
        // EMP002 has no ENROLLMENT in audit trail, so filing a CLAIM
        // violates compliance rules.
        // ════════════════════════════════════════════════════════════

        printDemoHeader(8, "Compliance Check — VIOLATION (no enrollment on record)");
        auditController.handleComplianceCheck(
                "EMP002",
                AuditLog.ActionType.CLAIM,
                "claim-context-dental");

        // ════════════════════════════════════════════════════════════
        // DEMO 9 — Notification: Direct Send
        // ════════════════════════════════════════════════════════════

        printDemoHeader(9, "Notification Module — Direct Send (EMAIL)");
        notificationController.handleSendNotification(
                "EMP002",
                Notification.NotificationType.ENROLLMENT,
                "Your enrollment request for PLAN002 is pending approval.",
                Notification.Channel.EMAIL);

        // ════════════════════════════════════════════════════════════
        // DEMO 10 — Notification: SMS Channel
        // ════════════════════════════════════════════════════════════

        printDemoHeader(10, "Notification Module — Send via SMS");
        notificationController.handleSendNotification(
                "EMP001",
                Notification.NotificationType.APPROVAL,
                "Your benefit enrollment ENR001 has been approved!",
                Notification.Channel.SMS);

        // ════════════════════════════════════════════════════════════
        // DEMO 11 — Notification: Dashboard Channel
        // ════════════════════════════════════════════════════════════

        printDemoHeader(11, "Notification Module — Send to DASHBOARD");
        notificationController.handleSendNotification(
                "EMP001",
                Notification.NotificationType.CLAIM,
                "Your claim CLM001 for INR 15000 is under review.",
                Notification.Channel.DASHBOARD);

        // ════════════════════════════════════════════════════════════
        // DEMO 12 — Get Notifications for Employee
        // ════════════════════════════════════════════════════════════

        printDemoHeader(12, "Get All Notifications for EMP001");
        notificationController.handleGetNotificationsForEmployee("EMP001");

        // ════════════════════════════════════════════════════════════
        // DEMO 13 — Generate Employee Audit Report
        // ════════════════════════════════════════════════════════════

        printDemoHeader(13, "Audit Report — Employee EMP001");
        auditController.handleGenerateEmployeeReport("EMP001");

        // ════════════════════════════════════════════════════════════
        // DEMO 14 — Generate Full System Audit Report
        // ════════════════════════════════════════════════════════════

        printDemoHeader(14, "Full System Audit Report");
        auditController.handleGenerateFullReport();

        // ════════════════════════════════════════════════════════════
        // FINAL SUMMARY
        // ════════════════════════════════════════════════════════════

        printSectionHeader("Components 9 & 10 Demo Complete");
        System.out.println("  Component 9  — Audit & Compliance Module       : DONE");
        System.out.println("  Component 10 — Notification & Communication    : DONE");
        System.out.println("\n  Patterns Demonstrated:");
        System.out.println("    → Decorator : AuditableEnrollmentService");
        System.out.println("    → Observer  : BenefitsEventPublisher + 2 Listeners");
        System.out.println("\n" + "=".repeat(56));
    }

    private static void printSectionHeader(String title) {
        System.out.println("\n" + "=".repeat(56));
        System.out.println("   " + title);
        System.out.println("=".repeat(56));
    }

    private static void printDemoHeader(int num, String title) {
        System.out.println("\n--- DEMO " + num + ": " + title + " ---");
    }
}
