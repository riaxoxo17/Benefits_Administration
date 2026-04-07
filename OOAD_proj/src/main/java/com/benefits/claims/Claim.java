package main.java.com.benefits.claims;

/*
 * Class: Claim
 * ------------------------------------------------------------
 * Responsibility:
 * This class represents a claim submitted by an employee.
 * It acts as a data carrier (Entity/Model) that holds all
 * necessary information required for claim processing.
 *
 * Attributes:
 * - employeeId     → Identifies the employee submitting the claim
 * - amount         → Monetary value of the claim
 * - hasDocuments   → Indicates whether supporting documents are attached
 * - policyValid    → Indicates whether the claim complies with policy rules
 *
 * Design Role:
 * → This is a Plain Old Java Object (POJO) used across multiple modules:
 *   - Validation (Chain of Responsibility)
 *   - Workflow (State Pattern)
 *   - Payroll Integration
 *
 * GRASP Principles:
 * → Information Expert:
 *   - This class contains all relevant claim-related data.
 *   - Other components query this object for decision-making.
 *
 * SOLID Principles:
 * → Single Responsibility Principle (SRP):
 *   - This class is only responsible for storing claim data.
 *   - It does NOT perform validation, workflow, or processing.
 *
 * → Low Coupling:
 *   - No dependency on other modules (validation, workflow, payroll).
 *   - Makes it reusable and easy to maintain.
 *
 * Usage in System:
 * - Passed through validation chain (Document → Amount → Policy)
 * - Used by workflow engine to determine approval/rejection
 * - Used by payroll module to process reimbursements
 */
public class Claim {

    // Unique identifier for the employee raising the claim
    private int employeeId;

    // Monetary value of the claim
    private int amount;

    // Flag indicating whether required documents are submitted
    private boolean hasDocuments;

    // Flag indicating whether claim satisfies policy conditions
    private boolean policyValid;

    /*
     * Constructor:
     * Initializes a claim with all required attributes.
     */
    public Claim(int employeeId, int amount, boolean hasDocuments, boolean policyValid) {
        this.employeeId = employeeId;
        this.amount = amount;
        this.hasDocuments = hasDocuments;
        this.policyValid = policyValid;
    }

    // Getter for employee ID
    public int getEmployeeId() { return employeeId; }

    // Getter for claim amount
    public int getAmount() { return amount; }

    // Returns whether supporting documents are present
    public boolean hasDocuments() { return hasDocuments; }

    // Returns whether claim satisfies policy rules
    public boolean isPolicyValid() { return policyValid; }
}