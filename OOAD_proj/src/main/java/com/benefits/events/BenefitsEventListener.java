package com.benefits.events;

/**
 * OBSERVER PATTERN — Observer interface.
 *
 * All modules that want to react to system events implement this.
 * AuditEventListener and NotificationEventListener are the two key implementations.
 *
 * GRASP - Indirection: Decouples event sources from event handlers.
 * SOLID - DIP: Event publishers depend on this abstraction, not concrete listeners.
 * SOLID - ISP: Single focused method — observers only implement what they need.
 */
public interface BenefitsEventListener {

    /**
     * Called whenever a system event is published.
     *
     * @param event The event that occurred.
     */
    void onEvent(BenefitsEvent event);
}
