package com.benefits.manager;

import com.benefits.model.EmployeeProfile;
import com.benefits.dao.EmployeeProfileDAO;
import com.benefits.observer.ProfileEventNotifier;
import com.benefits.observer.ProfileObserver;
import com.benefits.exception.ProfileNotFoundException;
import com.benefits.exception.DuplicateProfileException;

import java.util.*;

/**
 * Singleton manager for all employee benefit profiles.
 *
 * CREATIONAL PATTERN - Singleton:
 * Only one instance of this manager exists across the entire system.
 * This ensures a single, consistent source of truth for all employee
 * profile data. No two parts of the system can have conflicting views
 * of employee data.
 *
 * GRASP - Controller:
 * This class acts as the system controller for all profile-related
 * operations. It delegates data storage, validation, and notification
 * to appropriate collaborators — it does not do everything itself.
 *
 * GRASP - Creator:
 * This manager is responsible for creating and storing EmployeeProfile
 * objects, since it closely uses and manages them.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This class is only responsible for managing the lifecycle of employee
 * profiles (create, retrieve, update, delete). Notification is delegated
 * to ProfileEventNotifier.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * This class depends on the ProfileEventNotifier abstraction layer
 * rather than directly calling observers.
 */

public class EmployeeBenefitsProfileManager {
    // ── Singleton Instance ───────────────────────────────────────────────────

    /**
     * The single instance of this manager.
     * Volatile ensures thread-safe visibility across threads.
     */
    private static volatile EmployeeBenefitsProfileManager instance = null;

    // ── DAO Dependency (provided by DB team) ─────────────────────────────────

    /**
     * Data Access Object for employee profiles.
     * This is injected at runtime — the DB team provides the implementation.
     * We only depend on the interface, never the concrete class.
     */
    private final EmployeeProfileDAO profileDAO;

    // ── Observer Notifier ────────────────────────────────────────────────────

    /**
     * Notifier used to broadcast profile events to all registered observers.
     */
    private final ProfileEventNotifier notifier = new ProfileEventNotifier();

    // ── Private Constructor (Singleton + Dependency Injection) ───────────────

    /**
     * Private constructor accepts the DAO implementation at creation time.
     * This is how we decouple ourselves from the DB team's implementation.
     *
     * @param profileDAO The DAO implementation provided by the DB team.
     */
    private EmployeeBenefitsProfileManager(EmployeeProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
        System.out.println("[ProfileManager] Singleton instance created.");
        System.out.println("[ProfileManager] Connected to DAO: "
                + profileDAO.getClass().getSimpleName());
    }

    // ── Singleton Access Method ───────────────────────────────────────────────

    /**
     * Returns the single instance of EmployeeBenefitsProfileManager.
     * Uses double-checked locking for thread safety.
     * Must be initialized with a DAO implementation on first call.
     *
     * @param profileDAO The DAO implementation to inject (DB team provides).
     * @return The singleton instance.
     */
    public static EmployeeBenefitsProfileManager getInstance(
            EmployeeProfileDAO profileDAO) {
        if (instance == null) {
            synchronized (EmployeeBenefitsProfileManager.class) {
                if (instance == null) {
                    instance = new EmployeeBenefitsProfileManager(profileDAO);
                }
            }
        }
        return instance;
    }

    /**
     * Returns the already-initialized singleton instance.
     * Call this after the first getInstance(profileDAO) call.
     *
     * @return The singleton instance.
     * @throws IllegalStateException if manager hasn't been initialized yet.
     */
    public static EmployeeBenefitsProfileManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(
                    "[ProfileManager] Not initialized. " +
                            "Call getInstance(EmployeeProfileDAO) first.");
        }
        return instance;
    }

    // ── Observer Registration ─────────────────────────────────────────────────

    /**
     * Registers an observer to be notified on profile events.
     *
     * @param observer The observer to register.
     */
    public void registerObserver(ProfileObserver observer) {
        notifier.registerObserver(observer);
    }

    /**
     * Removes an observer from the notification list.
     *
     * @param observer The observer to remove.
     */
    public void removeObserver(ProfileObserver observer) {
        notifier.removeObserver(observer);
    }
    // ── Core Operations ───────────────────────────────────────────────────────

    /**
     * Requests creation of a new employee profile via the DAO.
     * Notifies all registered observers after successful creation.
     *
     * @param profile The new EmployeeProfile to add.
     * @throws DuplicateProfileException if a profile with the same ID exists.
     */
    public void createProfile(EmployeeProfile profile)
            throws DuplicateProfileException {

        // Ask the DAO (DB team) to check for duplicates
        if (profileDAO.existsById(profile.getEmployeeId())) {
            throw new DuplicateProfileException(
                    "Profile already exists for Employee ID: "
                            + profile.getEmployeeId());
        }

        // Delegate saving to the DAO
        profileDAO.save(profile);
        System.out.println("\n[ProfileManager] Profile creation requested for: "
                + profile.getName()
                + " (ID: " + profile.getEmployeeId() + ")");

        // Notify all observers about the new profile
        notifier.notifyObservers(profile);
    }

    /**
     * Requests an employee profile by ID from the DAO.
     *
     * @param employeeId The ID of the employee to look up.
     * @return The matching EmployeeProfile from the database.
     * @throws ProfileNotFoundException if no profile exists for the given ID.
     */
    public EmployeeProfile getProfile(String employeeId)
            throws ProfileNotFoundException {

        // Request data from the DB team's implementation
        EmployeeProfile profile = profileDAO.findById(employeeId);

        if (profile == null) {
            throw new ProfileNotFoundException(
                    "No profile found for Employee ID: " + employeeId);
        }

        return profile;
    }

    /**
     * Requests an update to an existing employee profile via the DAO.
     * Notifies all observers after a successful update.
     *
     * @param profile The updated EmployeeProfile.
     * @throws ProfileNotFoundException if the profile doesn't exist yet.
     */
    public void updateProfile(EmployeeProfile profile)
            throws ProfileNotFoundException {

        // Guard: Cannot update a non-existent profile
        if (!profileDAO.existsById(profile.getEmployeeId())) {
            throw new ProfileNotFoundException(
                    "Cannot update. No profile found for Employee ID: "
                            + profile.getEmployeeId());
        }

        // Delegate update to the DAO
        profileDAO.update(profile);
        System.out.println("\n[ProfileManager] Profile update requested for: "
                + profile.getName()
                + " (ID: " + profile.getEmployeeId() + ")");

        // Notify observers about the update
        notifier.notifyObservers(profile);
    }

    /**
     * Requests deletion of an employee profile via the DAO.
     *
     * @param employeeId The ID of the employee whose profile to delete.
     * @throws ProfileNotFoundException if no profile exists for the given ID.
     */
    public void deleteProfile(String employeeId)
            throws ProfileNotFoundException {

        if (!profileDAO.existsById(employeeId)) {
            throw new ProfileNotFoundException(
                    "Cannot delete. No profile found for Employee ID: "
                            + employeeId);
        }

        profileDAO.delete(employeeId);
        System.out.println("\n[ProfileManager] Profile deletion requested "
                + "for ID: " + employeeId);
    }

    /**
     * Requests all employee profiles from the DAO.
     *
     * @return A collection of all EmployeeProfile objects from the database.
     */
    public Collection<EmployeeProfile> getAllProfiles() {
        return profileDAO.findAll();
    }
}
