package main.java.com.benefits.payroll;

import main.java.com.benefits.exceptions.PayrollSyncException;
import main.java.com.benefits.exceptions.SystemIntegrationException;

/*
 * SRP: Handles only payroll logic
 *
 * PURPOSE:
 * This class provides the concrete implementation of PayrollService.
 * It contains the actual business logic related to payroll operations.
 *
 * DESIGN ROLE:
 * - Implements the PayrollService interface
 * - Used by PayrollAdapter to perform payroll operations
 *
 * DESIGN PATTERN CONTEXT:
 * - Works with Adapter Pattern (Structural):
 *   PayrollAdapter delegates calls to this implementation
 *
 * GRASP PRINCIPLES:
 * - Information Expert:
 *   This class contains the logic required for payroll calculations and processing
 *
 * - Low Coupling:
 *   Other parts of the system depend only on PayrollService interface,
 *   not this concrete implementation
 *
 * SOLID PRINCIPLES:
 * - SRP (Single Responsibility Principle):
 *   This class is responsible only for payroll-related logic
 *
 * - DIP (Dependency Inversion Principle):
 *   Used via PayrollService abstraction, not directly by higher-level modules
 */
public class PayrollServiceImpl implements PayrollService {

    /**
     * Calculates deduction based on the given amount
     *
     * @param amount → input value (e.g., benefit or reimbursement amount)
     * @return 10% of the amount as deduction
     *
     * DESIGN DECISION:
     * - Simple fixed percentage logic (can be extended later)
     *
     * OCP:
     * - Logic can be modified or extended without affecting callers
     */
    public double calculateDeduction(double amount) {
        return amount * 0.1;
    }

    /**
     * Processes payroll for an employee
     *
     * @param employeeId → ID of the employee
     * @param deduction  → calculated deduction amount
     *
     * PROCESS FLOW:
     * 1. Validate deduction
     * 2. Simulate payroll update
     * 3. Handle integration/system failures
     *
     * EXCEPTION STRATEGY:
     * - Uses layered exception handling:
     *   → SystemIntegrationException → specific system failure
     *   → PayrollSyncException → generic integration failure
     *
     * DESIGN BENEFIT:
     * - Separates specific vs generic failures
     * - Improves debugging and error reporting
     */
    public void processPayroll(int employeeId, double deduction) throws Exception {
        try {

            /**
             * Validation step
             *
             * If deduction is invalid (negative),
             * treat it as a system-level failure
             */
            if (deduction < 0) {
                throw new SystemIntegrationException("SYSTEM_INTEGRATION_FAILURE");
            }

            /**
             * Simulating payroll update
             *
             * In real system:
             * - This would call DB / external payroll API
             */
            System.out.println("Payroll updated for employee " + employeeId);

        } catch (SystemIntegrationException e) {

            /**
             * Re-throw specific exception
             *
             * WHY:
             * - Preserve exact failure cause
             * - Avoid losing context
             */
            throw e;

        } catch (Exception e) {

            /**
             * Wrap unknown exceptions into PayrollSyncException
             *
             * WHY:
             * - Abstracts low-level/system errors
             * - Provides consistent error handling for callers
             *
             * DESIGN PATTERN:
             * - Exception Wrapping (common enterprise pattern)
             */
            throw new PayrollSyncException("PAYROLL_SYNC_FAILURE");
        }
    }
}