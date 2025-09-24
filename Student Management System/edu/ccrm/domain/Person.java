package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Abstract base class for Person entities
 * Demonstrates abstraction and inheritance
 */
public abstract class Person {
    protected String id;
    protected String fullName;
    protected String email;
    protected LocalDate dateCreated;
    protected boolean active;

    public Person(String id, String fullName, String email) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.fullName = Objects.requireNonNull(fullName, "Full name cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.dateCreated = LocalDate.now();
        this.active = true;
    }

    // Abstract method - must be implemented by subclasses
    public abstract String getDisplayInfo();

    // Getters and setters (Encapsulation)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDate dateCreated) { this.dateCreated = dateCreated; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Person{id='%s', fullName='%s', email='%s', active=%s}", 
                           id, fullName, email, active);
    }
}

