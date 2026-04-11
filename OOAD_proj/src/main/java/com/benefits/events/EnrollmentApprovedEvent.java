package com.benefits.events;

/**
 * Event fired when an enrollment is approved by a manager.
 * OBSERVER PATTERN — concrete event payload.
 */
public class EnrollmentApprovedEvent extends BenefitsEvent {

    private final String enrollmentId;
    private final String planId;

    public EnrollmentApprovedEvent(String employeeId, String enrollmentId,
                                   String planId, String approvedBy) {
        super(employeeId, approvedBy);
        this.enrollmentId = enrollmentId;
        this.planId       = planId;
    }

    public String getEnrollmentId() { return enrollmentId; }
    public String getPlanId()       { return planId; }

    @Override
    public String getEventDescription() {
        return "Enrollment [" + enrollmentId + "] for Plan ["
                + planId + "] APPROVED by " + getPerformedBy();
    }
}
