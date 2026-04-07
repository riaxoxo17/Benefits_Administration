package main.java.com.benefits.dao;

import main.java.com.benefits.model.EmployeeProfile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * In-Memory DAO Implementation (Mock DB)
 *
 * PURPOSE:
 * This class acts as a temporary/mock database using in-memory storage.
 * It replaces actual database interactions so that the system can run
 * without requiring a real DB connection.
 *
 * DESIGN PATTERN:
 * - DAO (Data Access Object) Pattern → Separates persistence logic from business logic
 *
 * GRASP PRINCIPLE:
 * - Information Expert → This class is responsible for managing EmployeeProfile data
 *
 * SOLID PRINCIPLES:
 * - SRP (Single Responsibility Principle):
 *   This class is only responsible for data access operations.
 *
 * - DIP (Dependency Inversion Principle):
 *   Higher-level modules depend on the EmployeeProfileDAO interface,
 *   not this concrete implementation.
 *
 * NOTE:
 * This implementation is mainly for testing/development purposes and
 * can later be replaced with a real database-backed DAO.
 */
public class EmployeeProfileDAOImpl implements EmployeeProfileDAO {

    /**
     * Simulated database using HashMap
     *
     * KEY   → employeeId (String)
     * VALUE → EmployeeProfile object
     *
     * WHY HashMap?
     * - Fast lookup (O(1))
     * - Simple in-memory representation of a table
     */
    private final Map<String, EmployeeProfile> profileStore = new HashMap<>();

    /**
     * Saves a new EmployeeProfile into the mock database.
     *
     * FUNCTIONALITY:
     * - Inserts a new profile OR overwrites if ID already exists
     *
     * EXCEPTION HANDLING NOTE:
     * - No validation is done here (assumed to be handled in service layer)
     */
    @Override
    public void save(EmployeeProfile profile) {
        profileStore.put(profile.getEmployeeId(), profile);

        // Logging to simulate DB operation visibility
        System.out.println("[DAO MOCK] Saved profile: " + profile.getEmployeeId());
    }

    /**
     * Fetches an EmployeeProfile by employeeId.
     *
     * RETURNS:
     * - EmployeeProfile if found
     * - null if not found
     *
     * DESIGN NOTE:
     * - Caller must handle null (avoids forcing exception at DAO layer)
     */
    @Override
    public EmployeeProfile findById(String employeeId) {
        System.out.println("[DAO MOCK] Fetching profile: " + employeeId);

        return profileStore.get(employeeId);
    }

    /**
     * Retrieves all EmployeeProfiles.
     *
     * RETURNS:
     * - Collection of all stored profiles
     *
     * STRUCTURAL NOTE:
     * - Returning Collection instead of specific List → promotes flexibility
     */
    @Override
    public Collection<EmployeeProfile> findAll() {
        System.out.println("[DAO MOCK] Fetching all profiles");

        return profileStore.values();
    }

    /**
     * Updates an existing EmployeeProfile.
     *
     * FUNCTIONALITY:
     * - Overwrites the existing profile with the same employeeId
     *
     * ASSUMPTION:
     * - Profile exists (validation should be done before calling this method)
     */
    @Override
    public void update(EmployeeProfile profile) {
        profileStore.put(profile.getEmployeeId(), profile);

        System.out.println("[DAO MOCK] Updated profile: " + profile.getEmployeeId());
    }

    /**
     * Deletes an EmployeeProfile using employeeId.
     *
     * BEHAVIOR:
     * - If ID does not exist → no action (safe delete)
     */
    @Override
    public void delete(String employeeId) {
        profileStore.remove(employeeId);

        System.out.println("[DAO MOCK] Deleted profile: " + employeeId);
    }

    /**
     * Checks whether an EmployeeProfile exists.
     *
     * RETURNS:
     * - true  → if employee exists
     * - false → otherwise
     *
     * USE CASE:
     * - Validation before update/delete operations
     */
    @Override
    public boolean existsById(String employeeId) {
        return profileStore.containsKey(employeeId);
    }
}