package main.java.com.benefits.model;

/**
 * Represents an employee's benefits profile.
 * 
 * GRASP - Information Expert: This class owns and manages all
 * employee profile data, making it the expert on employee information.
 * 
 * SOLID - Single Responsibility Principle (SRP): This class is solely
 * responsible for holding and representing employee profile data.
 */

public class EmployeeProfile {

    // Fields

    private String employeeId;
    private String name;
    private String designation;
    private String department;
    private String salaryBand;
    private EmploymentType employmentType;
    private String dateOfJoining;
    private boolean eligibilityStatus;
    private String benefitEnrollmentHistory;

    // Enum for Employment Type ─────────────────────────────────────────────

    /**
     * SOLID - Open/Closed Principle (OCP): Adding new employment types
     * only requires extending this enum, not modifying existing logic.
     */

    public enum EmploymentType {
        FULL_TIME,
        CONTRACT,
        INTERN
    }

    // ── Constructor ──────────────────────────────────────────────────────────

    /**
     * Constructs a new EmployeeProfile with all required fields.
     * eligibilityStatus defaults to false until verified.
     */
    public EmployeeProfile(String employeeId, String name, String designation,
            String department, String salaryBand,
            EmploymentType employmentType, String dateOfJoining) {
        this.employeeId = employeeId;
        this.name = name;
        this.designation = designation;
        this.department = department;
        this.salaryBand = salaryBand;
        this.employmentType = employmentType;
        this.dateOfJoining = dateOfJoining;
        this.eligibilityStatus = false; // default: not yet verified
        this.benefitEnrollmentHistory = "No enrollments yet.";
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public String getDepartment() {
        return department;
    }

    public String getSalaryBand() {
        return salaryBand;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public boolean isEligibilityStatus() {
        return eligibilityStatus;
    }

    public String getBenefitEnrollmentHistory() {
        return benefitEnrollmentHistory;
    }

    // ── Setters ──────────────────────────────────────────────────────────────

    public void setEligibilityStatus(boolean status) {
        this.eligibilityStatus = status;
    }

    public void setBenefitEnrollmentHistory(String history) {
        this.benefitEnrollmentHistory = history;
    }

    // ── Display ──────────────────────────────────────────────────────────────

    /**
     * Returns a readable summary of the employee's profile.
     */
    @Override
    public String toString() {
        return "EmployeeProfile {" +
                "\n  ID            : " + employeeId +
                "\n  Name          : " + name +
                "\n  Designation   : " + designation +
                "\n  Department    : " + department +
                "\n  Salary Band   : " + salaryBand +
                "\n  Employment    : " + employmentType +
                "\n  Joined        : " + dateOfJoining +
                "\n  Eligible      : " + eligibilityStatus +
                "\n  History       : " + benefitEnrollmentHistory +
                "\n}";
    }

}
