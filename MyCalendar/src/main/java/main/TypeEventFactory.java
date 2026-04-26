package main;

import java.util.Map;
import java.util.Objects;

public final class TypeEventFactory {
    private static final Map<String, TypeEvent> REGISTRY = Map.of(
            "RDV_PERSONNEL", new RdvPersonnelType(),
            "REUNION", new ReunionType(),
            "PERIODIQUE", new PeriodiqueType()
    );

    private TypeEventFactory() {
    }

    public static TypeEvent from(TypeEvenement type) {
        TypeEvent comportement = REGISTRY.get(type.asString());
        return Objects.requireNonNull(comportement, "TypeEvenement inconnu: " + type.asString());
    }
}
