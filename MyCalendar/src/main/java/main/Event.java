package main;

import java.time.LocalDateTime;

public class Event {
    public EventId id;
    public TypeEvenement type;
    public TitreEvenement title;
    public Proprietaire proprietaire;

    public Creneau creneau;

    public Lieu lieu; // REUNION
    public Participants participants; // REUNION
    public FrequenceJours frequence; // PERIODIQUE

    // Constructeur "bordure" conservé pour limiter la casse (Main/tests)
    public Event(String type, TitreEvenement title, String proprietaire, LocalDateTime dateDebut, int dureeMinutes,
                 String lieu, String participants, int frequenceJours) {
        this.id = EventId.newId();
        this.type = TypeEvenement.of(type);
        this.title = title;
        this.proprietaire = new Proprietaire(proprietaire);

        this.creneau = new Creneau(new DateHeureEvenement(dateDebut), new DureeEvenement(dureeMinutes));

        this.lieu = (lieu == null) ? Lieu.vide() : new Lieu(lieu);
        this.participants = Participants.fromCsv(participants);
        this.frequence = new FrequenceJours(frequenceJours);
    }

    public String description() {
        String desc = "";
        if (type.estRdvPersonnel()) {
            desc = "RDV : " + title.asString() + " à " + creneau.debut().asLocalDateTime().toString();
        } else if (type.estReunion()) {
            desc = "Réunion : " + title.asString() + " à " + lieu.asString() + " avec " + participants.asCsv();
        } else if (type.estPeriodique()) {
            desc = "Événement périodique : " + title.asString() + " tous les " + frequence.enJours() + " jours";
        }
        return desc;
    }
}
