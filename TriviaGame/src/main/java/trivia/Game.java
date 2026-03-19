package trivia;

import trivia.decks.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game implements IGame {
   ArrayList<Player> players = new ArrayList<>();

   int currentPlayer = 0;
   ArrayList<Deck> decks = new ArrayList<>();

   public Game() {
      decks.add(new PopDeck());
      decks.add(new ScienceDeck());
      decks.add(new SportsDeck());
      decks.add(new RockDeck());
   }

   public boolean add(String playerName) {
      players.add(new Player(playerName, decks));

      System.out.println("They are player number " + players.size());
      return true;
   }

   public void roll(int roll) {
      players.get(currentPlayer).play(roll);
   }

   public boolean handleCorrectAnswer() {
      boolean res = players.get(currentPlayer).correctAnswer();
      nextPlayer();
      return !res;
   }

   public boolean wrongAnswer() {
      players.get(currentPlayer).wrongAnswer();
      nextPlayer();
      return true;
   }

   private void nextPlayer(){
      currentPlayer = (currentPlayer + 1) % players.size();
   }
}