package main;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

class CalendarManagerTest {

    @Test
    void eventsDansPeriode_retient_un_evenement_non_periodique_dans_intervalle_bornes_incluses() {
        CalendarManager calendar = new CalendarManager();

        LocalDateTime debut = LocalDateTime.of(2026, 1, 10, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 1, 10, 12, 0);

        calendar.ajouterEvent("RDV_PERSONNEL", "PileDebut", "Alice", debut, 30, "", "", 0);
        calendar.ajouterEvent("RDV_PERSONNEL", "PileFin", "Alice", fin, 30, "", "", 0);

        List<Event> result = calendar.eventsDansPeriode(debut, fin);

        assertEquals(2, result.size());
        assertEquals("PileDebut", result.get(0).title().asString());
        assertEquals("PileFin", result.get(1).title().asString());
    }

    @Test
    void eventsDansPeriode_exclut_un_evenement_non_periodique_hors_intervalle() {
        CalendarManager calendar = new CalendarManager();

        LocalDateTime debut = LocalDateTime.of(2026, 1, 10, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 1, 10, 12, 0);

        calendar.ajouterEvent("RDV_PERSONNEL", "Avant", "Alice", debut.minusMinutes(1), 30, "", "", 0);
        calendar.ajouterEvent("RDV_PERSONNEL", "Apres", "Alice", fin.plusMinutes(1), 30, "", "", 0);

        List<Event> result = calendar.eventsDansPeriode(debut, fin);

        assertTrue(result.isEmpty());
    }

    @Test
    void eventsDansPeriode_periodique_retourne_levent_si_une_occurrence_tombe_dans_intervalle() {
        CalendarManager calendar = new CalendarManager();

        LocalDateTime debut = LocalDateTime.of(2026, 1, 10, 0, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 1, 10, 23, 59);

        calendar.ajouterEvent(
                "PERIODIQUE",
                "Sport",
                "Alice",
                LocalDateTime.of(2026, 1, 1, 10, 0),
                0,
                "",
                "",
                3
        );

        List<Event> result = calendar.eventsDansPeriode(debut, fin);

        assertEquals(1, result.size());
        assertEquals("PERIODIQUE", result.get(0).type().asString());
        assertEquals("Sport", result.get(0).title().asString());
    }

    @Test
    void eventsDansPeriode_periodique_retourne_vide_si_aucune_occurrence_dans_intervalle() {
        CalendarManager calendar = new CalendarManager();

        LocalDateTime debut = LocalDateTime.of(2026, 1, 2, 0, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 1, 2, 23, 59);

        calendar.ajouterEvent(
                "PERIODIQUE",
                "Sport",
                "Alice",
                LocalDateTime.of(2026, 1, 1, 10, 0),
                0,
                "",
                "",
                7
        );

        List<Event> result = calendar.eventsDansPeriode(debut, fin);

        assertTrue(result.isEmpty());
    }

    @Test
    void conflit_retourne_faux_si_un_des_events_est_periodique() {
        CalendarManager calendar = new CalendarManager();

        Event periodique = Event.withNewId(
                TypeEvenement.of("PERIODIQUE"),
                new TitreEvenement("P"),
                new Proprietaire("Alice"),
                new Creneau(new DateHeureEvenement(LocalDateTime.of(2026, 1, 10, 10, 0)), new DureeEvenement(0)),
                new Lieu(""),
                Participants.fromCsv(""),
                new FrequenceJours(3)
        );

        Event rdv = Event.withNewId(
                TypeEvenement.of("RDV_PERSONNEL"),
                new TitreEvenement("R"),
                new Proprietaire("Bob"),
                new Creneau(new DateHeureEvenement(LocalDateTime.of(2026, 1, 10, 10, 0)), new DureeEvenement(60)),
                new Lieu(""),
                Participants.fromCsv(""),
                new FrequenceJours(0)
        );

        assertFalse(calendar.conflit(periodique, rdv));
        assertFalse(calendar.conflit(rdv, periodique));
    }

    @Test
    void conflit_retourne_vrai_si_chevauchement() {
        CalendarManager calendar = new CalendarManager();

        Event e1 = Event.withNewId(
                TypeEvenement.of("RDV_PERSONNEL"),
                new TitreEvenement("E1"),
                new Proprietaire("Alice"),
                new Creneau(new DateHeureEvenement(LocalDateTime.of(2026, 1, 10, 10, 0)), new DureeEvenement(60)),
                new Lieu(""),
                Participants.fromCsv(""),
                new FrequenceJours(0)
        );

        Event e2 = Event.withNewId(
                TypeEvenement.of("RDV_PERSONNEL"),
                new TitreEvenement("E2"),
                new Proprietaire("Bob"),
                new Creneau(new DateHeureEvenement(LocalDateTime.of(2026, 1, 10, 10, 30)), new DureeEvenement(30)),
                new Lieu(""),
                Participants.fromCsv(""),
                new FrequenceJours(0)
        );

        assertTrue(calendar.conflit(e1, e2));
    }

    @Test
    void conflit_retourne_faux_si_pas_chevauchement_bord_a_bord() {
        CalendarManager calendar = new CalendarManager();

        Event e1 = Event.withNewId(
                TypeEvenement.of("RDV_PERSONNEL"),
                new TitreEvenement("E1"),
                new Proprietaire("Alice"),
                new Creneau(new DateHeureEvenement(LocalDateTime.of(2026, 1, 10, 10, 0)), new DureeEvenement(30)),
                new Lieu(""),
                Participants.fromCsv(""),
                new FrequenceJours(0)
        );

        Event e2 = Event.withNewId(
                TypeEvenement.of("RDV_PERSONNEL"),
                new TitreEvenement("E2"),
                new Proprietaire("Bob"),
                new Creneau(new DateHeureEvenement(LocalDateTime.of(2026, 1, 10, 10, 30)), new DureeEvenement(30)),
                new Lieu(""),
                Participants.fromCsv(""),
                new FrequenceJours(0)
        );

        assertFalse(calendar.conflit(e1, e2));
    }

    @Test
    void description_rdv_personnel() {
        Event rdv = Event.withNewId(
                TypeEvenement.of("RDV_PERSONNEL"),
                new TitreEvenement("Dentiste"),
                new Proprietaire("Alice"),
                new Creneau(new DateHeureEvenement(LocalDateTime.of(2026, 1, 10, 10, 0)), new DureeEvenement(30)),
                new Lieu(""),
                Participants.fromCsv(""),
                new FrequenceJours(0)
        );

        String desc = rdv.description();

        assertTrue(desc.startsWith("RDV : Dentiste à "));
        assertTrue(desc.contains("2026-01-10T10:00"));
    }

    @Test
    void description_reunion() {
        Event reunion = Event.withNewId(
                TypeEvenement.of("REUNION"),
                new TitreEvenement("Sprint"),
                new Proprietaire("Alice"),
                new Creneau(new DateHeureEvenement(LocalDateTime.of(2026, 1, 10, 11, 0)), new DureeEvenement(60)),
                new Lieu("Salle A"),
                Participants.fromCsv("Alice, Bob"),
                new FrequenceJours(0)
        );

        String desc = reunion.description();

        assertEquals("Réunion : Sprint à Salle A avec Alice, Bob", desc);
    }

    @Test
    void description_periodique() {
        Event periodique = Event.withNewId(
                TypeEvenement.of("PERIODIQUE"),
                new TitreEvenement("Sport"),
                new Proprietaire("Alice"),
                new Creneau(new DateHeureEvenement(LocalDateTime.of(2026, 1, 1, 10, 0)), new DureeEvenement(0)),
                new Lieu(""),
                Participants.fromCsv(""),
                new FrequenceJours(3)
        );

        String desc = periodique.description();

        assertEquals("Événement périodique : Sport tous les 3 jours", desc);
    }

    @Test
    void description_type_inconnu_leve_une_exception() {
        assertThrows(NullPointerException.class, () -> Event.withNewId(
                TypeEvenement.of("AUTRE"),
                new TitreEvenement("X"),
                new Proprietaire("Alice"),
                new Creneau(new DateHeureEvenement(LocalDateTime.of(2026, 1, 1, 10, 0)), new DureeEvenement(0)),
                new Lieu(""),
                Participants.fromCsv(""),
                new FrequenceJours(0)
        ));
    }
}
