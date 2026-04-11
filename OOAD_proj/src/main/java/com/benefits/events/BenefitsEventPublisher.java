package com.benefits.events;

import java.util.ArrayList;
import java.util.List;

/**
 * OBSERVER PATTERN — Subject (Publisher / Event Bus).
 *
 * Maintains a registry of all listeners and broadcasts events to each.
 * This is the central glue that decouples all modules from each other.
 *
 * GRASP - Pure Fabrication: No domain meaning — exists purely to wire modules.
 * GRASP - Indirection: Source modules publish here; they never talk to
 *                      Audit or Notification directly.
 * SOLID - OCP: New listeners subscribe without modifying this class at all.
 * SOLID - DIP: Depends only on BenefitsEventListener interface.
 */
public class BenefitsEventPublisher {

    // ── Registered Listeners ─────────────────────────────────────────────────
    private final List<BenefitsEventListener> listeners = new ArrayList<>();

    // ── Subscribe / Unsubscribe ───────────────────────────────────────────────

    /**
     * Registers a new listener to receive all published events.
     *
     * @param listener The listener to register.
     */
    public void subscribe(BenefitsEventListener listener) {
        listeners.add(listener);
        System.out.println("[EventPublisher] Subscribed: "
                + listener.getClass().getSimpleName());
    }

    /**
     * Removes a listener from the registry.
     *
     * @param listener The listener to remove.
     */
    public void unsubscribe(BenefitsEventListener listener) {
        listeners.remove(listener);
        System.out.println("[EventPublisher] Unsubscribed: "
                + listener.getClass().getSimpleName());
    }

    // ── Publish ───────────────────────────────────────────────────────────────

    /**
     * Publishes an event to ALL registered listeners.
     * Each listener reacts independently — completely decoupled.
     *
     * @param event The event to broadcast.
     */
    public void publish(BenefitsEvent event) {
        System.out.println("\n[EventPublisher] Publishing: "
                + event.getClass().getSimpleName()
                + " | " + event.getEventDescription());
        for (BenefitsEventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}
