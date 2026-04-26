package main;

public interface TypeEvent {
    String descriptionDe(Event e);

    boolean estDansPeriode(Event e, DateHeureEvenement debut, DateHeureEvenement fin);

    boolean conflit(Event e1, Event e2);
}
