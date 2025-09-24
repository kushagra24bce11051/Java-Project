package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.service.StudentService;
import edu.ccrm.util.ReportGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main CLI application for Campus Course & Records Manager
 * Demonstrates comprehensive Java features and design patterns
 */
public class CCRMApplication {
    private final Scanner scanner;
    private final StudentService studentService;
    private final ImportExportService importExportService;
    private final AppConfig config;
    private final ReportGenerator reportGenerator;

    public CCRMApplication() {
        this.scanner = new Scanner(System.in);
        this.config = AppConfig.getInstance();
        this.studentService = new StudentService(config.getMaxCreditsPerSemester());
        this.importExportService = new ImportExportService(studentService);
        this.reportGenerator = new ReportGenerator(studentService);
        
        // Initialize data directories
        try {
            Files.createDirectories(config.getDataDirectory());
            Files.createDirectories(config.getBackupDirectory());
        } catch (IOException e) {
            System.err.println("Error creating directories: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        CCRMApplication app = new CCRMApplication();
        app.run();
    }

    public void run() {
        System.out.println("=== Campus Course & Records Manager (CCRM) ===");
        System.out.println("Welcome to the Campus Course & Records Management System!");
        
        // Load initial data if available
        loadInitialData();
        
        while (true) {
            showMainMenu();
            String menuSelection = scanner.nextLine().trim();
            
            try {
                switch (menuSelection) {
                    case "1": manageStudents(); break;
                    case "2": manageCourses(); break;
                    case "3": manageEnrollments(); break;
                    case "4": manageGrades(); break;
                    case "5": importExportData(); break;
                    case "6": backupAndReports(); break;
                    case "7": showReports(); break;
                    case "8": config.printPlatformInfo(); break;
                    case "0": 
                        System.out.println("Thank you for using CCRM!");
                        return;
                    default: 
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private void showMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollments");
        System.out.println("4. Manage Grades");
        System.out.println("5. Import/Export Data");
        System.out.println("6. Backup & Reports");
        System.out.println("7. Show Reports");
        System.out.println("8. Platform Information");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private void manageStudents() {
        while (true) {
            System.out.println("\n=== Student Management ===");
            System.out.println("1. Add Student");
            System.out.println("2. List All Students");
            System.out.println("3. View Student Profile");
            System.out.println("4. Update Student");
            System.out.println("5. Deactivate Student");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            String studentMenuSelection = scanner.nextLine().trim();
            
            switch (studentMenuSelection) {
                case "1": addStudent(); break;
                case "2": listStudents(); break;
                case "3": viewStudentProfile(); break;
                case "4": updateStudent(); break;
                case "5": deactivateStudent(); break;
                case "0": return;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addStudent() {
        System.out.print("Enter Student ID: ");
        String studentIdInput = scanner.nextLine().trim();
        
        System.out.print("Enter Registration Number: ");
        String registrationNumberInput = scanner.nextLine().trim();
        
        System.out.print("Enter Full Name: ");
        String fullNameInput = scanner.nextLine().trim();
        
        System.out.print("Enter Email: ");
        String emailInput = scanner.nextLine().trim();
        
        Student student = new Student(studentIdInput, registrationNumberInput, fullNameInput, emailInput);
        studentService.addStudent(student);
        System.out.println("Student added successfully!");
    }

    private void listStudents() {
        List<Student> activeStudents = studentService.getActiveStudents();
        if (activeStudents.isEmpty()) {
            System.out.println("No active students found.");
        } else {
            System.out.println("\nActive Students:");
            activeStudents.forEach(s -> System.out.println(s.getDisplayInfo()));
        }
    }

    private void viewStudentProfile() {
        System.out.print("Enter Student ID: ");
        String profileStudentId = scanner.nextLine().trim();
        
        Student student = studentService.getStudent(profileStudentId);
        if (student != null) {
            System.out.println("\n" + student.getDisplayInfo());
            System.out.println("Enrolled Courses: " + student.getEnrolledCourses());
            System.out.println("GPA: " + String.format("%.2f", student.getGpa()));
        } else {
            System.out.println("Student not found.");
        }
    }

    private void updateStudent() {
        System.out.print("Enter Student ID: ");
        String updateStudentId = scanner.nextLine().trim();
        
        Student student = studentService.getStudent(updateStudentId);
        if (student != null) {
            System.out.print("Enter new Full Name (or press Enter to keep current): ");
            String newFullNameInput = scanner.nextLine().trim();
            if (!newFullNameInput.isEmpty()) {
                student.setFullName(newFullNameInput);
            }
            
            System.out.print("Enter new Email (or press Enter to keep current): ");
            String newEmailInput = scanner.nextLine().trim();
            if (!newEmailInput.isEmpty()) {
                student.setEmail(newEmailInput);
            }
            
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Student not found.");
        }
    }

    private void deactivateStudent() {
        System.out.print("Enter Student ID: ");
        String deactivateStudentId = scanner.nextLine().trim();
        
        Student student = studentService.getStudent(deactivateStudentId);
        if (student != null) {
            student.setActive(false);
            System.out.println("Student deactivated successfully!");
        } else {
            System.out.println("Student not found.");
        }
    }

    private void manageCourses() {
        while (true) {
            System.out.println("\n=== Course Management ===");
            System.out.println("1. Add Course");
            System.out.println("2. List All Courses");
            System.out.println("3. Search Courses by Instructor");
            System.out.println("4. Search Courses by Department");
            System.out.println("5. Search Courses by Semester");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            String courseMenuSelection = scanner.nextLine().trim();
            
            switch (courseMenuSelection) {
                case "1": addCourse(); break;
                case "2": listCourses(); break;
                case "3": searchCoursesByInstructor(); break;
                case "4": searchCoursesByDepartment(); break;
                case "5": searchCoursesBySemester(); break;
                case "0": return;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addCourse() {
        System.out.print("Enter Course Code: ");
        String courseCodeInput = scanner.nextLine().trim();
        
        System.out.print("Enter Course Title: ");
        String courseTitleInput = scanner.nextLine().trim();
        
        System.out.print("Enter Credits: ");
        int courseCreditsInput = Integer.parseInt(scanner.nextLine().trim());
        
        System.out.print("Enter Instructor ID: ");
        String instructorIdInput = scanner.nextLine().trim();
        
        System.out.println("Select Semester:");
        for (Semester semester : Semester.values()) {
            System.out.println(semester.ordinal() + 1 + ". " + semester.getDisplayName());
        }
        int semesterIndexSelection = Integer.parseInt(scanner.nextLine().trim()) - 1;
        Semester selectedSemester = Semester.values()[semesterIndexSelection];
        
        System.out.print("Enter Department: ");
        String departmentInput = scanner.nextLine().trim();
        
        Course course = new Course.Builder()
                .code(courseCodeInput)
                .title(courseTitleInput)
                .credits(courseCreditsInput)
                .instructorId(instructorIdInput)
                .semester(selectedSemester)
                .department(departmentInput)
                .build();
        
        studentService.addCourse(course);
        System.out.println("Course added successfully!");
    }

    private void listCourses() {
        List<Course> allCourses = studentService.getAllCourses();
        if (allCourses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            System.out.println("\nAll Courses:");
            allCourses.forEach(c -> System.out.println(c.toString()));
        }
    }

    private void searchCoursesByInstructor() {
        System.out.print("Enter Instructor ID: ");
        String searchInstructorId = scanner.nextLine().trim();
        
        List<Course> instructorCourses = studentService.searchCoursesByInstructor(searchInstructorId);
        if (instructorCourses.isEmpty()) {
            System.out.println("No courses found for instructor: " + searchInstructorId);
        } else {
            System.out.println("\nCourses by Instructor " + searchInstructorId + ":");
            instructorCourses.forEach(c -> System.out.println(c.toString()));
        }
    }

    private void searchCoursesByDepartment() {
        System.out.print("Enter Department: ");
        String searchDepartment = scanner.nextLine().trim();
        
        List<Course> departmentCourses = studentService.searchCoursesByDepartment(searchDepartment);
        if (departmentCourses.isEmpty()) {
            System.out.println("No courses found for department: " + searchDepartment);
        } else {
            System.out.println("\nCourses in Department " + searchDepartment + ":");
            departmentCourses.forEach(c -> System.out.println(c.toString()));
        }
    }

    private void searchCoursesBySemester() {
        System.out.println("Select Semester:");
        for (Semester semester : Semester.values()) {
            System.out.println(semester.ordinal() + 1 + ". " + semester.getDisplayName());
        }
        int semesterSelectionIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
        Semester semester = Semester.values()[semesterSelectionIndex];
        
        List<Course> semesterCourses = studentService.searchCoursesBySemester(semester);
        if (semesterCourses.isEmpty()) {
            System.out.println("No courses found for semester: " + semester.getDisplayName());
        } else {
            System.out.println("\nCourses in " + semester.getDisplayName() + " Semester:");
            semesterCourses.forEach(c -> System.out.println(c.toString()));
        }
    }

    private void manageEnrollments() {
        while (true) {
            System.out.println("\n=== Enrollment Management ===");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. Unenroll Student from Course");
            System.out.println("3. View Student Enrollments");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            String enrollmentMenuSelection = scanner.nextLine().trim();
            
            switch (enrollmentMenuSelection) {
                case "1": enrollStudent(); break;
                case "2": unenrollStudent(); break;
                case "3": viewStudentEnrollments(); break;
                case "0": return;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void enrollStudent() {
        System.out.print("Enter Student ID: ");
        String enrollStudentId = scanner.nextLine().trim();
        
        System.out.print("Enter Course Code: ");
        String enrollCourseCode = scanner.nextLine().trim();
        
        try {
            studentService.enrollStudent(enrollStudentId, enrollCourseCode);
            System.out.println("Student enrolled successfully!");
        } catch (DuplicateEnrollmentException e) {
            System.err.println("Enrollment failed: " + e.getMessage());
        } catch (MaxCreditLimitExceededException e) {
            System.err.println("Enrollment failed: " + e.getMessage());
        }
    }

    private void unenrollStudent() {
        System.out.print("Enter Student ID: ");
        String unenrollStudentId = scanner.nextLine().trim();
        
        System.out.print("Enter Course Code: ");
        String unenrollCourseCode = scanner.nextLine().trim();
        
        studentService.unenrollStudent(unenrollStudentId, unenrollCourseCode);
        System.out.println("Student unenrolled successfully!");
    }

    private void viewStudentEnrollments() {
        System.out.print("Enter Student ID: ");
        String enrollmentsStudentId = scanner.nextLine().trim();
        
        Student student = studentService.getStudent(enrollmentsStudentId);
        if (student != null) {
            System.out.println("\nEnrollments for " + student.getFullName() + ":");
            student.getEnrolledCourses().forEach(courseCode -> {
                Course course = studentService.getCourse(courseCode);
                if (course != null) {
                    System.out.println("- " + course.getCode() + ": " + course.getTitle());
                }
            });
        } else {
            System.out.println("Student not found.");
        }
    }

    private void manageGrades() {
        while (true) {
            System.out.println("\n=== Grade Management ===");
            System.out.println("1. Record Grade");
            System.out.println("2. View Student Transcript");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1": recordGrade(); break;
                case "2": viewTranscript(); break;
                case "0": return;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void recordGrade() {
        System.out.print("Enter Student ID: ");
        String gradeStudentId = scanner.nextLine().trim();
        
        System.out.print("Enter Course Code: ");
        String gradeCourseCode = scanner.nextLine().trim();
        
        System.out.print("Enter Marks (0-100): ");
        double gradeMarksInput = Double.parseDouble(scanner.nextLine().trim());
        
        studentService.recordGrade(gradeStudentId, gradeCourseCode, gradeMarksInput);
        System.out.println("Grade recorded successfully!");
    }

    private void viewTranscript() {
        System.out.print("Enter Student ID: ");
        String transcriptStudentId = scanner.nextLine().trim();
        
        String transcript = studentService.generateTranscript(transcriptStudentId);
        System.out.println("\n" + transcript);
    }

    private void importExportData() {
        while (true) {
            System.out.println("\n=== Import/Export Data ===");
            System.out.println("1. Import Students from CSV");
            System.out.println("2. Import Courses from CSV");
            System.out.println("3. Export Students to CSV");
            System.out.println("4. Export Courses to CSV");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            String dataMenuSelection = scanner.nextLine().trim();
            
            try {
                switch (dataMenuSelection) {
                    case "1": 
                        System.out.print("Enter CSV file path: ");
                        Path importStudentsCsvPath = Paths.get(scanner.nextLine().trim());
                        importExportService.importStudentsFromCSV(importStudentsCsvPath);
                        System.out.println("Students imported successfully!");
                        break;
                    case "2": 
                        System.out.print("Enter CSV file path: ");
                        Path importCoursesCsvPath = Paths.get(scanner.nextLine().trim());
                        importExportService.importCoursesFromCSV(importCoursesCsvPath);
                        System.out.println("Courses imported successfully!");
                        break;
                    case "3": 
                        Path studentsExportCsvPath = config.getDataDirectory().resolve("students_export.csv");
                        importExportService.exportStudentsToCSV(studentsExportCsvPath);
                        System.out.println("Students exported to: " + studentsExportCsvPath);
                        break;
                    case "4": 
                        Path coursesExportCsvPath = config.getDataDirectory().resolve("courses_export.csv");
                        importExportService.exportCoursesToCSV(coursesExportCsvPath);
                        System.out.println("Courses exported to: " + coursesExportCsvPath);
                        break;
                    case "0": return;
                    default: System.out.println("Invalid choice. Please try again.");
                }
            } catch (IOException e) {
                System.err.println("File operation failed: " + e.getMessage());
            }
        }
    }

    private void backupAndReports() {
        while (true) {
            System.out.println("\n=== Backup & Reports ===");
            System.out.println("1. Create Backup");
            System.out.println("2. Show Backup Directory Size");
            System.out.println("3. List Backup Files by Depth");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            String backupMenuSelection = scanner.nextLine().trim();
            
            try {
                switch (backupMenuSelection) {
                    case "1": 
                        importExportService.createBackup(config.getBackupDirectory());
                        break;
                    case "2": 
                        long backupDirectorySizeBytes = importExportService.getBackupDirectorySize(config.getBackupDirectory());
                        System.out.println("Backup directory size: " + backupDirectorySizeBytes + " bytes");
                        break;
                    case "3": 
                        System.out.print("Enter max depth: ");
                        int maxTraversalDepth = Integer.parseInt(scanner.nextLine().trim());
                        importExportService.listBackupFilesByDepth(config.getBackupDirectory(), maxTraversalDepth);
                        break;
                    case "0": return;
                    default: System.out.println("Invalid choice. Please try again.");
                }
            } catch (IOException e) {
                System.err.println("Backup operation failed: " + e.getMessage());
            }
        }
    }

    private void showReports() {
        reportGenerator.showReports();
    }

    private void loadInitialData() {
        // Load sample data if files exist
        try {
            Path studentFile = config.getDataDirectory().resolve("students.csv");
            Path courseFile = config.getDataDirectory().resolve("courses.csv");
            
            if (Files.exists(studentFile)) {
                importExportService.importStudentsFromCSV(studentFile);
            }
            if (Files.exists(courseFile)) {
                importExportService.importCoursesFromCSV(courseFile);
            }
        } catch (IOException e) {
            System.out.println("No initial data files found. Starting with empty system.");
        }
    }
}
