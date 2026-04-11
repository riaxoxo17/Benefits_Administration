package com.benefits.policy.dao;

import com.benefits.policy.model.BenefitPolicy;
import java.util.Collection;

/**
 * DAO (Data Access Object) interface for BenefitPolicy.
 *
 * STRUCTURAL PATTERN - This interface is the contract between
 * our business logic layer and the database layer for all
 * benefit policy data operations.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * The facade depends on this abstraction, not on any concrete
 * database implementation. The DB team provides the implementation.
 *
 * SOLID - Interface Segregation Principle (ISP):
 * Only contains methods directly relevant to policy data access.
 * Clean, focused, and nothing unnecessary.
 *
 * GRASP - Low Coupling:
 * Our entire policy layer is decoupled from the database
 * technology used by the DB team.
 *
 * ─────────────────────────────────────────────────────────────────
 * NOTE TO DATABASE TEAM:
 * Please implement this interface in BenefitPolicyDAOImpl.java
 * All methods must handle their own DB connection management.
 * Return null from findById() if no record is found.
 * Return empty collection from findAll() if no records exist.
 * Return empty collection from findActive() if none are active.
 * Do NOT change method signatures — our code depends on them.
 * ─────────────────────────────────────────────────────────────────
 */
public interface BenefitPolicyDAO {

    /**
     * Saves a new benefit policy to the database.
     *
     * @param policy The BenefitPolicy to persist.
     */
    void save(BenefitPolicy policy);

    /**
     * Retrieves a benefit policy by its unique policy ID.
     *
     * @param policyId The unique policy identifier.
     * @return The matching BenefitPolicy, or null if not found.
     */
    BenefitPolicy findById(String policyId);

    /**
     * Retrieves all benefit policies from the database.
     *
     * @return A collection of all BenefitPolicy objects.
     *         Returns empty collection if none exist.
     */
    Collection<BenefitPolicy> findAll();

    /**
     * Retrieves all currently active benefit policies.
     * Active policies are those with active = true.
     *
     * @return A collection of all active BenefitPolicy objects.
     *         Returns empty collection if none are active.
     */
    Collection<BenefitPolicy> findAllActive();

    /**
     * Updates an existing benefit policy in the database.
     *
     * @param policy The updated BenefitPolicy to persist.
     */
    void update(BenefitPolicy policy);

    /**
     * Deletes a benefit policy from the database by ID.
     *
     * @param policyId The unique policy identifier to delete.
     */
    void delete(String policyId);

    /**
     * Checks whether a policy exists for the given policy ID.
     *
     * @param policyId The unique policy identifier to check.
     * @return true if a policy exists, false otherwise.
     */
    boolean existsById(String policyId);
}
