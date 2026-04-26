package main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarManager {
    public List<Event> events;

    public CalendarManager() {
        this.events = new ArrayList<>();
    }

    public void ajouterEvent(String type, String title, String proprietaire, LocalDateTime dateDebut, int dureeMinutes,
                             String lieu, String participants, int frequenceJours) {
        Event e = Event.withNewId(
                TypeEvenement.of(type),
                new TitreEvenement(title),
                new Proprietaire(proprietaire),
                new Creneau(new DateHeureEvenement(dateDebut), new DureeEvenement(dureeMinutes)),
                new Lieu(lieu),
                Participants.fromCsv(participants),
                new FrequenceJours(frequenceJours)
        );
        events.add(e);
    }

    public ResultatSuppression supprimerParId(EventId id) {
        int avant = events.size();
        events.removeIf(e -> e.id().equals(id));
        int apres = events.size();

        List<ResultatSuppression> choix = List.of(ResultatSuppression.introuvable(), ResultatSuppression.supprime());
        int index = Math.max(0, Integer.compare(avant, apres));
        return choix.get(index);
    }


    public List<Event> eventsDansPeriode(LocalDateTime debut, LocalDateTime fin) {
        DateHeureEvenement d = new DateHeureEvenement(debut);
        DateHeureEvenement f = new DateHeureEvenement(fin);

        return events.stream()
                .filter(e -> e.estDansPeriode(d, f))
                .collect(Collectors.toList());
    }

    public boolean conflit(Event e1, Event e2) {
        return e1.conflitAvec(e2);
    }

    public void afficherEvenements() {
        for (Event e : events) {
            System.out.println(e.description());
        }
    }
}
