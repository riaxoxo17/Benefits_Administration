package com.benefits.notification.controller;

import com.benefits.notification.exception.NotificationDeliveryFailedException;
import com.benefits.notification.model.Notification;
import com.benefits.notification.service.NotificationService;

import java.util.Collection;

/**
 * Controller for the Notification & Communication Module.
 *
 * GRASP - Controller: Designated handler for all incoming notification requests.
 *                     Delegates entirely to NotificationService. No logic here.
 * GRASP - Low Coupling: Only depends on NotificationService interface.
 * SOLID - SRP: Only responsible for receiving requests and routing.
 * SOLID - DIP: Depends on NotificationService abstraction, not the impl.
 */
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
        System.out.println("[NotificationController] Initialized and ready.");
    }

    // ── Request Handlers ──────────────────────────────────────────────────────

    /**
     * Handles a request to send a notification.
     */
    public void handleSendNotification(String employeeId,
                                       Notification.NotificationType type,
                                       String message,
                                       Notification.Channel channel) {
        System.out.println("\n[NotificationController] → sendNotification() for: " + employeeId);
        try {
            notificationService.sendNotification(employeeId, type, message, channel);
            System.out.println("[NotificationController] SUCCESS: Notification sent.");
        } catch (NotificationDeliveryFailedException e) {
            System.out.println("[NotificationController] FAILED: " + e.getMessage());
        }
    }

    /**
     * Handles a retry request for a previously failed notification.
     */
    public void handleRetryDelivery(String notificationId) {
        System.out.println("\n[NotificationController] → retryDelivery() for: " + notificationId);
        try {
            notificationService.retryDelivery(notificationId);
            System.out.println("[NotificationController] SUCCESS: Retry delivered.");
        } catch (NotificationDeliveryFailedException e) {
            System.out.println("[NotificationController] Retry FAILED: " + e.getMessage());
        }
    }

    /**
     * Handles a request to get all notifications for an employee.
     */
    public Collection<Notification> handleGetNotificationsForEmployee(String employeeId) {
        System.out.println("\n[NotificationController] → getNotifications() for: " + employeeId);
        Collection<Notification> notifications =
                notificationService.getNotificationsForEmployee(employeeId);
        System.out.println("[NotificationController] Found: "
                + notifications.size() + " notification(s).");
        for (Notification n : notifications) {
            System.out.println("  → " + n.getNotificationId()
                    + " | " + n.getNotificationType()
                    + " | " + n.getDeliveryStatus()
                    + " | " + n.getChannel());
        }
        return notifications;
    }

    /**
     * Handles a request to get all notifications by delivery status.
     */
    public Collection<Notification> handleGetByStatus(Notification.DeliveryStatus status) {
        System.out.println("\n[NotificationController] → getByStatus(" + status + ")");
        Collection<Notification> notifications =
                notificationService.getNotificationsByStatus(status);
        System.out.println("[NotificationController] Found: "
                + notifications.size() + " notification(s) with status: " + status);
        return notifications;
    }
}
