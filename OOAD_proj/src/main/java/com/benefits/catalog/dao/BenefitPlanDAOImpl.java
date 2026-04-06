package main.java.com.benefits.catalog.dao;

import main.java.com.benefits.catalog.model.BenefitPlan;
import java.util.Collection;

/**
 * Stub implementation of BenefitPlanDAO.
 *
 * ─────────────────────────────────────────────────────────────────
 * TO THE DATABASE TEAM:
 * This file is yours to implement. Replace each method body
 * with your actual database queries.
 * (JDBC, Hibernate, JPA, or any ORM of your choice)
 *
 * We have defined the full contract in BenefitPlanDAO.java.
 * You only need to fulfill it here.
 *
 * Key notes:
 * → findById() : return null if record not found
 * → findAll() : return empty collection if no records
 * → findByType() : return empty collection if no matches
 * → existsById() : return true/false only, no exceptions
 *
 * Do NOT change method signatures under any circumstance.
 * Our business logic layer depends on them exactly as written.
 * ─────────────────────────────────────────────────────────────────
 *
 * SOLID - Open/Closed Principle (OCP):
 * Our catalog layer is completely closed for modification.
 * The DB team fills this in without touching anything else.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * We depend on BenefitPlanDAO (abstraction), not this class.
 * This class is injected at runtime via the facade constructor.
 */
public class BenefitPlanDAOImpl implements BenefitPlanDAO {

    @Override
    public void save(BenefitPlan plan) {
        // TODO (DB Team): Insert benefit plan record into the database
        throw new UnsupportedOperationException(
                "[DAO] save() - Awaiting DB team implementation.");
    }

    @Override
    public BenefitPlan findById(String planId) {
        // TODO (DB Team): Query database for plan by planId
        // Return null if no record found — do not throw exception
        throw new UnsupportedOperationException(
                "[DAO] findById() - Awaiting DB team implementation.");
    }

    @Override
    public Collection<BenefitPlan> findAll() {
        // TODO (DB Team): Query database for all benefit plans
        // Return empty collection if no records exist
        throw new UnsupportedOperationException(
                "[DAO] findAll() - Awaiting DB team implementation.");
    }

    @Override
    public Collection<BenefitPlan> findByType(BenefitPlan.PlanType planType) {
        // TODO (DB Team): Query database for all plans matching planType
        // Return empty collection if no matching plans found
        throw new UnsupportedOperationException(
                "[DAO] findByType() - Awaiting DB team implementation.");
    }

    @Override
    public void update(BenefitPlan plan) {
        // TODO (DB Team): Update existing plan record in the database
        throw new UnsupportedOperationException(
                "[DAO] update() - Awaiting DB team implementation.");
    }

    @Override
    public void delete(String planId) {
        // TODO (DB Team): Delete plan record from the database by planId
        throw new UnsupportedOperationException(
                "[DAO] delete() - Awaiting DB team implementation.");
    }

    @Override
    public boolean existsById(String planId) {
        // TODO (DB Team): Check if a plan record exists for given planId
        // Return true if found, false if not — no exceptions
        throw new UnsupportedOperationException(
                "[DAO] existsById() - Awaiting DB team implementation.");
    }
}
