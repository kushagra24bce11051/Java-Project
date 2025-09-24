package edu.ccrm.domain;

/**
 * Enum for Semester with constructors and fields
 * Demonstrates enum usage
 */
public enum Semester {
    SPRING("Spring", 1),
    SUMMER("Summer", 2),
    FALL("Fall", 3);

    private final String displayName;
    private final int order;

    Semester(String displayName, int order) {
        this.displayName = displayName;
        this.order = order;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

