package com.benefits.events;

/**
 * Event fired when payroll is updated after an approved enrollment.
 * OBSERVER PATTERN — concrete event payload.
 */
public class PayrollUpdatedEvent extends BenefitsEvent {

    private final String deductionId;
    private final double deductionAmount;

    public PayrollUpdatedEvent(String employeeId, String deductionId,
                               double deductionAmount, String updatedBy) {
        super(employeeId, updatedBy);
        this.deductionId     = deductionId;
        this.deductionAmount = deductionAmount;
    }

    public String getDeductionId()     { return deductionId; }
    public double getDeductionAmount() { return deductionAmount; }

    @Override
    public String getEventDescription() {
        return "Payroll updated — Deduction [" + deductionId
                + "] of INR " + deductionAmount;
    }
}
