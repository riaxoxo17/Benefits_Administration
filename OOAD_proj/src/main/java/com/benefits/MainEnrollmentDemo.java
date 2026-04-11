package main.java.com.benefits;

import main.java.com.benefits.dao.EmployeeProfileDAOImpl;
import main.java.com.benefits.catalog.dao.BenefitPlanDAOImpl;
import main.java.com.benefits.facade.BenefitsProfileFacade;
import main.java.com.benefits.catalog.facade.BenefitsCatalogFacade;
import main.java.com.benefits.model.EmployeeProfile;
import main.java.com.benefits.catalog.model.BenefitPlan;
import main.java.com.benefits.exception.DuplicateProfileException;
import main.java.com.benefits.enrollment.controller.EnrollmentController;
import com.benefits.events.BenefitsEventPublisher;
import com.benefits.events.AuditEventListener;
import com.benefits.events.NotificationEventListener;
import com.benefits.audit.service.AuditServiceImpl;
import com.benefits.audit.dao.AuditLogDAOImpl;
import com.benefits.notification.service.NotificationServiceImpl;
import com.benefits.notification.dao.NotificationDAOImpl;

/**
 * Demo class for Prerana's Eligibility Verification Engine
 * and Employee Self-Enrollment Module.
 *
 * Demonstrates:
 * 1. Strategy Pattern  — three pluggable eligibility rules
 * 2. Facade Pattern    — EnrollmentFacade as single entry point
 * 3. Observer Pattern  — events firing to Audit + Notification (Vraj)
 *
 * How to run:
 * Compile and run this class as the main entry point.
 * It will simulate 4 enrollment scenarios:
 *   → Full-time eligible employee (should SUCCEED)
 *   → Duplicate enrollment attempt (should FAIL)
 *   → Contract employee (should FAIL — employment type rule)
 *   → Eligibility-only check (no enrollment)
 */
public class MainEnrollmentDemo {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║   Benefits Administration — Prerana's Module     ║");
        System.out.println("║   Eligibility Engine + Self-Enrollment Module    ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        // ── Step 1: Set up Poorav's subsystems ───────────────────────────────
        // We use their DAOImpl stubs and facades exactly as intended.
        // In the real system, the DB team's SQL implementations replace these.

        BenefitsProfileFacade profileFacade =
                new BenefitsProfileFacade(new EmployeeProfileDAOImpl());

        BenefitsCatalogFacade catalogFacade =
                new BenefitsCatalogFacade(new BenefitPlanDAOImpl());

        // ── Step 2: Set up Vraj's Observer event bus ─────────────────────────
        // AuditEventListener and NotificationEventListener need their
        // service implementations injected (Vraj's design requires this).
        BenefitsEventPublisher eventPublisher = new BenefitsEventPublisher();
        eventPublisher.subscribe(new AuditEventListener(new AuditServiceImpl(new AuditLogDAOImpl())));
        eventPublisher.subscribe(new NotificationEventListener(new NotificationServiceImpl(new NotificationDAOImpl())));
        // ── Step 3: Create some employees in Poorav's profile system ─────────
        System.out.println("\n─── Setting up test data ───────────────────────────\n");

        try {
            // Full-time eligible employee — joined 2023-01-15
            profileFacade.createProfile(new EmployeeProfile(
                    "EMP001", "Prerana M N", "Software Engineer",
                    "Engineering", "B3",
                    EmployeeProfile.EmploymentType.FULL_TIME,
                    "2023-01-15"));

            // Contract employee — should fail employment type rule
            profileFacade.createProfile(new EmployeeProfile(
                    "EMP002", "Vraj Detroja", "Contractor",
                    "DevOps", "B2",
                    EmployeeProfile.EmploymentType.CONTRACT,
                    "2024-06-01"));

        } catch (DuplicateProfileException e) {
            System.out.println("Profile already exists: " + e.getMessage());
        }

        // ── Step 4: Add a plan to Poorav's catalog ────────────────────────────
        // eligibilityCriteria uses "SALARY_BAND:B2, WAITING_PERIOD:6"
        // so our SalaryBandStrategy and WaitingPeriodStrategy will parse it.
        catalogFacade.addPlan(
                BenefitPlan.PlanType.HEALTH,
                "PLAN-HEALTH-01",
                "Premium Health Cover",
                "Hospitalization, OPD, dental basic",
                500000.0,
                2500.0,
                "Star Health Insurance",
                "SALARY_BAND:B2, WAITING_PERIOD:6",
                12
        );

        // ── Step 5: Create Prerana's EnrollmentController ─────────────────────
        // This wires the entire Eligibility + Enrollment subsystem internally.
        EnrollmentController controller = new EnrollmentController(
                profileFacade, catalogFacade, eventPublisher);

        // ══════════════════════════════════════════════════════════════════════
        // SCENARIO 1: Eligible full-time employee — should SUCCEED
        // ══════════════════════════════════════════════════════════════════════
        System.out.println("\n\n━━━ SCENARIO 1: Valid Enrollment (Full-time, B3, 2yr tenure) ━━━");
        controller.handleEnrollmentRequest(
                "EMP001",
                "PLAN-HEALTH-01",
                "Spouse: Ananya M N",
                "EMP001");

        // ══════════════════════════════════════════════════════════════════════
        // SCENARIO 2: Duplicate enrollment — same employee, same plan
        // ══════════════════════════════════════════════════════════════════════
        System.out.println("\n\n━━━ SCENARIO 2: Duplicate Enrollment Attempt ━━━");
        controller.handleEnrollmentRequest(
                "EMP001",
                "PLAN-HEALTH-01",
                "None",
                "EMP001");

        // ══════════════════════════════════════════════════════════════════════
        // SCENARIO 3: Contract employee — should FAIL employment type rule
        // ══════════════════════════════════════════════════════════════════════
        System.out.println("\n\n━━━ SCENARIO 3: Contract Employee (ineligible) ━━━");
        controller.handleEnrollmentRequest(
                "EMP002",
                "PLAN-HEALTH-01",
                "None",
                "EMP002");

        // ══════════════════════════════════════════════════════════════════════
        // SCENARIO 4: Eligibility check only — no enrollment
        // ══════════════════════════════════════════════════════════════════════
        System.out.println("\n\n━━━ SCENARIO 4: Eligibility Check Only (EMP001) ━━━");
        controller.handleEligibilityCheck("EMP001", "PLAN-HEALTH-01");

        // ══════════════════════════════════════════════════════════════════════
        // SCENARIO 5: View all enrollments
        // ══════════════════════════════════════════════════════════════════════
        System.out.println("\n\n━━━ SCENARIO 5: View All Enrollments ━━━");
        controller.displayAllEnrollments();

        System.out.println("\n\n╔══════════════════════════════════════════════════╗");
        System.out.println("║              Demo Complete                       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }
}
