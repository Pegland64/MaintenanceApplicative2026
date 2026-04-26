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

        Event periodique = new Event(
                "PERIODIQUE",
                new TitreEvenement("P"),
                "Alice",
                LocalDateTime.of(2026, 1, 10, 10, 0),
                0,
                "",
                "",
                3
        );

        Event rdv = new Event(
                "RDV_PERSONNEL",
                new TitreEvenement("R"),
                "Bob",
                LocalDateTime.of(2026, 1, 10, 10, 0),
                60,
                "",
                "",
                0
        );

        assertFalse(calendar.conflit(periodique, rdv));
        assertFalse(calendar.conflit(rdv, periodique));
    }

    @Test
    void conflit_retourne_vrai_si_chevauchement() {
        CalendarManager calendar = new CalendarManager();

        Event e1 = new Event(
                "RDV_PERSONNEL",
                new TitreEvenement("E1"),
                "Alice",
                LocalDateTime.of(2026, 1, 10, 10, 0),
                60,
                "",
                "",
                0
        );

        Event e2 = new Event(
                "RDV_PERSONNEL",
                new TitreEvenement("E2"),
                "Bob",
                LocalDateTime.of(2026, 1, 10, 10, 30),
                30,
                "",
                "",
                0
        );

        assertTrue(calendar.conflit(e1, e2));
    }

    @Test
    void conflit_retourne_faux_si_pas_chevauchement_bord_a_bord() {
        CalendarManager calendar = new CalendarManager();

        Event e1 = new Event(
                "RDV_PERSONNEL",
                new TitreEvenement("E1"),
                "Alice",
                LocalDateTime.of(2026, 1, 10, 10, 0),
                30,
                "",
                "",
                0
        );

        Event e2 = new Event(
                "RDV_PERSONNEL",
                new TitreEvenement("E2"),
                "Bob",
                LocalDateTime.of(2026, 1, 10, 10, 30),
                30,
                "",
                "",
                0
        );

        assertFalse(calendar.conflit(e1, e2));
    }

    @Test
    void description_rdv_personnel() {
        Event rdv = new Event(
                "RDV_PERSONNEL",
                new TitreEvenement("Dentiste"),
                "Alice",
                LocalDateTime.of(2026, 1, 10, 10, 0),
                30,
                "",
                "",
                0
        );

        String desc = rdv.description();

        assertTrue(desc.startsWith("RDV : Dentiste à "));
        assertTrue(desc.contains("2026-01-10T10:00"));
    }

    @Test
    void description_reunion() {
        Event reunion = new Event(
                "REUNION",
                new TitreEvenement("Sprint"),
                "Alice",
                LocalDateTime.of(2026, 1, 10, 11, 0),
                60,
                "Salle A",
                "Alice, Bob",
                0
        );

        String desc = reunion.description();

        assertEquals("Réunion : Sprint à Salle A avec Alice, Bob", desc);
    }

    @Test
    void description_periodique() {
        Event periodique = new Event(
                "PERIODIQUE",
                new TitreEvenement("Sport"),
                "Alice",
                LocalDateTime.of(2026, 1, 1, 10, 0),
                0,
                "",
                "",
                3
        );

        String desc = periodique.description();

        assertEquals("Événement périodique : Sport tous les 3 jours", desc);
    }

    @Test
    void description_type_inconnu_leve_une_exception() {
        assertThrows(NullPointerException.class, () -> new Event(
                "AUTRE",
                new TitreEvenement("X"),
                "Alice",
                LocalDateTime.of(2026, 1, 1, 10, 0),
                0,
                "",
                "",
                0
        ));
    }
}
