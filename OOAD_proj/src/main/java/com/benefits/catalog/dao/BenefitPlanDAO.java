package com.benefits.catalog.dao;

import com.benefits.catalog.model.BenefitPlan;
import java.util.Collection;

/**
 * DAO (Data Access Object) interface for BenefitPlan.
 *
 * STRUCTURAL PATTERN - This interface is the contract between
 * our business logic layer and the database layer for all
 * benefit plan related data operations.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * The catalog manager and facade depend on this abstraction,
 * not on any concrete database implementation. The DB team
 * provides the implementation — we consume the interface.
 *
 * SOLID - Interface Segregation Principle (ISP):
 * Only contains methods directly relevant to benefit plan
 * data access. Clean, focused, nothing unnecessary.
 *
 * SOLID - Open/Closed Principle (OCP):
 * New query methods can be added to this interface without
 * modifying existing implementations that don't need them
 * — simply extend with a new interface if needed.
 *
 * GRASP - Low Coupling:
 * Our entire catalog layer is decoupled from the database
 * technology. Whether the DB team uses MySQL, PostgreSQL,
 * MongoDB or anything else — we don't care or know.
 *
 * ─────────────────────────────────────────────────────────────────
 * NOTE TO DATABASE TEAM:
 * Please implement this interface in BenefitPlanDAOImpl.java
 * All methods must handle their own DB connection management.
 * Return null from findById() if no record is found.
 * Return empty collection from findAll() if no records exist.
 * Return empty collection from findByType() if none match.
 * Do NOT change method signatures — our code depends on them.
 * ─────────────────────────────────────────────────────────────────
 */
public interface BenefitPlanDAO {

    /**
     * Saves a new benefit plan to the database.
     *
     * @param plan The BenefitPlan to persist.
     */
    void save(BenefitPlan plan);

    /**
     * Retrieves a benefit plan by its unique plan ID.
     *
     * @param planId The unique plan identifier.
     * @return The matching BenefitPlan, or null if not found.
     */
    BenefitPlan findById(String planId);

    /**
     * Retrieves all benefit plans from the database.
     *
     * @return A collection of all stored BenefitPlan objects.
     *         Returns empty collection if none exist.
     */
    Collection<BenefitPlan> findAll();

    /**
     * Retrieves all benefit plans of a specific type.
     * For example, all HEALTH plans or all RETIREMENT plans.
     *
     * @param planType The type of plans to retrieve.
     * @return A collection of matching BenefitPlan objects.
     *         Returns empty collection if none match.
     */
    Collection<BenefitPlan> findByType(BenefitPlan.PlanType planType);

    /**
     * Updates an existing benefit plan in the database.
     *
     * @param plan The updated BenefitPlan to persist.
     */
    void update(BenefitPlan plan);

    /**
     * Deletes a benefit plan from the database by its ID.
     *
     * @param planId The unique plan identifier to delete.
     */
    void delete(String planId);

    /**
     * Checks whether a plan exists for the given plan ID.
     *
     * @param planId The unique plan identifier to check.
     * @return true if a plan exists, false otherwise.
     */
    boolean existsById(String planId);
}
