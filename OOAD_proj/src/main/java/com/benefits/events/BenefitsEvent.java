package com.benefits.events;

import java.time.LocalDateTime;

/**
 * Base class for all system events.
 * OBSERVER PATTERN — the payload carried from publisher to all subscribers.
 *
 * SOLID - OCP: New event types extend this class without modifying existing ones.
 * GRASP - Information Expert: Each subclass knows its own event data.
 */
public abstract class BenefitsEvent {

    private final String employeeId;
    private final String performedBy;
    private final LocalDateTime occurredAt;

    public BenefitsEvent(String employeeId, String performedBy) {
        this.employeeId  = employeeId;
        this.performedBy = performedBy;
        this.occurredAt  = LocalDateTime.now();
    }

    public String getEmployeeId()       { return employeeId; }
    public String getPerformedBy()      { return performedBy; }
    public LocalDateTime getOccurredAt(){ return occurredAt; }

    /** Each event subclass describes itself. */
    public abstract String getEventDescription();
}
