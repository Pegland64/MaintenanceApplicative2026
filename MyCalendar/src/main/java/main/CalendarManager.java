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
        Event e = new Event(type, new TitreEvenement(title), proprietaire, dateDebut, dureeMinutes, lieu, participants, frequenceJours);
        events.add(e);
    }

    public ResultatSuppression supprimerParId(EventId id) {
        int avant = events.size();
        events.removeIf(e -> e.id().equals(id));
        int apres = events.size();

        // pas de ternaire: on indexe une liste sur le résultat boolean
        List<ResultatSuppression> choix = List.of(ResultatSuppression.introuvable(), ResultatSuppression.supprime());
        return choix.get(apres < avant ? 1 : 0);
    }

    public List<Event> eventsDansPeriode(LocalDateTime debut, LocalDateTime fin) {
        DateHeureEvenement d = new DateHeureEvenement(debut);
        DateHeureEvenement f = new DateHeureEvenement(fin);

        // pas de if: filtre stream
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
