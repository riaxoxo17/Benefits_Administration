package com.benefits.events;

/**
 * Event fired when an employee files a claim.
 * OBSERVER PATTERN — concrete event payload.
 */
public class ClaimFiledEvent extends BenefitsEvent {

    private final String claimId;
    private final double claimAmount;

    public ClaimFiledEvent(String employeeId, String claimId,
                           double claimAmount, String filedBy) {
        super(employeeId, filedBy);
        this.claimId     = claimId;
        this.claimAmount = claimAmount;
    }

    public String getClaimId()     { return claimId; }
    public double getClaimAmount() { return claimAmount; }

    @Override
    public String getEventDescription() {
        return "Claim [" + claimId + "] filed for amount INR " + claimAmount;
    }
}
