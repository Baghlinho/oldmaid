package org.example.cards;

public class DeckGenerator {
    public Deck generate() {
        Deck deck = new Deck();
        for (Type type: Type.values()) {
            if (type == Type.JOKER) continue;
            for (Suite suite : Suite.values()) {
                if (suite == Suite.NOSUITE) continue;
                deck.pushCard(createCard(type, suite));
            }
        }
        deck.pushCard(createCard(Type.JOKER, Suite.NOSUITE));
        deck.shuffle();
        return deck;
    }

    private Card createCard(Type type, Suite suite) {
        return new Card(type, suite);
    }
}
