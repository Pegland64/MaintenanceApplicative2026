package main;

import java.util.Objects;

public final class Lieu {
    private final String value;

    public Lieu(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Lieu invalide");
        }
        this.value = value.trim();
    }

    public static Lieu vide() {
        return new Lieu("");
    }

    public boolean estVide() {
        return value.isEmpty();
    }

    public String asString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Lieu other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
