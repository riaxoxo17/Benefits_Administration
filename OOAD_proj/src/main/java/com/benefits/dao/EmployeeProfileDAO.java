package main.java.com.benefits.dao;

import main.java.com.benefits.model.EmployeeProfile;
import java.util.Collection;

/**
 * DAO (Data Access Object) interface for EmployeeProfile.
 *
 * STRUCTURAL PATTERN - This interface acts as the contract between
 * our business logic layer and the database layer.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * The manager depends on this abstraction, not on any concrete DB
 * implementation. The DB team implements this, we consume it.
 *
 * SOLID - Interface Segregation Principle (ISP):
 * Only contains methods directly relevant to employee profile
 * data access. Nothing more, nothing less.
 *
 * GRASP - Low Coupling:
 * By depending on this interface, our entire business layer is
 * completely decoupled from the database technology used.
 *
 * ─────────────────────────────────────────────────────────────────
 * NOTE TO DATABASE TEAM:
 * Please implement this interface in EmployeeProfileDAOImpl.java
 * All methods must handle their own DB connection and error logging.
 * Return null from findById() if no record is found.
 * ─────────────────────────────────────────────────────────────────
 */
public interface EmployeeProfileDAO {

    /**
     * Saves a new employee profile to the database.
     *
     * @param profile The EmployeeProfile to persist.
     */
    void save(EmployeeProfile profile);

    /**
     * Retrieves an employee profile by their unique ID.
     *
     * @param employeeId The unique employee identifier.
     * @return The matching EmployeeProfile, or null if not found.
     */
    EmployeeProfile findById(String employeeId);

    /**
     * Retrieves all employee profiles from the database.
     *
     * @return A collection of all stored EmployeeProfile objects.
     */
    Collection<EmployeeProfile> findAll();

    /**
     * Updates an existing employee profile in the database.
     *
     * @param profile The updated EmployeeProfile to persist.
     */
    void update(EmployeeProfile profile);

    /**
     * Deletes an employee profile from the database by ID.
     *
     * @param employeeId The unique employee identifier to delete.
     */
    void delete(String employeeId);

    /**
     * Checks whether a profile exists for the given employee ID.
     *
     * @param employeeId The unique employee identifier to check.
     * @return true if a profile exists, false otherwise.
     */
    boolean existsById(String employeeId);
}