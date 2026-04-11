package com.benefits.controller;

import com.benefits.exception.DuplicateProfileException;
import com.benefits.exception.ProfileNotFoundException;
import com.benefits.facade.BenefitsProfileFacade;
import com.benefits.model.EmployeeProfile;

import java.util.Collection;

/**
 * Controller for the Employee Benefits Profile subsystem.
 *
 * GRASP - Controller:
 * This class is the designated handler for all incoming system
 * events related to employee profiles. It receives requests from
 * the outside world (UI, API, other subsystems) and delegates
 * them to the Facade — it never performs business logic itself.
 *
 * Think of this as the "traffic director" — it receives the
 * request, validates the inputs, and routes to the right place.
 *
 * GRASP - Low Coupling:
 * The controller only knows about the Facade. It has no knowledge
 * of the manager, DAO, or observers. Completely decoupled from
 * internal implementation details.
 *
 * GRASP - High Cohesion:
 * Each method in this controller handles exactly one type of
 * incoming request. Clean, focused, and easy to maintain.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Only responsible for receiving requests, validating inputs,
 * and delegating to the facade. Nothing more.
 *
 * SOLID - Open/Closed Principle (OCP):
 * New request types can be added as new methods without
 * modifying existing ones.
 */
public class ProfileController {

    // ── Facade Reference ──────────────────────────────────────────────────────

    /**
     * The facade that handles all profile subsystem operations.
     * The controller delegates every request to this facade.
     */
    private final BenefitsProfileFacade profileFacade;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * Constructs the controller with the facade it will delegate to.
     *
     * @param profileFacade The BenefitsProfileFacade instance.
     */
    public ProfileController(BenefitsProfileFacade profileFacade) {
        this.profileFacade = profileFacade;
        System.out.println("[Controller] ProfileController initialized.");
    }

    // ── Request Handlers ──────────────────────────────────────────────────────

    /**
     * Handles an incoming request to create a new employee profile.
     * Validates that required fields are present before delegating.
     *
     * @param employeeId     Unique identifier for the employee.
     * @param name           Full name of the employee.
     * @param designation    Job title/designation.
     * @param department     Department name.
     * @param salaryBand     Salary band (e.g., "L1", "L2", "L3").
     * @param employmentType Type of employment (FULL_TIME, CONTRACT, INTERN).
     * @param dateOfJoining  Date the employee joined (YYYY-MM-DD).
     */
    public void handleCreateProfile(
            String employeeId,
            String name,
            String designation,
            String department,
            String salaryBand,
            EmployeeProfile.EmploymentType employmentType,
            String dateOfJoining) {

        System.out.println("\n[Controller] Handling → CREATE profile "
                + "request for ID: " + employeeId);

        // ── Input Validation ─────────────────────────────────────────────────
        // Guard against null or empty required fields before processing

        if (employeeId == null || employeeId.isBlank()) {
            System.out.println("[Controller] ERROR: Employee ID is required.");
            return;
        }
        if (name == null || name.isBlank()) {
            System.out.println("[Controller] ERROR: Name is required.");
            return;
        }
        if (employmentType == null) {
            System.out.println("[Controller] ERROR: Employment type "
                    + "is required.");
            return;
        }

        try {
            // Build the profile model
            EmployeeProfile profile = new EmployeeProfile(
                    employeeId, name, designation, department,
                    salaryBand, employmentType, dateOfJoining);

            // Delegate to facade
            profileFacade.createProfile(profile);

            System.out.println("[Controller] SUCCESS: Profile created "
                    + "for ID: " + employeeId);

        } catch (DuplicateProfileException e) {
            // Handle known business exception gracefully
            System.out.println("[Controller] FAILED: " + e.toString());
        }
    }

    /**
     * Handles an incoming request to retrieve an employee profile by ID.
     *
     * @param employeeId The unique employee ID to look up.
     * @return The matching EmployeeProfile, or null if not found.
     */
    public EmployeeProfile handleGetProfile(String employeeId) {

        System.out.println("\n[Controller] Handling → GET profile "
                + "request for ID: " + employeeId);

        // ── Input Validation ─────────────────────────────────────────────────
        if (employeeId == null || employeeId.isBlank()) {
            System.out.println("[Controller] ERROR: Employee ID is required.");
            return null;
        }

        try {
            // Delegate to facade
            EmployeeProfile profile = profileFacade.getProfile(employeeId);

            System.out.println("[Controller] SUCCESS: Profile retrieved "
                    + "for ID: " + employeeId);
            return profile;

        } catch (ProfileNotFoundException e) {
            System.out.println("[Controller] FAILED: " + e.toString());
            return null;
        }
    }

    /**
     * Handles an incoming request to update an existing employee profile.
     *
     * @param profile The updated EmployeeProfile to persist.
     */
    public void handleUpdateProfile(EmployeeProfile profile) {

        System.out.println("\n[Controller] Handling → UPDATE profile "
                + "request for ID: " + profile.getEmployeeId());

        // ── Input Validation ─────────────────────────────────────────────────
        if (profile == null) {
            System.out.println("[Controller] ERROR: Profile cannot be null.");
            return;
        }

        try {
            // Delegate to facade
            profileFacade.updateProfile(profile);

            System.out.println("[Controller] SUCCESS: Profile updated "
                    + "for ID: " + profile.getEmployeeId());

        } catch (ProfileNotFoundException e) {
            System.out.println("[Controller] FAILED: " + e.toString());
        }
    }

    /**
     * Handles an incoming request to delete an employee profile by ID.
     *
     * @param employeeId The unique employee ID whose profile to delete.
     */
    public void handleDeleteProfile(String employeeId) {

        System.out.println("\n[Controller] Handling → DELETE profile "
                + "request for ID: " + employeeId);

        // ── Input Validation ─────────────────────────────────────────────────
        if (employeeId == null || employeeId.isBlank()) {
            System.out.println("[Controller] ERROR: Employee ID is required.");
            return;
        }

        try {
            // Delegate to facade
            profileFacade.deleteProfile(employeeId);

            System.out.println("[Controller] SUCCESS: Profile deleted "
                    + "for ID: " + employeeId);

        } catch (ProfileNotFoundException e) {
            System.out.println("[Controller] FAILED: " + e.toString());
        }
    }

    /**
     * Handles an incoming request to retrieve all employee profiles.
     *
     * @return A collection of all EmployeeProfile objects, or null on error.
     */
    public Collection<EmployeeProfile> handleGetAllProfiles() {

        System.out.println("\n[Controller] Handling → GET ALL profiles "
                + "request");

        // Delegate to facade
        Collection<EmployeeProfile> profiles = profileFacade.getAllProfiles();

        System.out.println("[Controller] SUCCESS: Retrieved "
                + profiles.size() + " profile(s).");

        return profiles;
    }
}