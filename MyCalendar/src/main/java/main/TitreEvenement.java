package main;

import java.util.Objects;

public final class TitreEvenement {
    private final String value;

    public TitreEvenement(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("TitreEvenement invalide");
        }
        this.value = value;
    }

    public String asString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TitreEvenement other)) return false;
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
