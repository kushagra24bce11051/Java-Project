package edu.ccrm.domain;

/**
 * Enum for Grade with grade points
 * Demonstrates enum with constructors and fields
 */
public enum Grade {
    S("S", 10.0, "Excellent"),
    A("A", 9.0, "Very Good"),
    B("B", 8.0, "Good"),
    C("C", 7.0, "Average"),
    D("D", 6.0, "Below Average"),
    F("F", 0.0, "Fail");

    private final String letter;
    private final double points;
    private final String description;

    Grade(String letter, double points, String description) {
        this.letter = letter;
        this.points = points;
        this.description = description;
    }

    public String getLetter() {
        return letter;
    }

    public double getPoints() {
        return points;
    }

    public String getDescription() {
        return description;
    }

    public static Grade fromPercentage(double percentage) {
        if (percentage >= 90) return S;
        if (percentage >= 80) return A;
        if (percentage >= 70) return B;
        if (percentage >= 60) return C;
        if (percentage >= 50) return D;
        return F;
    }

    @Override
    public String toString() {
        return String.format("%s (%.1f points) - %s", letter, points, description);
    }
}

