package main;

import java.util.Objects;

public final class Event {
    private final EventId id;
    private final TypeEvenement type;
    private final TitreEvenement title;
    private final Proprietaire proprietaire;
    private final Creneau creneau;
    private final Lieu lieu;
    private final Participants participants;
    private final FrequenceJours frequence;
    private final TypeEvent comportement;

    public Event(
            EventId id,
            TypeEvenement type,
            TitreEvenement title,
            Proprietaire proprietaire,
            Creneau creneau,
            Lieu lieu,
            Participants participants,
            FrequenceJours frequence
    ) {
        this.id = Objects.requireNonNull(id, "Event.id");
        this.type = Objects.requireNonNull(type, "Event.type");
        this.title = Objects.requireNonNull(title, "Event.title");
        this.proprietaire = Objects.requireNonNull(proprietaire, "Event.proprietaire");
        this.creneau = Objects.requireNonNull(creneau, "Event.creneau");
        this.lieu = Objects.requireNonNull(lieu, "Event.lieu");
        this.participants = Objects.requireNonNull(participants, "Event.participants");
        this.frequence = Objects.requireNonNull(frequence, "Event.frequence");
        this.comportement = TypeEventFactory.from(this.type);
    }

    public static Event withNewId(
            TypeEvenement type,
            TitreEvenement title,
            Proprietaire proprietaire,
            Creneau creneau,
            Lieu lieu,
            Participants participants,
            FrequenceJours frequence
    ) {
        return new Event(
                EventId.newId(),
                type,
                title,
                proprietaire,
                creneau,
                lieu,
                participants,
                frequence
        );
    }

    public EventId id() {
        return id;
    }

    public TypeEvenement type() {
        return type;
    }

    public TitreEvenement title() {
        return title;
    }

    public Proprietaire proprietaire() {
        return proprietaire;
    }

    public Creneau creneau() {
        return creneau;
    }

    public Lieu lieu() {
        return lieu;
    }

    public Participants participants() {
        return participants;
    }

    public FrequenceJours frequence() {
        return frequence;
    }

    public String description() {
        return comportement.descriptionDe(this);
    }

    public boolean estDansPeriode(DateHeureEvenement debut, DateHeureEvenement fin) {
        return comportement.estDansPeriode(this, debut, fin);
    }

    public boolean conflitAvec(Event autre) {
        return comportement.conflit(this, autre);
    }
}
