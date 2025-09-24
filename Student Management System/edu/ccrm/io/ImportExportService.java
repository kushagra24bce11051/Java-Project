package edu.ccrm.io;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.service.StudentService;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service for importing and exporting data using NIO.2 and Streams
 * Demonstrates NIO.2 APIs and Stream processing
 */
public class ImportExportService {
    private final StudentService studentService;

    public ImportExportService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void importStudentsFromCSV(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.skip(1) // Skip header
                 .filter(line -> line != null && !line.trim().isEmpty())
                 .map(this::parseStudentFromCSV)
                 .forEach(studentService::addStudent);
        }
    }

    public void importCoursesFromCSV(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.skip(1) // Skip header
                 .filter(line -> line != null && !line.trim().isEmpty())
                 .map(this::parseCourseFromCSV)
                 .forEach(studentService::addCourse);
        }
    }

    public void exportStudentsToCSV(Path filePath) throws IOException {
        List<String> lines = studentService.getAllStudents().stream()
                .map(this::studentToCSV)
                .collect(Collectors.toList());
        
        lines.add(0, "ID,RegNo,FullName,Email,Active,GPA"); // Add header
        
        Files.write(filePath, lines);
    }

    public void exportCoursesToCSV(Path filePath) throws IOException {
        List<String> lines = studentService.getAllCourses().stream()
                .map(this::courseToCSV)
                .collect(Collectors.toList());
        
        lines.add(0, "Code,Title,Credits,InstructorId,Semester,Department,Active"); // Add header
        
        Files.write(filePath, lines);
    }

    public void createBackup(Path backupDir) throws IOException {
        String timestamp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        Path timestampedDir = backupDir.resolve("backup_" + timestamp);
        
        Files.createDirectories(timestampedDir);
        
        // Export data to backup directory
        exportStudentsToCSV(timestampedDir.resolve("students.csv"));
        exportCoursesToCSV(timestampedDir.resolve("courses.csv"));
        
        System.out.println("Backup created at: " + timestampedDir);
    }

    public long getBackupDirectorySize(Path backupDir) throws IOException {
        if (!Files.exists(backupDir)) {
            return 0;
        }
        
        return Files.walk(backupDir)
                .filter(Files::isRegularFile)
                .mapToLong(this::getFileSize)
                .sum();
    }

    public void listBackupFilesByDepth(Path backupDir, int maxDepth) throws IOException {
        if (!Files.exists(backupDir)) {
            System.out.println("Backup directory does not exist");
            return;
        }
        
        Files.walk(backupDir, maxDepth)
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    try {
                        int depth = backupDir.relativize(path).getNameCount();
                        System.out.println("Depth " + depth + ": " + path.getFileName() + 
                                         " (" + Files.size(path) + " bytes)");
                    } catch (IOException e) {
                        System.err.println("Error reading file: " + path);
                    }
                });
    }

    private Student parseStudentFromCSV(String line) {
        String[] fields = line.split(",");
        Student student = new Student(fields[0], fields[1], fields[2], fields[3]);
        student.setActive(Boolean.parseBoolean(fields[4]));
        if (fields.length > 5) {
            student.setGpa(Double.parseDouble(fields[5]));
        }
        return student;
    }

    private Course parseCourseFromCSV(String line) {
        String[] fields = line.split(",");
        return new Course.Builder()
                .code(fields[0])
                .title(fields[1])
                .credits(Integer.parseInt(fields[2]))
                .instructorId(fields[3])
                .semester(Semester.valueOf(fields[4]))
                .department(fields[5])
                .active(Boolean.parseBoolean(fields[6]))
                .build();
    }

    private String studentToCSV(Student student) {
        return String.format("%s,%s,%s,%s,%s,%.2f",
                student.getId(),
                student.getRegNo(),
                student.getFullName(),
                student.getEmail(),
                student.isActive(),
                student.getGpa());
    }

    private String courseToCSV(Course course) {
        return String.format("%s,%s,%d,%s,%s,%s,%s",
                course.getCode(),
                course.getTitle(),
                course.getCredits(),
                course.getInstructorId(),
                course.getSemester(),
                course.getDepartment(),
                course.isActive());
    }

    private long getFileSize(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            return 0;
        }
    }
}



