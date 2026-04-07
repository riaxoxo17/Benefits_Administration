package main.java.com.benefits.exceptions;

/**
 * Custom Exception: PayrollSyncException
 *
 * PURPOSE:
 * This exception is thrown when there is a failure while synchronizing
 * claim or employee-related data with the payroll system.
 *
 * It represents an integration-level failure between subsystems.
 *
 * WHERE IT IS USED:
 * - During integration with external/internal payroll systems
 * - When updating salary deductions, reimbursements, or benefits
 * - In service or integration layers responsible for payroll communication
 *
 * EXAMPLES OF FAILURE SCENARIOS:
 * - Payroll system is unreachable
 * - Data format mismatch between systems
 * - API call failure or timeout
 * - Inconsistent employee or claim data during sync
 *
 * DESIGN PATTERN CONTEXT:
 * - Facade Pattern (Structural Pattern):
 *   A payroll integration service may act as a facade, and this exception
 *   is thrown when underlying complexities fail.
 *
 * - Chain of Responsibility (Behavioral Pattern):
 *   If payroll sync is part of the claim processing chain, this exception
 *   can terminate the flow when integration fails.
 *
 * GRASP PRINCIPLES:
 * - Low Coupling:
 *   Core business logic is not tightly dependent on payroll system details.
 *
 * - Protected Variations:
 *   Changes in payroll system APIs or vendors do not affect higher-level
 *   modules because this exception abstracts integration failures.
 *
 * SOLID PRINCIPLES:
 * - SRP (Single Responsibility Principle):
 *   This class is only responsible for representing payroll synchronization errors.
 *
 * - DIP (Dependency Inversion Principle):
 *   Higher-level modules depend on abstractions (interfaces/exceptions),
 *   not on concrete payroll system implementations.
 *
 * EXCEPTION HANDLING STRATEGY:
 * - This is a checked exception (extends Exception)
 *   → Forces explicit handling of integration failures
 *   → Enables retry logic, fallback mechanisms, or alerting
 *
 * DESIGN DECISION:
 * - Separates integration errors from validation and processing errors
 * - Improves clarity when debugging cross-system issues
 *
 * REAL-WORLD ANALOGY:
 * - Similar to a reimbursement being approved but failing to reflect
 *   in the employee’s salary due to payroll system issues.
 */
public class PayrollSyncException extends Exception {

    /**
     * Constructor to initialize the exception with a meaningful message.
     *
     * @param msg Description of the synchronization failure
     *
     * WHY IMPORTANT:
     * - Helps identify whether the issue is network-related, API-related,
     *   or data-related
     * - Useful for logging, debugging, and monitoring integration health
     */
    public PayrollSyncException(String msg) {
        super(msg);
    }
}