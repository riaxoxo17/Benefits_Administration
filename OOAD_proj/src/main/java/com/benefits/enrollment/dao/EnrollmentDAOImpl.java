package main.java.com.benefits.enrollment.dao;

import main.java.com.benefits.enrollment.model.Enrollment;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * In-memory implementation of EnrollmentDAO.
 *
 * Uses a HashMap to store enrollments during the program's runtime.
 * This allows the system to run and be demonstrated without a real
 * database connection, consistent with how Poorav structured
 * EmployeeProfileDAOImpl and BenefitPlanDAOImpl.
 *
 * ─────────────────────────────────────────────────────────────────
 * NOTE TO DATABASE TEAM:
 * When the real DB is ready, replace this implementation with a
 * JDBC/JPA version. The EnrollmentDAO interface contract does not
 * change — just the implementation body.
 *
 * Do NOT change method signatures — our service depends on them.
 * ─────────────────────────────────────────────────────────────────
 *
 * SOLID - Open/Closed Principle (OCP):
 * EnrollmentServiceImpl is closed for modification — it depends on
 * EnrollmentDAO. The DB team can swap this impl freely.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This class is solely responsible for in-memory data storage
 * and retrieval of Enrollment objects.
 */
public class EnrollmentDAOImpl implements EnrollmentDAO {

    // ── In-memory store ──────────────────────────────────────────────────────
    // Key: enrollmentId → Value: Enrollment
    private final Map<String, Enrollment> store = new HashMap<>();

    // ── Save ─────────────────────────────────────────────────────────────────

    @Override
    public void save(Enrollment enrollment) {
        store.put(enrollment.getEnrollmentId(), enrollment);
        System.out.println("[EnrollmentDAO] Saved enrollment: "
                + enrollment.getEnrollmentId());
    }

    // ── Find by ID ───────────────────────────────────────────────────────────

    @Override
    public Enrollment findById(String enrollmentId) {
        return store.get(enrollmentId); // returns null if not found
    }

    // ── Find by Employee ─────────────────────────────────────────────────────

    @Override
    public Collection<Enrollment> findByEmployeeId(String employeeId) {
        return store.values().stream()
                .filter(e -> e.getEmployeeId().equals(employeeId))
                .collect(Collectors.toList());
    }

    // ── Find All ─────────────────────────────────────────────────────────────

    @Override
    public Collection<Enrollment> findAll() {
        return Collections.unmodifiableCollection(store.values());
    }

    // ── Update ───────────────────────────────────────────────────────────────

    @Override
    public void update(Enrollment enrollment) {
        if (store.containsKey(enrollment.getEnrollmentId())) {
            store.put(enrollment.getEnrollmentId(), enrollment);
            System.out.println("[EnrollmentDAO] Updated enrollment: "
                    + enrollment.getEnrollmentId());
        }
    }

    // ── Exists by Employee + Plan ─────────────────────────────────────────────

    @Override
    public boolean existsByEmployeeAndPlan(String employeeId, String planId) {
        return store.values().stream()
                .anyMatch(e -> e.getEmployeeId().equals(employeeId)
                        && e.getPlanId().equals(planId));
    }
}
