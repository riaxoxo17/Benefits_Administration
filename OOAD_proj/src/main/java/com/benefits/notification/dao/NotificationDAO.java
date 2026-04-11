package com.benefits.notification.dao;

import com.benefits.notification.model.Notification;
import java.util.Collection;

/**
 * DAO interface for Notification persistence.
 *
 * SOLID - DIP: NotificationService depends on this abstraction.
 * SOLID - ISP: Only contains methods needed for notification storage.
 *
 * ─────────────────────────────────────────────────────────────────
 * NOTE TO DATABASE TEAM:
 * Implement in NotificationDAOImpl.java.
 * ─────────────────────────────────────────────────────────────────
 */
public interface NotificationDAO {

    void save(Notification notification);

    Notification findById(String notificationId);

    Collection<Notification> findAll();

    Collection<Notification> findByEmployeeId(String employeeId);

    Collection<Notification> findByStatus(Notification.DeliveryStatus status);

    void update(Notification notification);
}
