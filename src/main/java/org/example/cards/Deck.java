package org.example.cards;

import java.util.Collections;
import java.util.Stack;

public class Deck {
    private final Stack<Card> cards = new Stack<>();

    public void pushCard(Card card) {
        cards.push(card);
    }

    public Card popCard() {
        return cards.pop();
    }

    public boolean isEmpty() {
        return cards.empty();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
