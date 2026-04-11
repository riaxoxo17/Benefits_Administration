package com.benefits.observer;

import com.benefits.model.EmployeeProfile;

/**
 * Observer interface for the Observer design pattern (Behavioural Pattern).
 *
 * SOLID - Dependency Inversion Principle (DIP): High-level modules
 * should not depend on low-level modules. Both should depend on
 * abstractions. This interface is that abstraction.
 *
 * SOLID - Interface Segregation Principle (ISP): This interface is
 * intentionally kept small and focused — observers only need to
 * implement one method.
 *
 * GRASP - Low Coupling: Components that need to react to profile
 * changes depend on this interface, not on concrete implementations,
 * keeping coupling low across the system.
 */

public interface ProfileObserver {

    /**
     * Called automatically whenever an employee profile is
     * created or updated.
     *
     * @ param profile The employee profile that was changed.
     */
    void onProfileUpdated(EmployeeProfile profile);
}
