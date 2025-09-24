package edu.ccrm.util;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;
import edu.ccrm.service.StudentService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class for generating reports using Stream API
 * Demonstrates Stream processing and lambda expressions
 */
public class ReportGenerator {
    private final StudentService studentService;

    public ReportGenerator(StudentService studentService) {
        this.studentService = studentService;
    }

    public void showReports() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== Reports ===");
            System.out.println("1. Top Students by GPA");
            System.out.println("2. GPA Distribution");
            System.out.println("3. Course Enrollment Statistics");
            System.out.println("4. Department-wise Course Count");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            String choice = scanner.nextLine().trim();
            scanner.close();
            
            switch (choice) {
                case "1": showTopStudents(); break;
                case "2": showGPADistribution(); break;
                case "3": showEnrollmentStats(); break;
                case "4": showDepartmentStats(); break;
                case "0": return;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showTopStudents() {
        List<Student> topStudents = studentService.getActiveStudents().stream()
                .filter(s -> s.getGpa() > 0)
                .sorted((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()))
                .limit(5)
                .collect(Collectors.toList());
        
        System.out.println("\n=== Top 5 Students by GPA ===");
        if (topStudents.isEmpty()) {
            System.out.println("No students with recorded grades found.");
        } else {
            topStudents.forEach(s -> 
                System.out.println(String.format("%s - GPA: %.2f", 
                    s.getFullName(), s.getGpa())));
        }
    }

    private void showGPADistribution() {
        Map<String, Long> distribution = studentService.getActiveStudents().stream()
                .filter(s -> s.getGpa() > 0)
                .collect(Collectors.groupingBy(
                    s -> getGPARange(s.getGpa()),
                    Collectors.counting()
                ));
        
        System.out.println("\n=== GPA Distribution ===");
        if (distribution.isEmpty()) {
            System.out.println("No students with recorded grades found.");
        } else {
            distribution.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> 
                        System.out.println(entry.getKey() + ": " + entry.getValue() + " students"));
        }
    }

    private void showEnrollmentStats() {
        List<Student> students = studentService.getActiveStudents();
        int totalEnrollments = students.stream()
                .mapToInt(s -> s.getEnrolledCourses().size())
                .sum();
        
        double avgEnrollments = students.isEmpty() ? 0 : 
                (double) totalEnrollments / students.size();
        
        System.out.println("\n=== Enrollment Statistics ===");
        System.out.println("Total Students: " + students.size());
        System.out.println("Total Enrollments: " + totalEnrollments);
        System.out.println("Average Enrollments per Student: " + 
                String.format("%.2f", avgEnrollments));
    }

    private void showDepartmentStats() {
        Map<String, Long> departmentStats = studentService.getAllCourses().stream()
                .collect(Collectors.groupingBy(
                    Course::getDepartment,
                    Collectors.counting()
                ));
        
        System.out.println("\n=== Department-wise Course Count ===");
        if (departmentStats.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            departmentStats.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEach(entry -> 
                        System.out.println(entry.getKey() + ": " + entry.getValue() + " courses"));
        }
    }

    private String getGPARange(double gpa) {
        if (gpa >= 9.0) return "9.0-10.0 (Excellent)";
        if (gpa >= 8.0) return "8.0-8.9 (Very Good)";
        if (gpa >= 7.0) return "7.0-7.9 (Good)";
        if (gpa >= 6.0) return "6.0-6.9 (Average)";
        if (gpa >= 5.0) return "5.0-5.9 (Below Average)";
        return "0.0-4.9 (Poor)";
    }
}



