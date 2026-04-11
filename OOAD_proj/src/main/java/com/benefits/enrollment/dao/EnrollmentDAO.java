package main.java.com.benefits.enrollment.dao;

import main.java.com.benefits.enrollment.model.Enrollment;
import java.util.Collection;

/**
 * DAO (Data Access Object) interface for Enrollment.
 *
 * This is the contract between Prerana's enrollment business logic
 * and the database layer. The DB team implements this interface.
 * Our enrollment service depends only on this abstraction.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * EnrollmentServiceImpl depends on this interface, never on a
 * concrete database implementation. The DB team provides the impl.
 *
 * SOLID - Interface Segregation Principle (ISP):
 * Only contains methods directly relevant to enrollment data access.
 * Nothing more, nothing less.
 *
 * GRASP - Low Coupling:
 * The enrollment service layer is completely decoupled from the
 * underlying database technology.
 *
 * ─────────────────────────────────────────────────────────────────
 * NOTE TO DATABASE TEAM:
 * Please implement this interface in EnrollmentDAOImpl.java
 * All methods must handle their own DB connection and error logging.
 * Return null from findById() if no record is found.
 * Return empty collection from findByEmployeeId() if none found.
 * ─────────────────────────────────────────────────────────────────
 */
public interface EnrollmentDAO {

    /**
     * Saves a new enrollment record to the database.
     *
     * @param enrollment The Enrollment to persist.
     */
    void save(Enrollment enrollment);

    /**
     * Retrieves an enrollment by its unique enrollment ID.
     *
     * @param enrollmentId The unique enrollment identifier.
     * @return The matching Enrollment, or null if not found.
     */
    Enrollment findById(String enrollmentId);

    /**
     * Retrieves all enrollments for a specific employee.
     *
     * @param employeeId The employee whose enrollments to retrieve.
     * @return Collection of Enrollment objects for that employee.
     *         Returns empty collection if none found.
     */
    Collection<Enrollment> findByEmployeeId(String employeeId);

    /**
     * Retrieves all enrollments in the system.
     *
     * @return Collection of all Enrollment records.
     */
    Collection<Enrollment> findAll();

    /**
     * Updates an existing enrollment record (e.g. status change).
     *
     * @param enrollment The updated Enrollment to persist.
     */
    void update(Enrollment enrollment);

    /**
     * Checks whether an enrollment already exists for a given
     * employee + plan combination. Used to prevent duplicates.
     *
     * @param employeeId The employee ID.
     * @param planId     The plan ID.
     * @return true if an enrollment already exists, false otherwise.
     */
    boolean existsByEmployeeAndPlan(String employeeId, String planId);
}
