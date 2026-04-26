package main;

import java.time.LocalDateTime;

public final class PeriodiqueType implements TypeEvent {

    @Override
    public String descriptionDe(Event e) {
        return "Événement périodique : " + e.title().asString() + " tous les " + e.frequence().enJours() + " jours";
    }

    @Override
    public boolean estDansPeriode(Event e, DateHeureEvenement debut, DateHeureEvenement fin) {
        LocalDateTime temp = e.creneau().debut().asLocalDateTime();
        LocalDateTime finPeriode = fin.asLocalDateTime();
        LocalDateTime debutPeriode = debut.asLocalDateTime();

        while (temp.isBefore(finPeriode)) {
            if (!temp.isBefore(debutPeriode)) {
                return true;
            }
            temp = temp.plusDays(e.frequence().enJours());
        }
        return false;
    }

    @Override
    public boolean conflit(Event e1, Event e2) {
        return false; // règle actuelle conservée (pas de conflit si périodique)
    }
}
