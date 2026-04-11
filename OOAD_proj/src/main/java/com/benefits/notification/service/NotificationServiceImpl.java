package com.benefits.notification.service;

import com.benefits.notification.dao.NotificationDAO;
import com.benefits.notification.exception.NotificationDeliveryFailedException;
import com.benefits.notification.model.Notification;

import java.util.Collection;
import java.util.UUID;

/**
 * Concrete implementation of NotificationService.
 *
 * GRASP - Pure Fabrication: Pure infrastructure — no domain entity.
 *
 * SOLID - SRP: Only responsible for sending and tracking notifications.
 *
 * SOLID - OCP: New channel types (WhatsApp, Push) can be added
 * by extending the send logic — no changes to existing channels needed.
 *
 * SOLID - DIP: Depends on NotificationDAO abstraction (injected).
 *
 * Retry Logic: Up to MAX_RETRY_ATTEMPTS before marking as FAILED
 * and switching to fallback channel.
 */
public class NotificationServiceImpl implements NotificationService {

    // ── Constants ─────────────────────────────────────────────────────────────
    private static final int MAX_RETRY_ATTEMPTS = 3;

    // ── DAO Dependency ────────────────────────────────────────────────────────
    private final NotificationDAO notificationDAO;

    // ── Constructor ───────────────────────────────────────────────────────────
    public NotificationServiceImpl(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
        System.out.println("[NotificationService] Initialized and ready.");
    }

    // ── Core Operations ───────────────────────────────────────────────────────

    /**
     * Sends a notification via the requested channel.
     * On failure, logs the error and marks as FAILED.
     * Throws NotificationDeliveryFailedException for caller awareness.
     */
    @Override
    public void sendNotification(String employeeId,
                                 Notification.NotificationType type,
                                 String messageContent,
                                 Notification.Channel channel) {
        String id = "NOTIF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Notification notification = new Notification(id, employeeId, type, messageContent, channel);

        System.out.println("[NotificationService] Sending [" + channel + "] notification to: "
                + employeeId + " | Type: " + type);

        try {
            // Simulate delivery (in real system: call email/SMS gateway here)
            deliver(notification);
            notification.setDeliveryStatus(Notification.DeliveryStatus.DELIVERED);
            notificationDAO.save(notification);
            System.out.println("[NotificationService] DELIVERED: " + id);
        } catch (Exception e) {
            notification.setDeliveryStatus(Notification.DeliveryStatus.FAILED);
            notificationDAO.save(notification);
            System.out.println("[NotificationService] FAILED: " + id + " | " + e.getMessage());
            throw new NotificationDeliveryFailedException(id, channel.name(), e);
        }
    }

    /**
     * Retries delivery of a failed notification.
     * Attempts up to MAX_RETRY_ATTEMPTS times, then switches to DASHBOARD fallback.
     */
    @Override
    public void retryDelivery(String notificationId) {
        Notification notification = notificationDAO.findById(notificationId);

        if (notification == null) {
            System.out.println("[NotificationService] Retry FAILED: Notification not found: "
                    + notificationId);
            return;
        }

        System.out.println("[NotificationService] Retrying delivery for: " + notificationId
                + " (Attempt " + (notification.getRetryCount() + 1) + ")");

        notification.setDeliveryStatus(Notification.DeliveryStatus.RETRYING);
        notification.incrementRetryCount();

        try {
            if (notification.getRetryCount() > MAX_RETRY_ATTEMPTS) {
                // Fallback: switch to DASHBOARD channel if all retries exhausted
                System.out.println("[NotificationService] Max retries reached. "
                        + "Switching to DASHBOARD fallback.");
                notification.setChannel(Notification.Channel.DASHBOARD);
            }

            deliver(notification);
            notification.setDeliveryStatus(Notification.DeliveryStatus.DELIVERED);
            notificationDAO.update(notification);
            System.out.println("[NotificationService] Retry SUCCESS: " + notificationId);

        } catch (Exception e) {
            notification.setDeliveryStatus(Notification.DeliveryStatus.FAILED);
            notificationDAO.update(notification);
            System.out.println("[NotificationService] Retry FAILED again: "
                    + notificationId + " | " + e.getMessage());
            throw new NotificationDeliveryFailedException(notificationId,
                    notification.getChannel().name(), e);
        }
    }

    /**
     * Returns all notifications for a specific employee.
     */
    @Override
    public Collection<Notification> getNotificationsForEmployee(String employeeId) {
        Collection<Notification> notifications = notificationDAO.findByEmployeeId(employeeId);
        System.out.println("[NotificationService] Found " + notifications.size()
                + " notifications for Employee: " + employeeId);
        return notifications;
    }

    /**
     * Returns all notifications with a specific delivery status.
     */
    @Override
    public Collection<Notification> getNotificationsByStatus(Notification.DeliveryStatus status) {
        Collection<Notification> notifications = notificationDAO.findByStatus(status);
        System.out.println("[NotificationService] Found " + notifications.size()
                + " notifications with status: " + status);
        return notifications;
    }

    // ── Private Helper ────────────────────────────────────────────────────────

    /**
     * Simulates actual channel delivery.
     * In production: integrate with SMTP, SMS gateway, or push service.
     *
     * SOLID - OCP: Add new channels here without touching the rest.
     */
    private void deliver(Notification notification) {
        switch (notification.getChannel()) {
            case EMAIL:
                System.out.println("[NotificationService] [EMAIL] → "
                        + notification.getEmployeeId() + ": " + notification.getMessageContent());
                break;
            case SMS:
                System.out.println("[NotificationService] [SMS] → "
                        + notification.getEmployeeId() + ": " + notification.getMessageContent());
                break;
            case DASHBOARD:
                System.out.println("[NotificationService] [DASHBOARD] → "
                        + notification.getEmployeeId() + ": " + notification.getMessageContent());
                break;
            default:
                throw new IllegalArgumentException("Unknown channel: " + notification.getChannel());
        }
    }
}
