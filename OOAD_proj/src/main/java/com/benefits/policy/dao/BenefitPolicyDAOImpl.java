package com.benefits.policy.dao;

import com.benefits.policy.model.BenefitPolicy;
import java.util.Collection;

/**
 * Stub implementation of BenefitPolicyDAO.
 *
 * ─────────────────────────────────────────────────────────────────
 * TO THE DATABASE TEAM:
 * This file is yours to implement. Replace each method body
 * with your actual database queries.
 * (JDBC, Hibernate, JPA, or any ORM of your choice)
 *
 * We have defined the full contract in BenefitPolicyDAO.java.
 * You only need to fulfill it here.
 *
 * Key notes:
 *   → findById()     : return null if record not found
 *   → findAll()      : return empty collection if no records
 *   → findAllActive(): return only policies where active = true
 *   → existsById()   : return true/false only, no exceptions
 *
 * Do NOT change method signatures under any circumstance.
 * Our business logic layer depends on them exactly as written.
 * ─────────────────────────────────────────────────────────────────
 *
 * SOLID - Open/Closed Principle (OCP):
 * Our policy facade is completely closed for modification.
 * The DB team fills this in without touching anything else.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * We depend on BenefitPolicyDAO (abstraction), not this class.
 * This class is injected at runtime via the facade constructor.
 */
public class BenefitPolicyDAOImpl implements BenefitPolicyDAO {

    @Override
    public void save(BenefitPolicy policy) {
        // TODO (DB Team): Insert benefit policy record into the database
        throw new UnsupportedOperationException(
                "[DAO] save() - Awaiting DB team implementation.");
    }

    @Override
    public BenefitPolicy findById(String policyId) {
        // TODO (DB Team): Query database for policy by policyId
        // Return null if no record found — do not throw exception
        throw new UnsupportedOperationException(
                "[DAO] findById() - Awaiting DB team implementation.");
    }

    @Override
    public Collection<BenefitPolicy> findAll() {
        // TODO (DB Team): Query database for all benefit policies
        // Return empty collection if no records exist
        throw new UnsupportedOperationException(
                "[DAO] findAll() - Awaiting DB team implementation.");
    }

    @Override
    public Collection<BenefitPolicy> findAllActive() {
        // TODO (DB Team): Query database for all policies where active = true
        // Return empty collection if none are active
        throw new UnsupportedOperationException(
                "[DAO] findAllActive() - Awaiting DB team implementation.");
    }

    @Override
    public void update(BenefitPolicy policy) {
        // TODO (DB Team): Update existing policy record in the database
        throw new UnsupportedOperationException(
                "[DAO] update() - Awaiting DB team implementation.");
    }

    @Override
    public void delete(String policyId) {
        // TODO (DB Team): Delete policy record from the database by policyId
        throw new UnsupportedOperationException(
                "[DAO] delete() - Awaiting DB team implementation.");
    }

    @Override
    public boolean existsById(String policyId) {
        // TODO (DB Team): Check if a policy record exists for given policyId
        // Return true if found, false if not — no exceptions
        throw new UnsupportedOperationException(
                "[DAO] existsById() - Awaiting DB team implementation.");
    }
}
