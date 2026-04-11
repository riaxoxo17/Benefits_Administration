package main.java.com.benefits.eligibility.strategy;

import main.java.com.benefits.model.EmployeeProfile;
import main.java.com.benefits.catalog.model.BenefitPlan;

/**
 * Strategy interface for eligibility rule evaluation.
 *
 * BEHAVIOURAL PATTERN - Strategy:
 * Defines a family of eligibility algorithms — one per rule type.
 * Each concrete strategy encapsulates a single eligibility rule.
 * The EligibilityServiceImpl holds a list of these strategies
 * and runs them all. Adding a new rule = adding a new class.
 * Zero changes to existing code required.
 *
 * Without Strategy, we would have one giant if-else block:
 *   if (employmentType == FULL_TIME) { ... }
 *   else if (salaryBand.equals("B3")) { ... }
 *   else if (waitingPeriod > 6) { ... }
 * With Strategy, each rule lives in its own class — clean,
 * testable, and swappable independently.
 *
 * SOLID - Open/Closed Principle (OCP):
 * Adding a new eligibility rule only requires creating a new
 * strategy class that implements this interface. No existing
 * strategies or the service are ever touched.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Each concrete strategy is solely responsible for ONE rule.
 *
 * SOLID - Interface Segregation Principle (ISP):
 * This interface is deliberately small — two focused methods.
 * Implementors are not burdened with unrelated methods.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * EligibilityServiceImpl depends on this abstraction, never
 * on any concrete strategy class.
 *
 * GRASP - Polymorphism:
 * Different eligibility strategies are treated uniformly
 * through this interface. The service does not need to know
 * which concrete strategy it is running.
 *
 * GRASP - Low Coupling:
 * The service is completely decoupled from the rule logic.
 * Changing how salary bands are evaluated requires touching
 * only SalaryBandEligibilityStrategy — nothing else.
 */
public interface EligibilityStrategy {

    /**
     * Evaluates whether the given employee is eligible for
     * the given benefit plan according to THIS strategy's rule.
     *
     * @param profile The employee profile to evaluate.
     * @param plan    The benefit plan being applied for.
     * @return true if eligible under this rule, false otherwise.
     */
    boolean isEligible(EmployeeProfile profile, BenefitPlan plan);

    /**
     * Returns a human-readable name for this strategy.
     * Used in log messages and eligibility reports.
     *
     * @return The name of this strategy.
     */
    String getStrategyName();
}
