package main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public List<Event> eventsDansPeriode(LocalDateTime debut, LocalDateTime fin) {
        DateHeureEvenement d = new DateHeureEvenement(debut);
        DateHeureEvenement f = new DateHeureEvenement(fin);

        List<Event> result = new ArrayList<>();
        for (Event e : events) {
            if (e.type.estPeriodique()) {
                LocalDateTime temp = e.creneau.debut().asLocalDateTime();
                while (temp.isBefore(fin)) {
                    if (!temp.isBefore(debut)) {
                        result.add(e);
                        break;
                    }
                    temp = temp.plusDays(e.frequence.enJours());
                }
            } else if (e.creneau.estDans(d, f)) {
                result.add(e);
            }
        }
        return result;
    }

    public boolean conflit(Event e1, Event e2) {
        if (e1.type.estPeriodique() || e2.type.estPeriodique()) {
            return false; // simplification actuelle
        }
        return e1.creneau.chevauche(e2.creneau);
    }

    public void afficherEvenements() {
        for (Event e : events) {
            System.out.println(e.description());
        }
    }
}
