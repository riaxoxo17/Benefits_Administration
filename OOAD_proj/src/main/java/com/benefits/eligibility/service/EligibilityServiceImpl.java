package main.java.com.benefits.eligibility.service;

import main.java.com.benefits.eligibility.exception.EligibilityCheckFailedException;
import main.java.com.benefits.eligibility.exception.NotEligibleForPlanException;
import main.java.com.benefits.eligibility.strategy.EligibilityStrategy;
import main.java.com.benefits.facade.BenefitsProfileFacade;
import main.java.com.benefits.catalog.facade.BenefitsCatalogFacade;
import main.java.com.benefits.catalog.exception.PlanNotFoundException;
import main.java.com.benefits.exception.ProfileNotFoundException;
import main.java.com.benefits.model.EmployeeProfile;
import main.java.com.benefits.catalog.model.BenefitPlan;

import java.util.Arrays;
import java.util.List;

/**
 * Concrete implementation of EligibilityService.
 *
 * This class is the Eligibility Verification Engine.
 * It fetches the employee and plan, then runs every registered
 * EligibilityStrategy in sequence. If any strategy fails,
 * enrollment is denied for that plan.
 *
 * BEHAVIOURAL PATTERN - Strategy:
 * This class is the "context" in the Strategy pattern.
 * It holds a list of EligibilityStrategy objects and delegates
 * the eligibility decision to each one in turn. It never
 * knows what rule is being applied — it just calls isEligible().
 * Adding new rules = injecting a new strategy. Zero code changes.
 *
 * STRUCTURAL PATTERN - Facade (consumer):
 * This class does NOT talk to EmployeeBenefitsProfileManager or
 * BenefitPlanDAO directly. It only talks to BenefitsProfileFacade
 * and BenefitsCatalogFacade — the clean entry points into those
 * subsystems. This maintains Low Coupling across the entire system.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * This class depends on:
 *   - EligibilityStrategy interface (not concrete strategies)
 *   - BenefitsProfileFacade (Poorav's abstraction layer)
 *   - BenefitsCatalogFacade (Poorav's abstraction layer)
 * It never depends on concrete implementations.
 *
 * SOLID - Open/Closed Principle (OCP):
 * This class is closed for modification. Adding new eligibility
 * rules only requires injecting a new strategy — no changes here.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This class is solely responsible for orchestrating the
 * eligibility check. It does not contain rule logic itself.
 *
 * GRASP - Controller:
 * This class is the use-case controller for eligibility.
 * It coordinates fetching data and running strategies.
 *
 * GRASP - Polymorphism:
 * All strategy implementations are treated identically —
 * the same isEligible() call works for all rule types.
 */
public class EligibilityServiceImpl implements EligibilityService {

    // ── Dependencies ─────────────────────────────────────────────────────────

    /**
     * Poorav's facade — used to fetch employee profiles.
     * We depend on the facade, never on the manager or DAO directly.
     */
    private final BenefitsProfileFacade profileFacade;

    /**
     * Poorav's catalog facade — used to fetch benefit plans.
     * We depend on the facade, never on plan DAOs directly.
     */
    private final BenefitsCatalogFacade catalogFacade;

    /**
     * The list of eligibility strategies to run.
     * Each strategy represents one rule (employment type, salary band,
     * waiting period, etc.). All must pass for eligibility to be granted.
     *
     * BEHAVIOURAL PATTERN - Strategy:
     * This list is the heart of the Strategy pattern.
     * Injected at construction time — never hard-coded here.
     */
    private final List<EligibilityStrategy> strategies;

    // ── Constructor ──────────────────────────────────────────────────────────

    /**
     * Constructs the EligibilityServiceImpl with all dependencies injected.
     *
     * SOLID - Dependency Inversion Principle (DIP):
     * All dependencies are injected — this class never creates its
     * own collaborators. Callers (EnrollmentFacade) wire everything
     * together at startup.
     *
     * @param profileFacade  Poorav's profile facade.
     * @param catalogFacade  Poorav's catalog facade.
     * @param strategies     The eligibility strategies to run, in order.
     */
    public EligibilityServiceImpl(BenefitsProfileFacade profileFacade,
                                   BenefitsCatalogFacade catalogFacade,
                                   EligibilityStrategy... strategies) {
        this.profileFacade = profileFacade;
        this.catalogFacade = catalogFacade;
        this.strategies = Arrays.asList(strategies);

        System.out.println("[EligibilityService] Initialized with "
                + this.strategies.size() + " strategies:");
        for (EligibilityStrategy s : this.strategies) {
            System.out.println("  → " + s.getStrategyName());
        }
    }

    // ── Core Method ──────────────────────────────────────────────────────────

    /**
     * Verifies whether an employee is eligible for a specific plan.
     *
     * Steps:
     * 1. Fetch the employee profile from BenefitsProfileFacade.
     * 2. Fetch the benefit plan from BenefitsCatalogFacade.
     * 3. Run each EligibilityStrategy in sequence.
     * 4. If any strategy fails → throw NotEligibleForPlanException.
     * 5. If all strategies pass → return true.
     *
     * @param employeeId The ID of the employee to check.
     * @param planId     The ID of the plan to check against.
     * @return true if all strategies pass.
     * @throws EligibilityCheckFailedException if employee/plan not found.
     * @throws NotEligibleForPlanException if any strategy fails.
     */
    @Override
    public boolean checkEligibility(String employeeId, String planId)
            throws EligibilityCheckFailedException,
                   NotEligibleForPlanException {

        System.out.println("\n[EligibilityService] Checking eligibility "
                + "for Employee [" + employeeId
                + "] on Plan [" + planId + "]");

        // ── Step 1: Fetch employee profile ───────────────────────────────────
        EmployeeProfile profile;
        try {
            profile = profileFacade.getProfile(employeeId);
        } catch (ProfileNotFoundException e) {
            throw new EligibilityCheckFailedException(
                    "Eligibility check failed: Employee not found — "
                            + employeeId, e);
        }

        // ── Step 2: Fetch benefit plan ───────────────────────────────────────
        BenefitPlan plan;
        try {
            plan = catalogFacade.getPlan(planId);
        } catch (PlanNotFoundException e) {
            throw new EligibilityCheckFailedException(
                    "Eligibility check failed: Plan not found — "
                            + planId, e);
        }

        // ── Step 3: Run all strategies ───────────────────────────────────────
        System.out.println("[EligibilityService] Running "
                + strategies.size() + " eligibility rules...");

        for (EligibilityStrategy strategy : strategies) {
            boolean passed = strategy.isEligible(profile, plan);

            if (!passed) {
                // ── Step 4: Any failure → deny enrollment ────────────────────
                throw new NotEligibleForPlanException(
                        employeeId,
                        planId,
                        strategy.getStrategyName() + " check failed");
            }
        }

        // ── Step 5: All rules passed ─────────────────────────────────────────
        System.out.println("[EligibilityService] ✓ Employee ["
                + employeeId + "] is ELIGIBLE for Plan [" + planId + "]");

        return true;
    }
}
