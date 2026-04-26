package main;

import java.util.Objects;
import java.util.UUID;

public final class EventId {
    private final UUID value;

    private EventId(UUID value) {
        this.value = Objects.requireNonNull(value, "EventId");
    }

    public static EventId newId() {
        return new EventId(UUID.randomUUID());
    }

    public static EventId of(UUID value) {
        return new EventId(value);
    }

    public UUID asUuid() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EventId other)) return false;
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
