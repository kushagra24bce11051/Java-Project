package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for student management
 * Demonstrates service layer pattern and Stream API usage
 */
public class StudentService {
    private final Map<String, Student> students;
    private final Map<String, Course> courses;
    private final Map<String, List<Enrollment>> enrollments;
    private final int maxCreditsPerSemester;

    public StudentService(int maxCreditsPerSemester) {
        this.students = new HashMap<>();
        this.courses = new HashMap<>();
        this.enrollments = new HashMap<>();
        this.maxCreditsPerSemester = maxCreditsPerSemester;
    }

    public void addStudent(Student student) {
        students.put(student.getId(), student);
        enrollments.put(student.getId(), new ArrayList<>());
    }

    public Student getStudent(String studentId) {
        return students.get(studentId);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    public List<Student> getActiveStudents() {
        return students.values().stream()
                .filter(Student::isActive)
                .collect(Collectors.toList());
    }

    public void enrollStudent(String studentId, String courseCode) 
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        
        Student targetStudent = students.get(studentId);
        Course targetCourse = courses.get(courseCode);
        
        if (targetStudent == null) {
            throw new IllegalArgumentException("Student not found: " + studentId);
        }
        if (targetCourse == null) {
            throw new IllegalArgumentException("Course not found: " + courseCode);
        }

        // Check for duplicate enrollment
        List<Enrollment> studentActiveEnrollments = enrollments.get(studentId);
        boolean alreadyEnrolled = studentActiveEnrollments.stream()
                .anyMatch(e -> e.getCourseCode().equals(courseCode) && e.isActive());
        
        if (alreadyEnrolled) {
            throw new DuplicateEnrollmentException("Student already enrolled in course: " + courseCode);
        }

        // Check credit limit
        int currentCredits = studentActiveEnrollments.stream()
                .filter(Enrollment::isActive)
                .mapToInt(e -> courses.get(e.getCourseCode()).getCredits())
                .sum();
        
        if (currentCredits + targetCourse.getCredits() > maxCreditsPerSemester) {
            throw new MaxCreditLimitExceededException(
                String.format("Credit limit exceeded. Current: %d, Adding: %d, Max: %d", 
                             currentCredits, targetCourse.getCredits(), maxCreditsPerSemester));
        }

        // Create enrollment
        Enrollment newEnrollment = new Enrollment(studentId, courseCode);
        studentActiveEnrollments.add(newEnrollment);
        targetStudent.enrollInCourse(courseCode);
    }

    public void unenrollStudent(String studentId, String courseCode) {
        List<Enrollment> studentEnrollmentList = enrollments.get(studentId);
        if (studentEnrollmentList != null) {
            studentEnrollmentList.stream()
                    .filter(e -> e.getCourseCode().equals(courseCode) && e.isActive())
                    .findFirst()
                    .ifPresent(e -> {
                        e.setActive(false);
                        students.get(studentId).unenrollFromCourse(courseCode);
                    });
        }
    }

    public void recordGrade(String studentId, String courseCode, double marks) {
        List<Enrollment> studentEnrollmentList = enrollments.get(studentId);
        if (studentEnrollmentList != null) {
            studentEnrollmentList.stream()
                    .filter(e -> e.getCourseCode().equals(courseCode) && e.isActive())
                    .findFirst()
                    .ifPresent(e -> e.recordGrade(marks));
            
            // Update student GPA
            updateStudentGPA(studentId);
        }
    }

    private void updateStudentGPA(String studentId) {
        List<Enrollment> studentEnrollmentList = enrollments.get(studentId);
        if (studentEnrollmentList != null) {
            double totalQualityPoints = studentEnrollmentList.stream()
                    .filter(Enrollment::isActive)
                    .filter(e -> e.getGrade() != null)
                    .mapToDouble(e -> {
                        Course course = courses.get(e.getCourseCode());
                        return e.getGrade().getPoints() * course.getCredits();
                    })
                    .sum();
            
            int totalAttemptedCredits = studentEnrollmentList.stream()
                    .filter(Enrollment::isActive)
                    .filter(e -> e.getGrade() != null)
                    .mapToInt(e -> courses.get(e.getCourseCode()).getCredits())
                    .sum();
            
            if (totalAttemptedCredits > 0) {
                double gpa = totalQualityPoints / totalAttemptedCredits;
                students.get(studentId).setGpa(gpa);
            }
        }
    }

    public String generateTranscript(String studentId) {
        Student targetStudent = students.get(studentId);
        if (targetStudent == null) {
            return "Student not found: " + studentId;
        }

        StringBuilder transcriptBuilder = new StringBuilder();
        transcriptBuilder.append("=== TRANSCRIPT ===\n");
        transcriptBuilder.append("Student: ").append(targetStudent.getFullName()).append("\n");
        transcriptBuilder.append("Registration No: ").append(targetStudent.getRegNo()).append("\n");
        transcriptBuilder.append("GPA: ").append(String.format("%.2f", targetStudent.getGpa())).append("\n\n");
        
        transcriptBuilder.append("Course Records:\n");
        transcriptBuilder.append("Code\tTitle\t\t\tCredits\tGrade\tMarks\n");
        transcriptBuilder.append("----\t-----\t\t\t-------\t-----\t-----\n");
        
        List<Enrollment> studentEnrollmentListForTranscript = enrollments.get(studentId);
        if (studentEnrollmentListForTranscript != null) {
            studentEnrollmentListForTranscript.stream()
                    .filter(Enrollment::isActive)
                    .forEach(e -> {
                        Course course = courses.get(e.getCourseCode());
                        transcriptBuilder.append(String.format("%s\t%-20s\t%d\t%s\t%.1f\n",
                                course.getCode(),
                                course.getTitle(),
                                course.getCredits(),
                                e.getGrade() != null ? e.getGrade().getLetter() : "N/A",
                                e.getMarks()));
                    });
        }
        
        return transcriptBuilder.toString();
    }

    public void addCourse(Course course) {
        courses.put(course.getCode(), course);
    }

    public Course getCourse(String courseCode) {
        return courses.get(courseCode);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }

    public List<Course> searchCoursesByInstructor(String instructorId) {
        return courses.values().stream()
                .filter(c -> c.getInstructorId().equals(instructorId))
                .collect(Collectors.toList());
    }

    public List<Course> searchCoursesByDepartment(String department) {
        return courses.values().stream()
                .filter(c -> c.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    public List<Course> searchCoursesBySemester(edu.ccrm.domain.Semester semester) {
        return courses.values().stream()
                .filter(c -> c.getSemester().equals(semester))
                .collect(Collectors.toList());
    }
}



