package main.java.com.benefits.builder;

/*
 * Creational Pattern: Builder
 * ----------------------------
 * Constructs complex objects step-by-step.
 *
 * WHY BUILDER?
 * - Avoids large constructors with many parameters
 * - Improves readability and flexibility
 * - Allows optional attributes to be set cleanly
 */

public class EmployeeBenefitProfile {

    /**
     * Core attributes of the Employee Benefit Profile
     *
     * These represent the state of the object being constructed.
     */
    private int employeeId;
    private String plan;
    private double coverage;

    /**
     * Private constructor
     *
     * PURPOSE:
     * - Prevents direct object creation using "new"
     * - Forces object creation through Builder
     *
     * DESIGN PRINCIPLE:
     * - Encapsulation: Object creation logic is controlled
     */
    private EmployeeBenefitProfile(Builder builder) {

        /**
         * Assigning values from Builder to the actual object
         *
         * WHY:
         * - Builder holds temporary state during construction
         * - Final object is created only when build() is called
         */
        this.employeeId = builder.employeeId;
        this.plan = builder.plan;
        this.coverage = builder.coverage;
    }

    /**
     * Static Inner Builder Class
     *
     * PURPOSE:
     * - Provides a step-by-step way to construct EmployeeBenefitProfile
     *
     * DESIGN PATTERN:
     * - Builder Pattern (Creational)
     *
     * GRASP PRINCIPLE:
     * - Creator:
     *   Builder is responsible for creating EmployeeBenefitProfile objects
     *
     * SOLID PRINCIPLES:
     * - SRP (Single Responsibility Principle):
     *   Builder handles construction logic, while the main class represents data
     *
     * - OCP (Open/Closed Principle):
     *   New fields can be added with new setter methods without modifying existing usage
     */
    public static class Builder {

        /**
         * Fields mirror the outer class
         *
         * These act as temporary holders before object creation
         */
        private int employeeId;
        private String plan;
        private double coverage;

        /**
         * Sets employee ID
         *
         * RETURNS:
         * - Builder object → enables method chaining
         *
         * EXAMPLE:
         * new Builder().setEmployeeId(101).setPlan("Gold")...
         */
        public Builder setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
            return this; // Enables chaining
        }

        /**
         * Sets benefit plan type
         *
         * EXAMPLES:
         * - "Basic"
         * - "Premium"
         * - "Gold"
         */
        public Builder setPlan(String plan) {
            this.plan = plan;
            return this;
        }

        /**
         * Sets coverage amount
         *
         * EXAMPLE:
         * - Insurance coverage value
         */
        public Builder setCoverage(double coverage) {
            this.coverage = coverage;
            return this;
        }

        /**
         * Final step: Build the EmployeeBenefitProfile object
         *
         * PROCESS:
         * - Takes all values set in Builder
         * - Passes them to private constructor
         *
         * DESIGN NOTE:
         * - Ensures object is created in a controlled and consistent way
         */
        public EmployeeBenefitProfile build() {
            return new EmployeeBenefitProfile(this);
        }
    }
}