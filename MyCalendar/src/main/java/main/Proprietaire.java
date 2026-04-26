package main;

import java.util.Objects;

public final class Proprietaire {
    private final String value;

    public Proprietaire(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Proprietaire invalide");
        }
        this.value = value.trim();
    }

    public String asString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Proprietaire other)) return false;
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
