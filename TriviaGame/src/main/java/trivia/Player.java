package trivia;

import trivia.decks.*;

import java.util.ArrayList;

public class Player {
    private final String name;
    private int place = 1;
    private int purse = 0;
    private boolean penalty = false;
    ArrayList<Deck> decks;

    public Player(String name, ArrayList<Deck> decks){
        System.out.println(name + " was added");
        this.name = name;
        this.decks = decks;
    }

    public void play(int roll){
        System.out.println(this.name + " is the current player");
        System.out.println("They have rolled a " + roll);

        if(this.penalty){
            if(roll % 2 != 0) {
                System.out.println(this.name + " is getting out of the penalty box");
                this.penalty = false;
            }else{
                System.out.println(this.name + " is not getting out of the penalty box");
                return;
            }
        }

        this.place = (this.place - 1 + roll) % 12 + 1;
        Deck deck = decks.get((this.place - 1) % 4);
        System.out.println(this.name
                + "'s new location is "
                + this.place);
        System.out.println("The category is " + deck.name);
        deck.ask();
    }

    public boolean correctAnswer(){
        boolean res = false;
        if (!this.penalty) {
            this.purse++;
            res = didPlayerWin();

            System.out.println("Answer was correct!!!!");
            System.out.println(this.name
                    + " now has "
                    + this.purse
                    + " Gold Coins.");

        }
        return res;
    }

    public void wrongAnswer(){
        System.out.println("Question was incorrectly answered");
        System.out.println(this.name + " was sent to the penalty box");
        this.penalty = true;
    }

    private boolean didPlayerWin() {
        return this.purse == 6;
    }
}