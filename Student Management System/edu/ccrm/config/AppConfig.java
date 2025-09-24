package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Singleton configuration class
 * Demonstrates Singleton design pattern
 */
public class AppConfig {
    private static AppConfig instance;
    private final Path dataDirectory;
    private final Path backupDirectory;
    private final int maxCreditsPerSemester;

    private AppConfig() {
        this.dataDirectory = Paths.get("data");
        this.backupDirectory = Paths.get("backups");
        this.maxCreditsPerSemester = 18;
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public Path getDataDirectory() {
        return dataDirectory;
    }

    public Path getBackupDirectory() {
        return backupDirectory;
    }

    public int getMaxCreditsPerSemester() {
        return maxCreditsPerSemester;
    }

    public void printPlatformInfo() {
        System.out.println("\n=== Java Platform Information ===");
        System.out.println("Java SE (Standard Edition):");
        System.out.println("- Core Java platform for desktop and server applications");
        System.out.println("- Includes Java API, JVM, and development tools");
        System.out.println("- Used for: Desktop apps, web services, enterprise applications");
        
        System.out.println("\nJava ME (Micro Edition):");
        System.out.println("- Subset of Java SE for mobile and embedded devices");
        System.out.println("- Limited API and smaller footprint");
        System.out.println("- Used for: Mobile phones, IoT devices, embedded systems");
        
        System.out.println("\nJava EE (Enterprise Edition):");
        System.out.println("- Extension of Java SE for enterprise applications");
        System.out.println("- Additional APIs for web services, messaging, persistence");
        System.out.println("- Used for: Large-scale enterprise applications, web services");
        
        System.out.println("\nJava Architecture:");
        System.out.println("- JDK (Java Development Kit): Development tools + JRE");
        System.out.println("- JRE (Java Runtime Environment): Runtime libraries + JVM");
        System.out.println("- JVM (Java Virtual Machine): Executes Java bytecode");
    }
}



