package com.benefits.audit.exception;

/**
 * Exception thrown when an action violates a compliance rule.
 *
 * Error Code: COMPLIANCE_VIOLATION_DETECTED
 * Category  : MAJOR — Block action; alert compliance officer.
 *
 * SOLID - SRP: Represents one specific kind of failure.
 */
public class ComplianceViolationException extends RuntimeException {

    private final String employeeId;
    private final String violatedRule;

    public ComplianceViolationException(String employeeId, String violatedRule) {
        super("[COMPLIANCE_VIOLATION_DETECTED] Activity violates compliance rules. " +
                "Employee ID: " + employeeId + " | Rule: " + violatedRule);
        this.employeeId   = employeeId;
        this.violatedRule = violatedRule;
    }

    public String getEmployeeId()   { return employeeId; }
    public String getViolatedRule() { return violatedRule; }
}
