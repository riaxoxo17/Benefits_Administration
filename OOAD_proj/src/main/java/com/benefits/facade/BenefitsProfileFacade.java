package com.benefits.facade;

import com.benefits.dao.EmployeeProfileDAO;
import com.benefits.exception.DuplicateProfileException;
import com.benefits.exception.ProfileNotFoundException;
import com.benefits.manager.EmployeeBenefitsProfileManager;
import com.benefits.model.EmployeeProfile;
import com.benefits.observer.EligibilityCheckListener;

import java.util.Collection;

/**
 * Facade for the Employee Benefits Profile subsystem.
 *
 * STRUCTURAL PATTERN - Facade:
 * Provides a single, simplified entry point to the entire profile
 * subsystem. External components (other subsystems, UI, APIs) should
 * ONLY interact with this class — never directly with the manager,
 * DAO, or observer internals.
 *
 * Think of this as the "front desk" — callers don't need to know
 * what happens behind the scenes. They just call the facade.
 *
 * GRASP - Low Coupling:
 * All external components depend only on this facade, not on the
 * internal classes. This means we can change internals freely
 * without breaking anything outside.
 *
 * GRASP - High Cohesion:
 * This class only does one thing — provide a clean interface to
 * the profile subsystem. All actual logic lives elsewhere.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Only responsible for delegating calls to the right internal
 * components. No business logic lives here.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * Accepts the DAO interface at construction — not a concrete class.
 */
public class BenefitsProfileFacade {

    // ── Internal Manager Reference ────────────────────────────────────────────

    /**
     * The singleton manager that handles all profile operations.
     * The facade delegates all calls to this manager.
     */
    private final EmployeeBenefitsProfileManager profileManager;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * Initializes the facade with the DAO implementation provided
     * by the database team. Sets up the manager and registers
     * default observers automatically.
     *
     * @param profileDAO The DAO implementation from the DB team.
     */
    public BenefitsProfileFacade(EmployeeProfileDAO profileDAO) {

        // Initialize the singleton manager with the DAO
        this.profileManager = EmployeeBenefitsProfileManager
                .getInstance(profileDAO);

        // Register default observers
        // The EligibilityCheckListener reacts to every profile event
        profileManager.registerObserver(new EligibilityCheckListener());

        System.out.println("[Facade] BenefitsProfileFacade initialized.");
        System.out.println("[Facade] Ready to accept requests.\n");
    }

    // ── Public API ────────────────────────────────────────────────────────────

    /**
     * Creates a new employee benefits profile.
     * Delegates to the profile manager which handles storage
     * via the DAO and notifies all observers.
     *
     * @param profile The new EmployeeProfile to create.
     * @throws DuplicateProfileException if profile already exists.
     */
    public void createProfile(EmployeeProfile profile)
            throws DuplicateProfileException {
        System.out.println("[Facade] Received → createProfile() "
                + "for ID: " + profile.getEmployeeId());
        profileManager.createProfile(profile);
    }

    /**
     * Retrieves an employee profile by ID.
     * Delegates to the profile manager which fetches from the DAO.
     *
     * @param employeeId The unique employee ID to look up.
     * @return The matching EmployeeProfile.
     * @throws ProfileNotFoundException if no profile exists for the ID.
     */
    public EmployeeProfile getProfile(String employeeId)
            throws ProfileNotFoundException {
        System.out.println("[Facade] Received → getProfile() "
                + "for ID: " + employeeId);
        return profileManager.getProfile(employeeId);
    }

    /**
     * Updates an existing employee profile.
     * Delegates to the profile manager and notifies observers.
     *
     * @param profile The updated EmployeeProfile.
     * @throws ProfileNotFoundException if profile does not exist.
     */
    public void updateProfile(EmployeeProfile profile)
            throws ProfileNotFoundException {
        System.out.println("[Facade] Received → updateProfile() "
                + "for ID: " + profile.getEmployeeId());
        profileManager.updateProfile(profile);
    }

    /**
     * Deletes an employee profile by ID.
     * Delegates to the profile manager which removes via the DAO.
     *
     * @param employeeId The unique employee ID to delete.
     * @throws ProfileNotFoundException if profile does not exist.
     */
    public void deleteProfile(String employeeId)
            throws ProfileNotFoundException {
        System.out.println("[Facade] Received → deleteProfile() "
                + "for ID: " + employeeId);
        profileManager.deleteProfile(employeeId);
    }

    /**
     * Retrieves all employee profiles from the database.
     * Delegates to the profile manager which fetches via the DAO.
     *
     * @return A collection of all EmployeeProfile objects.
     */
    public Collection<EmployeeProfile> getAllProfiles() {
        System.out.println("[Facade] Received → getAllProfiles()");
        return profileManager.getAllProfiles();
    }
}