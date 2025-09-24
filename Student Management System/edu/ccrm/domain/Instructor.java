package edu.ccrm.domain;

import java.util.Objects;

/**
 * Instructor class extending Person
 * Demonstrates inheritance
 */
public class Instructor extends Person {
    private String department;
    private String employeeId;

    public Instructor(String id, String employeeId, String fullName, String email, String department) {
        super(id, fullName, email);
        this.employeeId = Objects.requireNonNull(employeeId, "Employee ID cannot be null");
        this.department = Objects.requireNonNull(department, "Department cannot be null");
    }

    @Override
    public String getDisplayInfo() {
        return String.format("Instructor: %s (%s) - %s, %s", fullName, employeeId, email, department);
    }

    // Getters and setters
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    @Override
    public String toString() {
        return String.format("Instructor{id='%s', employeeId='%s', fullName='%s', " +
                           "email='%s', department='%s', active=%s}", 
                           id, employeeId, fullName, email, department, active);
    }
}

