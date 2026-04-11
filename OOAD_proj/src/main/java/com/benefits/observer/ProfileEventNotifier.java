package com.benefits.observer;

import com.benefits.model.EmployeeProfile;
import java.util.*;

/**
 * Acts as the Subject in the Observer pattern.
 * Maintains a list of observers and notifies them when
 * an employee profile is created or updated.
 *
 * GRASP - Low Coupling: The notifier doesn't know what
 * the observers do with the update — it just fires the event.
 *
 * GRASP - High Cohesion: This class has one clear job —
 * manage and notify observers. Nothing else.
 *
 * SOLID - Open/Closed Principle (OCP): New observers can be
 * added without modifying this class at all.
 */

public class ProfileEventNotifier {

    // ── List of registered observers ────────────────────────────────────────
    private List<ProfileObserver> observers = new ArrayList<>();

    /**
     * Registers a new observer to listen for profile events.
     *
     * @ param observer The observer to add.
     */
    public void registerObserver(ProfileObserver observer) {
        observers.add(observer);
        System.out.println("[Notifier] Observer registered: "
                + observer.getClass().getSimpleName());
    }

    /**
     * Removes an observer from the notification list.
     *
     * @ param observer The observer to remove.
     */
    public void removeObserver(ProfileObserver observer) {
        observers.remove(observer);
        System.out.println("[Notifier] Observer removed: "
                + observer.getClass().getSimpleName());
    }

    /**
     * Notifies all registered observers that a profile has been
     * created or updated.
     *
     * @ param profile The updated EmployeeProfile.
     */
    public void notifyObservers(EmployeeProfile profile) {
        System.out.println("[Notifier] Notifying " + observers.size()
                + " observer(s) for Employee ID: " + profile.getEmployeeId());
        for (ProfileObserver observer : observers) {
            observer.onProfileUpdated(profile);
        }
    }

}
