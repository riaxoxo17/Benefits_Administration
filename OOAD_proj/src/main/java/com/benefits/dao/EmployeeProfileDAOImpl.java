package main.java.com.benefits.dao;

import main.java.com.benefits.model.EmployeeProfile;
import java.util.Collection;

/**
 * Stub implementation of EmployeeProfileDAO.
 *
 * ─────────────────────────────────────────────────────────────────
 * TO THE DATABASE TEAM:
 * This file is yours to implement. Replace each method body with
 * your actual database queries (JDBC, Hibernate, JPA, etc.).
 *
 * We have defined the contract in EmployeeProfileDAO.java.
 * You just need to fulfill it here.
 *
 * Do NOT change method signatures — our code depends on them.
 * ─────────────────────────────────────────────────────────────────
 *
 * SOLID - Open/Closed Principle (OCP):
 * Our business logic is closed for modification but open for
 * extension — the DB team can swap implementations freely.
 */
public class EmployeeProfileDAOImpl implements EmployeeProfileDAO {

    @Override
    public void save(EmployeeProfile profile) {
        // TODO (DB Team): Insert profile record into the database
        throw new UnsupportedOperationException(
                "[DAO] save() - Awaiting DB team implementation.");
    }

    @Override
    public EmployeeProfile findById(String employeeId) {
        // TODO (DB Team): Query database for profile by employeeId
        throw new UnsupportedOperationException(
                "[DAO] findById() - Awaiting DB team implementation.");
    }

    @Override
    public Collection<EmployeeProfile> findAll() {
        // TODO (DB Team): Query database for all employee profiles
        throw new UnsupportedOperationException(
                "[DAO] findAll() - Awaiting DB team implementation.");
    }

    @Override
    public void update(EmployeeProfile profile) {
        // TODO (DB Team): Update existing profile record in the database
        throw new UnsupportedOperationException(
                "[DAO] update() - Awaiting DB team implementation.");
    }

    @Override
    public void delete(String employeeId) {
        // TODO (DB Team): Delete profile record from the database by ID
        throw new UnsupportedOperationException(
                "[DAO] delete() - Awaiting DB team implementation.");
    }

    @Override
    public boolean existsById(String employeeId) {
        // TODO (DB Team): Check if a profile record exists for given ID
        throw new UnsupportedOperationException(
                "[DAO] existsById() - Awaiting DB team implementation.");
    }
}