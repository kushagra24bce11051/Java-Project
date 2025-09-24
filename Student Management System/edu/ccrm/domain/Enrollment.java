package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Enrollment class representing student-course enrollment
 */
public class Enrollment {
    private String studentId;
    private String courseCode;
    private LocalDate enrollmentDate;
    private Grade grade;
    private double marks;
    private boolean active;

    public Enrollment(String studentId, String courseCode) {
        this.studentId = Objects.requireNonNull(studentId, "Student ID cannot be null");
        this.courseCode = Objects.requireNonNull(courseCode, "Course code cannot be null");
        this.enrollmentDate = LocalDate.now();
        this.grade = null;
        this.marks = 0.0;
        this.active = true;
    }

    public void recordGrade(double marks) {
        this.marks = marks;
        this.grade = Grade.fromPercentage(marks);
    }

    // Getters and setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public Grade getGrade() { return grade; }
    public void setGrade(Grade grade) { this.grade = grade; }

    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Enrollment that = (Enrollment) obj;
        return Objects.equals(studentId, that.studentId) && 
               Objects.equals(courseCode, that.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseCode);
    }

    @Override
    public String toString() {
        return String.format("Enrollment{studentId='%s', courseCode='%s', " +
                           "enrollmentDate=%s, grade=%s, marks=%.2f, active=%s}", 
                           studentId, courseCode, enrollmentDate, grade, marks, active);
    }
}

