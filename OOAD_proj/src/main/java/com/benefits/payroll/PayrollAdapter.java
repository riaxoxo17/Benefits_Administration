package main.java.com.benefits.payroll;

/*
 * Structural Pattern: Adapter
 * ----------------------------
 * PURPOSE:
 * This class acts as a bridge between your internal system and the PayrollService.
 *
 * WHY ADAPTER?
 * - The existing PayrollService may have a different interface
 * - This adapter converts your system’s expected inputs into a format
 *   compatible with PayrollService
 *
 * BENEFIT:
 * - Allows integration without modifying existing PayrollService code
 */

public class PayrollAdapter {

    /**
     * Reference to the adaptee (existing system/service)
     *
     * In Adapter Pattern terminology:
     * - PayrollService = Adaptee
     * - PayrollAdapter = Adapter
     */
    private PayrollService payrollService;

    /**
     * Constructor injection
     *
     * PURPOSE:
     * - Injects the PayrollService dependency
     *
     * SOLID PRINCIPLE:
     * - DIP (Dependency Inversion Principle):
     *   Depends on abstraction (PayrollService), not implementation details
     */
    public PayrollAdapter(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    /**
     * Unified method used by the system to run payroll
     *
     * PARAMETERS:
     * @param employeeId → ID of the employee
     * @param amount     → Amount to be processed (e.g., claim reimbursement)
     *
     * PROCESS FLOW:
     * 1. Convert system input into payroll-compatible format
     * 2. Call PayrollService methods
     *
     * DESIGN PATTERN ROLE:
     * - Translates one interface into another
     *
     * GRASP PRINCIPLES:
     * - Indirection:
     *   Adapter sits between system and payroll service to reduce direct dependency
     *
     * - Low Coupling:
     *   Business logic does not directly depend on PayrollService internals
     *
     * SOLID PRINCIPLES:
     * - SRP (Single Responsibility Principle):
     *   This class only handles adaptation logic
     *
     * - OCP (Open/Closed Principle):
     *   New payroll systems can be supported by adding new adapters
     *
     * EXCEPTION HANDLING:
     * - Throws generic Exception (can be improved by using custom exceptions
     *   like PayrollSyncException for better design clarity)
     */
    public void runPayroll(int employeeId, double amount) throws Exception {

        /**
         * Step 1: Convert amount into deduction using PayrollService logic
         *
         * WHY:
         * - Payroll system may not accept raw amount directly
         * - Requires transformation before processing
         */
        double deduction = payrollService.calculateDeduction(amount);

        /**
         * Step 2: Process payroll with adapted data
         *
         * RESULT:
         * - Deduction is applied to employee payroll
         */
        payrollService.processPayroll(employeeId, deduction);
    }
}