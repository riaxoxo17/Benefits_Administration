package com.benefits;

import com.benefits.catalog.controller.CatalogController;
import com.benefits.catalog.dao.BenefitPlanDAO;
import com.benefits.catalog.dao.BenefitPlanDAOImpl;
import com.benefits.catalog.facade.BenefitsCatalogFacade;
import com.benefits.catalog.model.BenefitPlan;
import com.benefits.controller.ProfileController;
import com.benefits.dao.EmployeeProfileDAO;
import com.benefits.dao.EmployeeProfileDAOImpl;
import com.benefits.facade.BenefitsProfileFacade;
import com.benefits.model.EmployeeProfile;

import java.util.Collection;

/**
 * Main entry point for the Benefits Administration System.
 * Subsystem 5 — Employee Benefits Profile Manager
 * + Benefits Plan Catalog
 *
 * This class wires together BOTH components and demonstrates
 * the complete flow of the system end to end.
 *
 * ─────────────────────────────────────────────────────────────────
 * COMPONENT 1 — Employee Benefits Profile Manager
 *
 * REQUEST
 * ↓
 * ProfileController ← GRASP Controller
 * ↓
 * BenefitsProfileFacade ← Structural Pattern (Facade)
 * ↓
 * EmployeeBenefitsProfileManager ← Creational Pattern (Singleton)
 * ↓
 * EmployeeProfileDAO ← SOLID DIP (abstraction)
 * ↓
 * EmployeeProfileDAOImpl ← DB Team's implementation
 *
 * Observers notified automatically on create/update:
 * ProfileEventNotifier → EligibilityCheckListener (Observer Pattern)
 *
 * ─────────────────────────────────────────────────────────────────
 * COMPONENT 2 — Benefits Plan Catalog
 *
 * REQUEST
 * ↓
 * CatalogController ← GRASP Controller
 * ↓
 * BenefitsCatalogFacade ← Structural Pattern (Facade)
 * ↓
 * BenefitPlanFactoryImpl ← Creational Pattern (Factory)
 * ↓
 * BenefitPlanDAO ← SOLID DIP (abstraction)
 * ↓
 * BenefitPlanDAOImpl ← DB Team's implementation
 *
 * Cost calculated per plan type via Strategy Pattern:
 * HealthCostStrategy, DentalCostStrategy,
 * RetirementCostStrategy, InsuranceCostStrategy
 *
 * ─────────────────────────────────────────────────────────────────
 * DESIGN PATTERNS USED:
 * Creational → Singleton (EmployeeBenefitsProfileManager)
 * Factory (BenefitPlanFactoryImpl)
 * Structural → Facade (BenefitsProfileFacade,
 * BenefitsCatalogFacade)
 * Behavioural → Observer (ProfileObserver,
 * ProfileEventNotifier,
 * EligibilityCheckListener)
 * Strategy (CostCalculationStrategy + 4 impls)
 *
 * SOLID PRINCIPLES USED:
 * S → SRP : Every class has exactly one responsibility
 * O → OCP : Open for extension, closed for modification
 * D → DIP : All layers depend on interfaces, not concretions
 * I → ISP : Interfaces are small and focused
 *
 * GRASP PRINCIPLES USED:
 * → Controller : ProfileController, CatalogController
 * → Information Expert: EmployeeProfile, BenefitPlan subclasses
 * → Creator : Manager (profiles), Factory (plans)
 * → Low Coupling : All components talk through interfaces
 * → High Cohesion : Each class is focused on one job
 * ─────────────────────────────────────────────────────────────────
 */
public class Main {

        public static void main(String[] args) {

                printBanner();

                // ════════════════════════════════════════════════════════════
                // COMPONENT 1 SETUP — Employee Benefits Profile Manager
                // ════════════════════════════════════════════════════════════

                System.out.println(">>> Initializing Component 1: "
                                + "Employee Benefits Profile Manager...\n");

                // DB team provides their DAO implementation
                // We depend on the interface — not the concrete class (DIP)
                EmployeeProfileDAO profileDAO = new EmployeeProfileDAOImpl();

                // Facade sets up the Singleton manager and registers
                // default observers (EligibilityCheckListener) internally
                BenefitsProfileFacade profileFacade = new BenefitsProfileFacade(profileDAO);

                // Controller is the only entry point for profile requests
                ProfileController profileController = new ProfileController(profileFacade);

                // ════════════════════════════════════════════════════════════
                // COMPONENT 2 SETUP — Benefits Plan Catalog
                // ════════════════════════════════════════════════════════════

                System.out.println("\n>>> Initializing Component 2: "
                                + "Benefits Plan Catalog...\n");

                // DB team provides their plan DAO implementation
                BenefitPlanDAO planDAO = new BenefitPlanDAOImpl();

                // Facade wires up the factory and DAO internally
                BenefitsCatalogFacade catalogFacade = new BenefitsCatalogFacade(planDAO);

                // Controller is the only entry point for catalog requests
                CatalogController catalogController = new CatalogController(catalogFacade);

                // ════════════════════════════════════════════════════════════
                // COMPONENT 1 DEMOS — Employee Benefits Profile Manager
                // ════════════════════════════════════════════════════════════

                printSectionHeader("COMPONENT 1 DEMOS — Employee Benefits Profile Manager");

                // ── Demo 1: Create Full-Time Employee ────────────────────────
                printDemoHeader(1, "Create Full-Time Employee Profile");
                profileController.handleCreateProfile(
                                "EMP001",
                                "Ria Parikh",
                                "Software Engineer",
                                "Engineering",
                                "L2",
                                EmployeeProfile.EmploymentType.FULL_TIME,
                                "2023-06-01");

                // ── Demo 2: Create Contract Employee ─────────────────────────
                printDemoHeader(2, "Create Contract Employee Profile");
                profileController.handleCreateProfile(
                                "EMP002",
                                "Poorav Bolar",
                                "UI Designer",
                                "Design",
                                "L1",
                                EmployeeProfile.EmploymentType.CONTRACT,
                                "2024-01-15");

                // ── Demo 3: Create Intern ─────────────────────────────────────
                printDemoHeader(3, "Create Intern Profile");
                profileController.handleCreateProfile(
                                "EMP003",
                                "Vraj Detroja",
                                "Intern - Backend",
                                "Engineering",
                                "L0",
                                EmployeeProfile.EmploymentType.INTERN,
                                "2025-01-10");

                // ── Demo 4: Retrieve a Profile ────────────────────────────────
                printDemoHeader(4, "Retrieve Employee Profile — EMP001");
                EmployeeProfile retrieved = profileController.handleGetProfile("EMP001");
                if (retrieved != null) {
                        System.out.println("\n[Main] Profile Details:");
                        System.out.println(retrieved);
                }

                // ── Demo 5: Duplicate Profile Attempt ────────────────────────
                printDemoHeader(5, "Attempt Duplicate Profile — EMP001");
                profileController.handleCreateProfile(
                                "EMP001",
                                "Ria Parikh",
                                "Software Engineer",
                                "Engineering",
                                "L2",
                                EmployeeProfile.EmploymentType.FULL_TIME,
                                "2023-06-01");

                // ── Demo 6: Profile Not Found ─────────────────────────────────
                printDemoHeader(6, "Retrieve Non-Existent Profile — EMP999");
                profileController.handleGetProfile("EMP999");

                // ── Demo 7: Get All Profiles ──────────────────────────────────
                printDemoHeader(7, "Get All Profiles");
                Collection<EmployeeProfile> allProfiles = profileController.handleGetAllProfiles();
                System.out.println("\n[Main] All Profiles in System:");
                for (EmployeeProfile p : allProfiles) {
                        System.out.println("  → " + p.getEmployeeId()
                                        + " | " + p.getName()
                                        + " | " + p.getEmploymentType()
                                        + " | Eligible: " + p.isEligibilityStatus());
                }

                // ── Demo 8: Delete a Profile ──────────────────────────────────
                printDemoHeader(8, "Delete Profile — EMP003");
                profileController.handleDeleteProfile("EMP003");

                // ── Demo 9: Delete Non-Existent Profile ───────────────────────
                printDemoHeader(9, "Delete Non-Existent Profile — EMP999");
                profileController.handleDeleteProfile("EMP999");

                // ════════════════════════════════════════════════════════════
                // COMPONENT 2 DEMOS — Benefits Plan Catalog
                // ════════════════════════════════════════════════════════════

                printSectionHeader("COMPONENT 2 DEMOS — Benefits Plan Catalog");

                // ── Demo 10: Add a Health Plan ────────────────────────────────
                printDemoHeader(10, "Add Health Plan to Catalog");
                catalogController.handleAddPlan(
                                BenefitPlan.PlanType.HEALTH,
                                "PLAN001",
                                "Premium Health Cover",
                                "Full hospitalization, surgeries, outpatient",
                                500000.0,
                                3000.0,
                                "Star Health Insurance",
                                "Full-Time employees with L2 and above",
                                12);

                // ── Demo 11: Add a Dental Plan ────────────────────────────────
                printDemoHeader(11, "Add Dental Plan to Catalog");
                catalogController.handleAddPlan(
                                BenefitPlan.PlanType.DENTAL,
                                "PLAN002",
                                "Basic Dental Care",
                                "Cleanings, fillings, extractions",
                                50000.0,
                                800.0,
                                "Apollo Dental",
                                "All full-time employees",
                                12);

                // ── Demo 12: Add a Retirement Plan ───────────────────────────
                printDemoHeader(12, "Add Retirement Plan to Catalog");
                catalogController.handleAddPlan(
                                BenefitPlan.PlanType.RETIREMENT,
                                "PLAN003",
                                "Employee Provident Fund",
                                "Monthly employer contribution to retirement corpus",
                                2000000.0,
                                5000.0,
                                "HDFC Pension Fund",
                                "Full-Time employees with 1+ year tenure",
                                120);

                // ── Demo 13: Add an Insurance Plan ───────────────────────────
                printDemoHeader(13, "Add Insurance Plan to Catalog");
                catalogController.handleAddPlan(
                                BenefitPlan.PlanType.INSURANCE,
                                "PLAN004",
                                "Group Term Life Insurance",
                                "Life cover for employee and dependents",
                                1000000.0,
                                1500.0,
                                "LIC of India",
                                "All permanent employees",
                                12);

                // ── Demo 14: Retrieve a Plan ──────────────────────────────────
                printDemoHeader(14, "Retrieve Plan — PLAN001");
                BenefitPlan plan = catalogController.handleGetPlan("PLAN001");
                if (plan != null) {
                        System.out.println("\n[Main] Plan Details:");
                        System.out.println(plan);
                }

                // ── Demo 15: Get Plans by Type ────────────────────────────────
                printDemoHeader(15, "Get All HEALTH Plans");
                Collection<BenefitPlan> healthPlans = catalogController.handleGetPlansByType(
                                BenefitPlan.PlanType.HEALTH);
                System.out.println("[Main] Health Plans found: "
                                + healthPlans.size());

                // ── Demo 16: Calculate Plan Cost — Health ─────────────────────
                printDemoHeader(16, "Calculate Cost — PLAN001 (Health)");
                catalogController.handleCalculatePlanCost("PLAN001");

                // ── Demo 17: Calculate Plan Cost — Retirement ─────────────────
                printDemoHeader(17, "Calculate Cost — PLAN003 (Retirement)");
                catalogController.handleCalculatePlanCost("PLAN003");

                // ── Demo 18: Get All Plans ────────────────────────────────────
                printDemoHeader(18, "Get All Plans in Catalog");
                Collection<BenefitPlan> allPlans = catalogController.handleGetAllPlans();
                System.out.println("\n[Main] All Plans in Catalog:");
                for (BenefitPlan p : allPlans) {
                        System.out.println("  → " + p.getPlanId()
                                        + " | " + p.getPlanName()
                                        + " | " + p.getPlanType()
                                        + " | Base Cost: " + p.getBaseCost());
                }

                // ── Demo 19: Plan Not Found ───────────────────────────────────
                printDemoHeader(19, "Retrieve Non-Existent Plan — PLAN999");
                catalogController.handleGetPlan("PLAN999");

                // ── Demo 20: Delete a Plan ────────────────────────────────────
                printDemoHeader(20, "Delete Plan — PLAN004");
                catalogController.handleDeletePlan("PLAN004");

                // ── Demo 21: Invalid Plan — Missing Name ──────────────────────
                printDemoHeader(21, "Add Invalid Plan — Missing Name");
                catalogController.handleAddPlan(
                                BenefitPlan.PlanType.DENTAL,
                                "PLAN005",
                                "", // intentionally blank to trigger validation
                                "Basic dental",
                                30000.0,
                                500.0,
                                "Apollo",
                                "All employees",
                                12);

                // ════════════════════════════════════════════════════════════
                // FINAL SUMMARY
                // ════════════════════════════════════════════════════════════

                printSectionHeader("System Demo Complete");
                System.out.println("  Component 1 — Employee Benefits Profile Manager : DONE");
                System.out.println("  Component 2 — Benefits Plan Catalog             : DONE");
                System.out.println("\n  Awaiting DB Team DAO Implementations:");
                System.out.println("    → EmployeeProfileDAOImpl.java");
                System.out.println("    → BenefitPlanDAOImpl.java");
                System.out.println("\n" + "=".repeat(56));
        }

        // ── Print Helpers ─────────────────────────────────────────────────────────

        /** Prints the system startup banner. */
        private static void printBanner() {
                System.out.println("=".repeat(56));
                System.out.println("   Benefits Administration System");
                System.out.println("   Subsystem 5 — OOAD Project");
                System.out.println("   Team: Ria | Prerana | Poorav | Vraj");
                System.out.println("=".repeat(56) + "\n");
        }

        /** Prints a section divider with a title. */
        private static void printSectionHeader(String title) {
                System.out.println("\n" + "=".repeat(56));
                System.out.println("   " + title);
                System.out.println("=".repeat(56));
        }

        /** Prints a numbered demo header. */
        private static void printDemoHeader(int num, String title) {
                System.out.println("\n--- DEMO " + num + ": " + title + " ---");
        }
}