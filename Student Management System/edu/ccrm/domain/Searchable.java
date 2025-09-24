package edu.ccrm.domain;

/**
 * Generic interface for searchable objects
 * Demonstrates generic interfaces
 */
public interface Searchable<T> {
    boolean matches(String searchTerm);
    T getSearchableContent();
}



