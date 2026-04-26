package main;

public final class ReunionType implements TypeEvent {

    @Override
    public String descriptionDe(Event e) {
        return "Réunion : " + e.title().asString() + " à " + e.lieu().asString() + " avec " + e.participants().asCsv();
    }

    @Override
    public boolean estDansPeriode(Event e, DateHeureEvenement debut, DateHeureEvenement fin) {
        return e.creneau().estDans(debut, fin);
    }

    @Override
    public boolean conflit(Event e1, Event e2) {
        return e1.creneau().chevauche(e2.creneau());
    }
}
