package com.benefits.audit.dao;

import com.benefits.audit.model.AuditLog;
import java.util.Collection;

/**
 * DAO interface for AuditLog persistence.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * AuditService depends on this abstraction, not on any concrete DB class.
 *
 * SOLID - Interface Segregation Principle (ISP):
 * Only contains methods needed for audit log storage and retrieval.
 *
 * ─────────────────────────────────────────────────────────────────
 * NOTE TO DATABASE TEAM:
 * Implement this in AuditLogDAOImpl.java.
 * All methods must handle their own DB connection and error logging.
 * ─────────────────────────────────────────────────────────────────
 */
public interface AuditLogDAO {

    /** Persists a new audit log entry. */
    void save(AuditLog log);

    /** Retrieves an audit log by its unique ID. */
    AuditLog findById(String logId);

    /** Retrieves all audit logs. */
    Collection<AuditLog> findAll();

    /** Retrieves all audit logs for a specific employee. */
    Collection<AuditLog> findByEmployeeId(String employeeId);

    /** Retrieves all audit logs of a specific action type. */
    Collection<AuditLog> findByActionType(AuditLog.ActionType actionType);
}
