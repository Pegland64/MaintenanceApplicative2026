package trivia.decks;

import java.util.LinkedList;

public abstract class Deck {
    public String name;
    private final LinkedList<String> questions = new LinkedList<>();

    public Deck(String name){
        this.name = name;
        for (int i = 0; i < 50; i++) {
            this.questions.add(this.name + " Question " + i);
        }
    }

    public void ask(){
        System.out.println(questions.removeFirst());
    }
}