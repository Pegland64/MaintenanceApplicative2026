package main;

import java.util.Objects;

public final class DureeEvenement {
    private final int minutes;

    public DureeEvenement(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("DureeEvenement invalide");
        }
        this.minutes = minutes;
    }

    public int enMinutes() {
        return minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DureeEvenement other)) return false;
        return minutes == other.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minutes);
    }

    @Override
    public String toString() {
        return minutes + " min";
    }
}
