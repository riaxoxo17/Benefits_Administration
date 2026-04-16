package com.benefits.events;

import com.benefits.notification.model.Notification;
import com.benefits.notification.service.NotificationService;

/**
 * OBSERVER PATTERN — Concrete Subscriber for the Notification Module.
 *
 * Subscribes to the BenefitsEventPublisher and reacts to every major
 * system event by sending the appropriate notification to the employee.
 *
 * GRASP - Pure Fabrication: Infrastructure listener — no domain role.
 * GRASP - Low Coupling: Does not know about enrollment, payroll, or claims.
 *                       It only knows about events and NotificationService.
 * SOLID - SRP: Only responsible for translating events into notifications.
 * SOLID - DIP: Depends on NotificationService interface, not the concrete impl.
 */
public class NotificationEventListener implements BenefitsEventListener {

    private final NotificationService notificationService;

    public NotificationEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
        System.out.println("[NotificationEventListener] Registered and listening.");
    }

    @Override
    public void onEvent(BenefitsEvent event) {
        System.out.println("[NotificationEventListener] Received: "
                + event.getClass().getSimpleName()
                + " for Employee: " + event.getEmployeeId());

        Notification.NotificationType type    = resolveType(event);
        String                        message = event.getEventDescription();

        // Default channel: EMAIL. Can be made employee-preference-driven later.
        notificationService.sendNotification(
                event.getEmployeeId(),
                type,
                message,
                Notification.Channel.EMAIL
        );
    }

    /**
     * Maps event type to the appropriate NotificationType.
     * SOLID - OCP: Add new mappings here as new event types are introduced.
     */
    private Notification.NotificationType resolveType(BenefitsEvent event) {
        if (event instanceof EnrollmentApprovedEvent) return Notification.NotificationType.APPROVAL;
        if (event instanceof ClaimFiledEvent)         return Notification.NotificationType.CLAIM;
        if (event instanceof PayrollUpdatedEvent)     return Notification.NotificationType.PAYROLL_UPDATE;
        return Notification.NotificationType.GENERAL;
    }
}
