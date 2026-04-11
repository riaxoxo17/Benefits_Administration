package com.benefits.notification.service;

import com.benefits.notification.model.Notification;
import java.util.Collection;

/**
 * Contract for the Notification & Communication Module.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * All modules depend on this interface — never on the concrete impl.
 * This is the second key interface owned by for cross-cutting integration.
 *
 * GRASP - Pure Fabrication:
 * Notification has no domain meaning — it's pure infrastructure.
 *
 * GRASP - Indirection:
 * Source modules (enrollment, claims, approval) never directly send
 * notifications — they fire events that the NotificationService handles.
 * This decouples the action from the communication layer completely.
 *
 * Integration Contract: Defined by, consumed by EVERYONE.
 */
public interface NotificationService {

    /**
     * Sends a notification via the specified channel.
     * Implements retry logic internally on delivery failure.
     *
     * @param employeeId     The recipient employee ID.
     * @param type           The notification type.
     * @param messageContent The message body.
     * @param channel        The delivery channel (EMAIL, SMS, DASHBOARD).
     */
    void sendNotification(String employeeId,
                          Notification.NotificationType type,
                          String messageContent,
                          Notification.Channel channel);

    /**
     * Retries delivery of a previously failed notification.
     * Attempts alternate channels if primary fails again.
     *
     * @param notificationId The ID of the failed notification to retry.
     */
    void retryDelivery(String notificationId);

    /**
     * Returns all notifications sent to a specific employee.
     *
     * @param employeeId The employee's ID.
     * @return Collection of Notification objects.
     */
    Collection<Notification> getNotificationsForEmployee(String employeeId);

    /**
     * Returns all notifications with a given delivery status.
     *
     * @param status The delivery status to filter by.
     * @return Collection of matching Notification objects.
     */
    Collection<Notification> getNotificationsByStatus(Notification.DeliveryStatus status);
}
