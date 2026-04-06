package main.java.com.benefits.catalog.strategy;

/**
 * Strategy interface for benefit plan cost calculation.
 *
 * BEHAVIOURAL PATTERN - Strategy:
 * Defines a family of cost calculation algorithms, one per
 * plan type. Each plan type has its own pricing logic —
 * Health, Dental, Retirement, and Insurance all calculate
 * costs differently. This interface is the contract they
 * all follow.
 *
 * Without Strategy pattern, we would have a massive
 * if-else or switch block inside a single cost method.
 * With Strategy, each algorithm lives in its own class —
 * clean, testable, and swappable at runtime.
 *
 * SOLID - Open/Closed Principle (OCP):
 * Adding a new plan type (e.g., Vision) only requires
 * creating a new strategy class. Zero changes to existing
 * strategies or the plans that use them.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Each strategy class is solely responsible for one
 * plan type's cost calculation logic. Nothing else.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * Plan classes depend on this interface, not on any
 * concrete strategy implementation.
 *
 * GRASP - Low Coupling:
 * Plans are decoupled from cost logic. You can swap
 * a plan's pricing strategy without touching the plan.
 */
public interface CostCalculationStrategy {

    /**
     * Calculates the final cost of a benefit plan based on
     * the base cost and any plan-specific pricing rules.
     *
     * @param baseCost       The base premium/cost of the plan.
     * @param coverageLimit  The maximum coverage the plan offers.
     * @param durationMonths The duration of the plan in months.
     * @return The final calculated cost as a double.
     */
    double calculate(double baseCost,
            double coverageLimit,
            int durationMonths);
}