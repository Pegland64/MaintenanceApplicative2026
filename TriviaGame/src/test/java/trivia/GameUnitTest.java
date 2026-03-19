package trivia;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.Test;


class GameUnitTest {

    private static String captureStdout(Runnable action) {
        PrintStream old = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (PrintStream inMemory = new PrintStream(baos)) {
            System.setOut(inMemory);
            action.run();
        } finally {
            System.setOut(old);
        }
        return baos.toString();
    }

    @Test
    void add_addsPlayersAndPrintsPlayerNumber() {
        Game game = new Game();

        String out = captureStdout(() -> {
            game.add("Chet");
            game.add("Pat");
            game.add("Sue");
        });

        assertTrue(out.contains("Chet was added"));
        assertTrue(out.contains("Pat was added"));
        assertTrue(out.contains("Sue was added"));

        assertTrue(out.contains("They are player number 1"));
        assertTrue(out.contains("They are player number 2"));
        assertTrue(out.contains("They are player number 3"));

        assertEquals(3, game.players.size());
    }

    @Test
    void roll_movesPlayerAsExpected_andAsksQuestionFromCorrectDeck() {
        Game game = new Game();
        game.add("Chet");
        game.add("Pat");
        game.add("Sue");

        String out = captureStdout(() -> game.roll(1));

        assertTrue(out.contains("Chet is the current player"));
        assertTrue(out.contains("They have rolled a 1"));
        assertTrue(out.contains("Chet's new location is 2"));

        assertTrue(out.contains("The category is Science"));
        assertTrue(out.contains("Science Question 0"));
    }

    @Test
    void wrongAnswer_sendsPlayerToPenaltyBox_andNextPlayerPlays() {
        Game game = new Game();
        game.add("Chet");
        game.add("Pat");
        game.add("Sue");

        String out = captureStdout(() -> {
            game.roll(2);
            game.wrongAnswer();
            game.roll(1);
        });

        assertTrue(out.contains("Question was incorrectly answered"));
        assertTrue(out.contains("Chet was sent to the penalty box"));
        assertTrue(out.contains("Pat is the current player"));
    }

    @Test
    void penaltyBox_evenRoll_staysInPenaltyBox_doesNotMoveOrAskQuestion() {
        Game game = new Game();
        game.add("Chet");
        game.add("Pat");
        game.add("Sue");

        String out = captureStdout(() -> {
            game.roll(2);
            game.wrongAnswer();

            game.roll(1);
            game.handleCorrectAnswer();

            game.roll(1);
            game.handleCorrectAnswer();

            game.roll(2);
        });

        assertTrue(out.contains("Chet is not getting out of the penalty box"));
        int idx = out.lastIndexOf("Chet is not getting out of the penalty box");
        String tail = out.substring(idx);
        assertFalse(tail.contains("Chet's new location is"));
        assertFalse(tail.contains("Question"));
    }

    @Test
    void penaltyBox_oddRoll_getsOut_movesAndAsksQuestion() {
        Game game = new Game();
        game.add("Chet");
        game.add("Pat");
        game.add("Sue");

        String out = captureStdout(() -> {
            game.roll(2);
            game.wrongAnswer();

            game.roll(1);
            game.handleCorrectAnswer();

            game.roll(1);
            game.handleCorrectAnswer();

            game.roll(1);
        });

        assertTrue(out.contains("Chet is getting out of the penalty box"));
        assertTrue(out.contains("Chet's new location is"));
        assertTrue(out.contains("The category is"));
    }

    @Test
    void handleCorrectAnswer_printsAndAdvancesPlayer_andGameEndsWhenAPlayerReaches6() {
        Game game = new Game();
        game.add("Chet");
        game.add("Pat");
        game.add("Sue");

        Player chet = game.players.get(0);

        String out = captureStdout(() -> {
            for (int i = 0; i < 5; i++) {
                game.currentPlayer = 0;
                boolean keepGoing = game.handleCorrectAnswer();
                assertTrue(keepGoing, "Le jeu ne doit pas finir avant 6 pièces");
            }
            game.currentPlayer = 0;
            boolean keepGoing = game.handleCorrectAnswer();
            assertFalse(keepGoing, "Le jeu doit s'arrêter quand un joueur atteint 6 pièces");
        });

        assertTrue(out.contains("Answer was courgette!!!!"));
        assertTrue(out.contains("Chet now has 6 Gold Coins."));
        assertNotNull(chet);
    }

    @Test
    void rotation_wrapsAroundAfterLastPlayer() {
        Game game = new Game();
        game.add("Chet");
        game.add("Pat");
        game.add("Sue");

        game.currentPlayer = 2;
        game.wrongAnswer();
        assertEquals(0, game.currentPlayer);
    }

    @Test
    void deckQuestionIncrementsPerDeck() {
        Game game = new Game();
        game.add("Chet");
        game.add("Pat");
        game.add("Sue");

        String out = captureStdout(() -> {
            game.currentPlayer = 0;
            game.roll(1);
            game.currentPlayer = 0;
            game.roll(4);
        });

        assertTrue(out.contains("Science Question 0"));
        assertTrue(out.contains("Science Question 1"));
    }
}

