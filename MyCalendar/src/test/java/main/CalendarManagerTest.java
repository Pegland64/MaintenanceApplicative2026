package main;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

class CalendarManagerTest {

    @Test
    void eventsDansPeriode_retourne_les_evenements_dans_l_intervalle() {
        CalendarManager calendar = new CalendarManager();

        LocalDateTime debut = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 1, 31, 23, 59);

        calendar.ajouterEvent(
                "RDV_PERSONNEL",
                "Dentiste",
                "Alice",
                LocalDateTime.of(2026, 1, 10, 10, 0),
                30,
                "",
                "",
                0
        );

        calendar.ajouterEvent(
                "RDV_PERSONNEL",
                "HorsPeriode",
                "Alice",
                LocalDateTime.of(2026, 2, 1, 10, 0),
                30,
                "",
                "",
                0
        );

        List<Event> result = calendar.eventsDansPeriode(debut, fin);

        assertEquals(1, result.size());
        assertEquals("Dentiste", result.get(0).title);
    }
}
