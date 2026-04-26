package main;

import java.time.LocalDateTime;
import java.util.Objects;

public class Event {
    private final EventId id;
    private final TypeEvenement type;
    private final TitreEvenement title;
    private final Proprietaire proprietaire;

    private final Creneau creneau;

    private final Lieu lieu; // REUNION
    private final Participants participants; // REUNION
    private final FrequenceJours frequence; // PERIODIQUE

    private final TypeEvent comportement;

    public Event(String type, TitreEvenement title, String proprietaire, LocalDateTime dateDebut, int dureeMinutes,
                 String lieu, String participants, int frequenceJours) {
        this.id = EventId.newId();
        this.type = TypeEvenement.of(type);
        this.title = Objects.requireNonNull(title, "Event.title");
        this.proprietaire = new Proprietaire(proprietaire);

        this.creneau = new Creneau(new DateHeureEvenement(dateDebut), new DureeEvenement(dureeMinutes));

        this.lieu = (lieu == null) ? Lieu.vide() : new Lieu(lieu);
        this.participants = Participants.fromCsv(participants);
        this.frequence = new FrequenceJours(frequenceJours);

        this.comportement = TypeEventFactory.from(this.type);
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
