package edu.ccrm.domain;

import java.util.Objects;

/**
 * Course class with Builder pattern
 * Demonstrates Builder design pattern
 */
public class Course {
    private String code;
    private String title;
    private int credits;
    private String instructorId;
    private Semester semester;
    private String department;
    private boolean active;

    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructorId = builder.instructorId;
        this.semester = builder.semester;
        this.department = builder.department;
        this.active = builder.active;
    }

    // Builder pattern
    public static class Builder {
        private String code;
        private String title;
        private int credits;
        private String instructorId;
        private Semester semester;
        private String department;
        private boolean active = true;

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder credits(int credits) {
            this.credits = credits;
            return this;
        }

        public Builder instructorId(String instructorId) {
            this.instructorId = instructorId;
            return this;
        }

        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }

        public Builder department(String department) {
            this.department = department;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public Course build() {
            Objects.requireNonNull(code, "Course code is required");
            Objects.requireNonNull(title, "Course title is required");
            Objects.requireNonNull(semester, "Semester is required");
            Objects.requireNonNull(department, "Department is required");
            
            if (credits <= 0) {
                throw new IllegalArgumentException("Credits must be positive");
            }
            
            return new Course(this);
        }
    }

    // Getters and setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }

    public Semester getSemester() { return semester; }
    public void setSemester(Semester semester) { this.semester = semester; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return Objects.equals(code, course.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return String.format("Course{code='%s', title='%s', credits=%d, " +
                           "instructorId='%s', semester=%s, department='%s', active=%s}", 
                           code, title, credits, instructorId, semester, department, active);
    }
}

