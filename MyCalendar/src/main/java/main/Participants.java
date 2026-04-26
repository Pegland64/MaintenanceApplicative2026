package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Participants {
    private final List<String> values;

    private Participants(List<String> values) {
        this.values = List.copyOf(values);
    }

    public static Participants vide() {
        return new Participants(List.of());
    }

    public static Participants fromCsv(String csv) {
        if (csv == null || csv.trim().isEmpty()) return vide();

        String[] parts = csv.split(",");
        List<String> out = new ArrayList<>();
        for (String p : parts) {
            String trimmed = p.trim();
            if (!trimmed.isEmpty()) out.add(trimmed);
        }
        return new Participants(out);
    }

    public List<String> asList() {
        return Collections.unmodifiableList(values);
    }

    public String asCsv() {
        return String.join(", ", values);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Participants other)) return false;
        return values.equals(other.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public String toString() {
        return asCsv();
    }
}
