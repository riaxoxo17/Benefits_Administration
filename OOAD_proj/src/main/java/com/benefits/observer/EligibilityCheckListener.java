package main.java.com.benefits.observer;

import main.java.com.benefits.model.EmployeeProfile;

/**
 * A concrete implementation of ProfileObserver.
 * Simulates the Eligibility Verification Engine reacting
 * to a new or updated employee profile.
 *
 * GRASP - Information Expert: This listener knows how to
 * respond to profile events from an eligibility perspective.
 *
 * SOLID - Single Responsibility Principle (SRP): This class
 * only handles eligibility-related reactions to profile updates.
 */

public class EligibilityCheckListener implements ProfileObserver {

    /**
     * Triggered when a profile is created or updated.
     * Performs a basic eligibility pre-check based on employment type.
     *
     * @ param profile The updated employee profile.
     */

    @Override
    public void onProfileUpdated(EmployeeProfile profile) {
        System.out.println("\n[EligibilityCheckListener] Profile update detected.");
        System.out.println("  → Running eligibility pre-check for: "
                + profile.getName());

        // Basic rule: Only FULL_TIME employees are auto-eligible
        if (profile.getEmploymentType() == EmployeeProfile.EmploymentType.FULL_TIME) {
            profile.setEligibilityStatus(true);
            System.out.println("  → Status: ELIGIBLE (Full-Time Employee)");
        }

        else {
            profile.setEligibilityStatus(false);
            System.out.println("  → Status: NOT ELIGIBLE (" + profile.getEmploymentType() + ")");
        }

    }
}
