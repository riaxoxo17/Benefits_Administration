package com.benefits.notification.model;

import java.time.LocalDateTime;

/**
 * Represents a notification sent to an employee or manager.
 *
 * GRASP - Information Expert: Owns all notification data.
 * SOLID - SRP: Only holds notification data.
 *
 * Data Entity: Notifications
 * Fields: notificationId, employeeId, notificationType, messageContent,
 *         channel, timestamp, deliveryStatus
 */
public class Notification {

    // ── Enums ─────────────────────────────────────────────────────────────────

    public enum NotificationType {
        ENROLLMENT,
        CLAIM,
        APPROVAL,
        PAYROLL_UPDATE,
        COMPLIANCE_ALERT,
        GENERAL
    }

    public enum Channel {
        EMAIL,
        SMS,
        DASHBOARD
    }

    public enum DeliveryStatus {
        PENDING,
        DELIVERED,
        FAILED,
        RETRYING
    }

    // ── Fields ────────────────────────────────────────────────────────────────

    private final String notificationId;
    private final String employeeId;
    private final NotificationType notificationType;
    private final String messageContent;
    private Channel channel;
    private final LocalDateTime timestamp;
    private DeliveryStatus deliveryStatus;
    private int retryCount;

    // ── Constructor ───────────────────────────────────────────────────────────

    public Notification(String notificationId, String employeeId,
                        NotificationType notificationType,
                        String messageContent, Channel channel) {
        this.notificationId   = notificationId;
        this.employeeId       = employeeId;
        this.notificationType = notificationType;
        this.messageContent   = messageContent;
        this.channel          = channel;
        this.timestamp        = LocalDateTime.now();
        this.deliveryStatus   = DeliveryStatus.PENDING;
        this.retryCount       = 0;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public String getNotificationId()         { return notificationId; }
    public String getEmployeeId()             { return employeeId; }
    public NotificationType getNotificationType() { return notificationType; }
    public String getMessageContent()         { return messageContent; }
    public Channel getChannel()               { return channel; }
    public LocalDateTime getTimestamp()       { return timestamp; }
    public DeliveryStatus getDeliveryStatus() { return deliveryStatus; }
    public int getRetryCount()                { return retryCount; }

    // ── Setters ───────────────────────────────────────────────────────────────

    public void setDeliveryStatus(DeliveryStatus status) { this.deliveryStatus = status; }
    public void setChannel(Channel channel)              { this.channel = channel; }
    public void incrementRetryCount()                    { this.retryCount++; }

    // ── Display ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Notification {" +
                "\n  ID       : " + notificationId +
                "\n  Employee : " + employeeId +
                "\n  Type     : " + notificationType +
                "\n  Channel  : " + channel +
                "\n  Status   : " + deliveryStatus +
                "\n  Retries  : " + retryCount +
                "\n  Sent At  : " + timestamp +
                "\n  Message  : " + messageContent +
                "\n}";
    }
}
