# Campus Course & Records Manager (CCRM)

A comprehensive Java SE console application for managing campus courses, student records, enrollments, and grades.

## Project Overview

CCRM is a console-based Java application that demonstrates advanced Java programming concepts including:
- Object-Oriented Programming (OOP) principles
- Design patterns (Singleton, Builder)
- Exception handling and custom exceptions
- File I/O using NIO.2 and Streams API
- Lambda expressions and functional programming
- Date/Time API
- Generic interfaces and collections

## Features

### Core Functionality
- **Student Management**: Add, update, list, and deactivate students
- **Course Management**: Create, update, search courses by instructor/department/semester
- **Enrollment Management**: Enroll/unenroll students with credit limit validation
- **Grade Management**: Record marks, compute GPA, generate transcripts
- **File Operations**: Import/export CSV data, backup with timestamps
- **Reports**: GPA distribution, top students, enrollment statistics

### Technical Features
- **Design Patterns**: Singleton (AppConfig), Builder (Course)
- **Exception Handling**: Custom exceptions for business rules
- **File I/O**: NIO.2 APIs for file operations and backup management
- **Stream Processing**: Lambda expressions for data filtering and aggregation
- **Immutability**: Immutable value classes (CourseCode)
- **Polymorphism**: Abstract Person class with Student/Instructor implementations

## Java Evolution Timeline

### Key Milestones
- **1995**: Java 1.0 - Initial release with applets
- **1997**: Java 1.1 - Inner classes, JDBC, RMI
- **1998**: Java 1.2 - Collections framework, Swing
- **2000**: Java 1.3 - HotSpot JVM, JNDI
- **2002**: Java 1.4 - Assertions, NIO, XML processing
- **2004**: Java 5.0 - Generics, annotations, enums, autoboxing
- **2006**: Java 6 - Scripting support, JDBC 4.0
- **2011**: Java 7 - Try-with-resources, NIO.2, diamond operator
- **2014**: Java 8 - Lambda expressions, Streams API, Date/Time API
- **2017**: Java 9 - Modules, JShell
- **2018**: Java 10 - Local variable type inference
- **2018**: Java 11 - LTS, HTTP client, var keyword
- **2021**: Java 17 - LTS, sealed classes, pattern matching
- **2023**: Java 21 - LTS, virtual threads, pattern matching for switch

## Java Platform Comparison

| Platform | Purpose | Target | Key Features |
|----------|---------|--------|--------------|
| **Java SE** | Standard Edition | Desktop/Server | Core APIs, JVM, development tools |
| **Java ME** | Micro Edition | Mobile/Embedded | Limited API, small footprint |
| **Java EE** | Enterprise Edition | Enterprise Apps | Web services, messaging, persistence |

## Java Architecture

### Components
- **JDK (Java Development Kit)**: Complete development environment
  - Compiler (javac)
  - Runtime (JRE)
  - Development tools (debugger, profiler)
  - Documentation generator (javadoc)

- **JRE (Java Runtime Environment)**: Runtime environment for applications
  - Java Virtual Machine (JVM)
  - Core libraries and APIs
  - Deployment technologies

- **JVM (Java Virtual Machine)**: Executes Java bytecode
  - Bytecode interpreter
  - Memory management
  - Garbage collection
  - Platform independence

### Interaction Flow
1. **Development**: Write Java source code (.java files)
2. **Compilation**: javac compiles source to bytecode (.class files)
3. **Execution**: JVM interprets bytecode on target platform
4. **Runtime**: JRE provides necessary libraries and runtime support

## Installation & Setup

### Prerequisites
- Java 11 or higher
- Windows 10/11
- Command Prompt or PowerShell

### Java Installation on Windows

1. **Download JDK**
   - Visit [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
   - Download JDK 11 or higher for Windows x64

2. **Install JDK**
   - Run the installer as administrator
   - Follow installation wizard
   - Note the installation path (default: `C:\Program Files\Java\jdk-XX`)

3. **Set Environment Variables**
   - Open System Properties → Advanced → Environment Variables
   - Add `JAVA_HOME`: `C:\Program Files\Java\jdk-XX`
   - Add to `PATH`: `%JAVA_HOME%\bin`

4. **Verify Installation**
   ```cmd
   java -version
   javac -version
   ```

### Eclipse IDE Setup

1. **Download Eclipse**
   - Visit [Eclipse Downloads](https://www.eclipse.org/downloads/)
   - Download Eclipse IDE for Java Developers

2. **Create New Project**
   - File → New → Java Project
   - Project name: `CCRM`
   - Use default JRE

3. **Import Source Code**
   - Right-click project → Import → File System
   - Select project directory
   - Import all source files

4. **Run Configuration**
   - Right-click `Main.java` → Run As → Java Application
   - Or create Run Configuration with main class: `Main`

## How to Run

### Command Line
```cmd
# Compile all Java files
javac -cp . edu/ccrm/domain/*.java edu/ccrm/service/*.java edu/ccrm/io/*.java edu/ccrm/util/*.java edu/ccrm/config/*.java edu/ccrm/exception/*.java edu/ccrm/cli/*.java Main.java

# Run the application
java Main
```

### Eclipse IDE
1. Import the project into Eclipse
2. Right-click on `Main.java`
3. Select "Run As" → "Java Application"

## Project Structure

```
CCRM/
├── edu/ccrm/
│   ├── cli/           # Command-line interface
│   │   └── CCRMApplication.java
│   ├── config/        # Configuration management
│   │   └── AppConfig.java
│   ├── domain/        # Domain models
│   │   ├── Person.java
│   │   ├── Student.java
│   │   ├── Instructor.java
│   │   ├── Course.java
│   │   ├── Enrollment.java
│   │   ├── Semester.java
│   │   ├── Grade.java
│   │   ├── CourseCode.java
│   │   ├── Persistable.java
│   │   └── Searchable.java
│   ├── exception/     # Custom exceptions
│   │   ├── DuplicateEnrollmentException.java
│   │   └── MaxCreditLimitExceededException.java
│   ├── io/           # File I/O operations
│   │   └── ImportExportService.java
│   ├── service/      # Business logic
│   │   └── StudentService.java
│   └── util/         # Utility classes
│       └── ReportGenerator.java
├── data/             # Sample data files
│   ├── students.csv
│   └── courses.csv
├── Main.java         # Application entry point
└── README.md
```

## Syllabus Topic Mapping

| Topic | Implementation | File/Class/Method |
|-------|----------------|-------------------|
| **OOP Principles** | | |
| Encapsulation | Private fields + getters/setters | All domain classes |
| Inheritance | Person → Student/Instructor | `Person.java`, `Student.java`, `Instructor.java` |
| Abstraction | Abstract Person class | `Person.java` |
| Polymorphism | Method overriding, virtual invocation | `Person.getDisplayInfo()` |
| **Design Patterns** | | |
| Singleton | AppConfig class | `AppConfig.java` |
| Builder | Course creation | `Course.Builder` |
| **Exception Handling** | | |
| Custom Exceptions | Business rule exceptions | `DuplicateEnrollmentException.java`, `MaxCreditLimitExceededException.java` |
| Try-Catch-Finally | File operations | `ImportExportService.java` |
| **Collections & Streams** | | |
| Stream API | Data processing | `StudentService.java`, `ReportGenerator.java` |
| Lambda Expressions | Functional programming | Stream operations throughout |
| **File I/O** | | |
| NIO.2 | File operations | `ImportExportService.java` |
| Path/Files APIs | File management | `ImportExportService.java` |
| **Date/Time API** | | |
| LocalDate | Date handling | `Person.java`, `Enrollment.java` |
| **Generics** | | |
| Generic Interfaces | Type safety | `Searchable.java` |
| **Enums** | | |
| Enum with fields | Grade/Semester | `Grade.java`, `Semester.java` |

## Enabling Assertions

To enable assertions for testing invariants:

```cmd
# Compile with assertions
javac -ea Main.java

# Run with assertions enabled
java -ea Main
```

### Sample Assertion Usage
```java
// In domain classes
assert id != null : "ID cannot be null";
assert credits > 0 : "Credits must be positive";
```

## Sample Commands

### Basic Operations
1. **Add Student**: Enter student details through menu
2. **Add Course**: Create course with builder pattern
3. **Enroll Student**: Enroll with credit limit validation
4. **Record Grade**: Enter marks and compute GPA
5. **Generate Transcript**: View student academic record

### File Operations
1. **Import Data**: Load from CSV files
2. **Export Data**: Save current data to CSV
3. **Create Backup**: Timestamped backup with recursive size calculation

### Reports
1. **Top Students**: GPA-based ranking
2. **GPA Distribution**: Statistical analysis
3. **Enrollment Stats**: Course enrollment metrics

## Demo Flow

1. **Start Application**: Load configuration and sample data
2. **Student Management**: Add students, view profiles
3. **Course Management**: Create courses, search by criteria
4. **Enrollment**: Enroll students with business rule validation
5. **Grading**: Record marks, view transcripts
6. **File Operations**: Export data, create backups
7. **Reports**: Generate statistical reports using Streams
8. **Platform Info**: Display Java SE vs ME vs EE comparison

## Technical Highlights

- **Comprehensive OOP**: All four pillars demonstrated
- **Modern Java**: Streams, lambdas, NIO.2, Date/Time API
- **Design Patterns**: Singleton and Builder patterns
- **Exception Handling**: Custom exceptions with business logic
- **File Management**: NIO.2 with recursive operations
- **Data Processing**: Stream API for reports and statistics
- **Type Safety**: Generics and immutable value classes

## Screenshots

*Note: Screenshots should be added showing:*
- JDK installation verification (`java -version`)
- Eclipse project setup and run configuration
- Program running with sample operations
- File exports and backup directory structure

## Acknowledgments

This project demonstrates comprehensive Java SE programming concepts as part of academic coursework. All code is original work implementing standard Java patterns and best practices.

## License

This project is created for educational purposes as part of academic coursework.



