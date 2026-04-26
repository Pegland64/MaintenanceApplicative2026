package main;

import java.util.Objects;

public final class Creneau {
    private final DateHeureEvenement debut;
    private final DureeEvenement duree;

    public Creneau(DateHeureEvenement debut, DureeEvenement duree) {
        this.debut = Objects.requireNonNull(debut, "Creneau.debut");
        this.duree = Objects.requireNonNull(duree, "Creneau.duree");
    }

    public DateHeureEvenement debut() {
        return debut;
    }

    public DureeEvenement duree() {
        return duree;
    }

    public DateHeureEvenement fin() {
        return new DateHeureEvenement(debut.asLocalDateTime().plusMinutes(duree.enMinutes()));
    }

    public boolean estDans(DateHeureEvenement debutPeriode, DateHeureEvenement finPeriode) {
        return !debut.asLocalDateTime().isBefore(debutPeriode.asLocalDateTime())
                && !debut.asLocalDateTime().isAfter(finPeriode.asLocalDateTime());
    }

    public boolean chevauche(Creneau autre) {
        return debut.asLocalDateTime().isBefore(autre.fin().asLocalDateTime())
                && fin().asLocalDateTime().isAfter(autre.debut.asLocalDateTime());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Creneau other)) return false;
        return debut.equals(other.debut) && duree.equals(other.duree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(debut, duree);
    }
}
