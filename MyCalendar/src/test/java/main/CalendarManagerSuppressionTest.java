package main;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class CalendarManagerSuppressionTest {

    @Test
    void supprimerParId_supprime_levent_quand_id_existe() {
        CalendarManager calendar = new CalendarManager();

        calendar.ajouterEvent("RDV_PERSONNEL", "Dentiste", "Alice",
                LocalDateTime.of(2026, 1, 10, 10, 0), 30,
                "", "", 0);

        EventId id = calendar.events.get(0).id();

        ResultatSuppression res = calendar.supprimerParId(id);

        assertTrue(res.estSupprime());
        assertTrue(calendar.events.isEmpty());
    }

    @Test
    void supprimerParId_retourne_introuvable_quand_id_absent() {
        CalendarManager calendar = new CalendarManager();

        calendar.ajouterEvent("RDV_PERSONNEL", "Dentiste", "Alice",
                LocalDateTime.of(2026, 1, 10, 10, 0), 30,
                "", "", 0);

        ResultatSuppression res = calendar.supprimerParId(EventId.newId());

        assertFalse(res.estSupprime());
        assertEquals(1, calendar.events.size());
    }
}
