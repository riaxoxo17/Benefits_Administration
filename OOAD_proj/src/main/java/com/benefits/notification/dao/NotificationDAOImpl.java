package com.benefits.notification.dao;

import com.benefits.notification.model.Notification;
import java.util.*;
import java.util.stream.Collectors;

/**
 * In-memory implementation of NotificationDAO.
 * Replace with DB implementation when integrating with database team.
 *
 * SOLID - SRP: Only responsible for in-memory storage of notifications.
 */
public class NotificationDAOImpl implements NotificationDAO {

    private final Map<String, Notification> store = new LinkedHashMap<>();

    @Override
    public void save(Notification notification) {
        store.put(notification.getNotificationId(), notification);
        System.out.println("[NotificationDAO] Saved notification: "
                + notification.getNotificationId());
    }

    @Override
    public Notification findById(String notificationId) {
        return store.get(notificationId);
    }

    @Override
    public Collection<Notification> findAll() {
        return Collections.unmodifiableCollection(store.values());
    }

    @Override
    public Collection<Notification> findByEmployeeId(String employeeId) {
        return store.values().stream()
                .filter(n -> employeeId.equals(n.getEmployeeId()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Notification> findByStatus(Notification.DeliveryStatus status) {
        return store.values().stream()
                .filter(n -> n.getDeliveryStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Notification notification) {
        store.put(notification.getNotificationId(), notification);
        System.out.println("[NotificationDAO] Updated notification: "
                + notification.getNotificationId());
    }
}
