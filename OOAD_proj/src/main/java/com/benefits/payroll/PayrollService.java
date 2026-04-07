package main.java.com.benefits.payroll;

/*
 * GRASP: Protected Variations
 * ----------------------------
 * PURPOSE:
 * This interface shields the system from changes in payroll logic or external systems.
 *
 * WHY IMPORTANT?
 * - If payroll implementation changes (e.g., new vendor, API, or rules),
 *   only the implementing class changes — not the entire system.
 *
 * DESIGN ROLE:
 * - Acts as an abstraction layer between business logic and payroll system
 */

public interface PayrollService {

    /**
     * Calculates deduction based on input amount
     *
     * @param amount → input value (e.g., reimbursement or benefit amount)
     * @return calculated deduction value
     *
     * PURPOSE:
     * - Encapsulates payroll-specific calculation logic
     *
     * DESIGN BENEFIT:
     * - Business layer does not need to know how deduction is computed
     *
     * SOLID PRINCIPLE:
     * - OCP (Open/Closed Principle):
     *   New deduction logic can be introduced without modifying existing code
     */
    double calculateDeduction(double amount);

    /**
     * Processes payroll for a given employee
     *
     * @param employeeId → ID of the employee
     * @param deduction  → computed deduction to apply
     *
     * PURPOSE:
     * - Executes payroll operation (e.g., updating salary, applying deductions)
     *
     * EXCEPTION HANDLING:
     * - Throws Exception to indicate failure in payroll processing
     *   (Can be refined to use custom exceptions like PayrollSyncException)
     *
     * GRASP PRINCIPLES:
     * - Low Coupling:
     *   System interacts only with this interface, not concrete implementations
     *
     * - Indirection:
     *   Calls are routed through this abstraction instead of directly invoking external systems
     *
     * SOLID PRINCIPLES:
     * - DIP (Dependency Inversion Principle):
     *   High-level modules depend on this abstraction, not concrete payroll classes
     */
    void processPayroll(int employeeId, double deduction) throws Exception;
}