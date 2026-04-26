package main;

import java.time.LocalDateTime;
import java.util.Objects;

public final class DateHeureEvenement {
    private final LocalDateTime value;

    public DateHeureEvenement(LocalDateTime value) {
        this.value = Objects.requireNonNull(value, "DateHeureEvenement");
    }

    public LocalDateTime asLocalDateTime() {
        return value;
    }

    public boolean estAvant(DateHeureEvenement other) {
        return value.isBefore(other.value);
    }

    public boolean estApres(DateHeureEvenement other) {
        return value.isAfter(other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DateHeureEvenement other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
