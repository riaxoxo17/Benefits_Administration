package com.benefits.notification.exception;

/**
 * Exception thrown when a notification cannot be delivered.
 *
 * Error Code: NOTIFICATION_DELIVERY_FAILED
 * Category  : MINOR — Retry delivery; fallback to alternate channel.
 *
 * SOLID - SRP: One class, one exception type.
 */
public class NotificationDeliveryFailedException extends RuntimeException {

    private final String notificationId;
    private final String channel;

    public NotificationDeliveryFailedException(String notificationId,
                                               String channel, Throwable cause) {
        super("[NOTIFICATION_DELIVERY_FAILED] Failed to send notification ID: "
                + notificationId + " via " + channel, cause);
        this.notificationId = notificationId;
        this.channel        = channel;
    }

    public NotificationDeliveryFailedException(String message) {
        super("[NOTIFICATION_DELIVERY_FAILED] " + message);
        this.notificationId = "UNKNOWN";
        this.channel        = "UNKNOWN";
    }

    public String getNotificationId() { return notificationId; }
    public String getChannel()        { return channel; }
}
