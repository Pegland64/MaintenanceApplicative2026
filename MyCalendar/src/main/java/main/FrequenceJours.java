package main;

import java.util.Objects;

public final class FrequenceJours {
    private final int jours;

    public FrequenceJours(int jours) {
        if (jours < 0) {
            throw new IllegalArgumentException("FrequenceJours invalide");
        }
        this.jours = jours;
    }

    public int enJours() {
        return jours;
    }

    public boolean estActive() {
        return jours > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FrequenceJours other)) return false;
        return jours == other.jours;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jours);
    }

    @Override
    public String toString() {
        return String.valueOf(jours);
    }
}
