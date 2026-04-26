package main;

public final class RdvPersonnelType implements TypeEvent {

    @Override
    public String descriptionDe(Event e) {
        return "RDV : " + e.title().asString() + " à " + e.creneau().debut().asLocalDateTime();
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
