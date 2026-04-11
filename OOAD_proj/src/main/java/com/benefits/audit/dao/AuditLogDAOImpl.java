package com.benefits.audit.dao;

import com.benefits.audit.model.AuditLog;
import java.util.*;
import java.util.stream.Collectors;

/**
 * In-memory implementation of AuditLogDAO.
 * Replace with actual DB implementation when integrating with database team.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Only responsible for in-memory storage and retrieval of audit logs.
 */
public class AuditLogDAOImpl implements AuditLogDAO {

    private final Map<String, AuditLog> store = new LinkedHashMap<>();

    @Override
    public void save(AuditLog log) {
        store.put(log.getLogId(), log);
        System.out.println("[AuditLogDAO] Saved audit log: " + log.getLogId());
    }

    @Override
    public AuditLog findById(String logId) {
        return store.get(logId);
    }

    @Override
    public Collection<AuditLog> findAll() {
        return Collections.unmodifiableCollection(store.values());
    }

    @Override
    public Collection<AuditLog> findByEmployeeId(String employeeId) {
        return store.values().stream()
                .filter(l -> employeeId.equals(l.getEmployeeId()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<AuditLog> findByActionType(AuditLog.ActionType actionType) {
        return store.values().stream()
                .filter(l -> l.getActionType() == actionType)
                .collect(Collectors.toList());
    }
}
