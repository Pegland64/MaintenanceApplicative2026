package main;

import java.util.Objects;

public final class TypeEvenement {
    private final String value;

    private TypeEvenement(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("TypeEvenement invalide");
        }
        this.value = value.trim();
    }

    public static TypeEvenement of(String value) {
        return new TypeEvenement(value);
    }

    public boolean estPeriodique() {
        return Objects.equals(value, "PERIODIQUE");
    }

    public boolean estRdvPersonnel() {
        return Objects.equals(value, "RDV_PERSONNEL");
    }

    public boolean estReunion() {
        return Objects.equals(value, "REUNION");
    }

    public String asString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TypeEvenement other)) return false;
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
