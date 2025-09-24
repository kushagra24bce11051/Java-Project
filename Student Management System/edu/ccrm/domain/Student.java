package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Student class extending Person
 * Demonstrates inheritance and encapsulation
 */
public class Student extends Person {
    private String regNo;
    private List<String> enrolledCourses;
    private double gpa;

    public Student(String id, String regNo, String fullName, String email) {
        super(id, fullName, email);
        this.regNo = Objects.requireNonNull(regNo, "Registration number cannot be null");
        this.enrolledCourses = new ArrayList<>();
        this.gpa = 0.0;
    }

    @Override
    public String getDisplayInfo() {
        return String.format("Student: %s (%s) - %s", fullName, regNo, email);
    }

    public void enrollInCourse(String courseCode) {
        if (!enrolledCourses.contains(courseCode)) {
            enrolledCourses.add(courseCode);
        }
    }

    public void unenrollFromCourse(String courseCode) {
        enrolledCourses.remove(courseCode);
    }

    public boolean isEnrolledIn(String courseCode) {
        return enrolledCourses.contains(courseCode);
    }

    // Getters and setters
    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }

    public List<String> getEnrolledCourses() { return new ArrayList<>(enrolledCourses); }
    public void setEnrolledCourses(List<String> enrolledCourses) { 
        this.enrolledCourses = new ArrayList<>(enrolledCourses); 
    }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    @Override
    public String toString() {
        return String.format("Student{id='%s', regNo='%s', fullName='%s', email='%s', " +
                           "enrolledCourses=%d, gpa=%.2f, active=%s}", 
                           id, regNo, fullName, email, enrolledCourses.size(), gpa, active);
    }
}

