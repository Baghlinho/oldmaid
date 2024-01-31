package org.example.cards;

public class Card {
    private final Type type;
    private final Suite suite;
    public Card(Type type, Suite suite) {
        this.type = type;
        this.suite = suite;
        checkValidity();
    }

    private void checkValidity() {
        if(type == null || suite == null)
            throw new IllegalArgumentException("Card type and suite can't be null");
        if(type != Type.JOKER && suite == Suite.NOSUITE)
            throw new IllegalArgumentException("Only the Joker has no suite");
        if(type == Type.JOKER && suite != Suite.NOSUITE)
            throw new IllegalArgumentException("The Joker can't have a suite");
    }

    public Type getType() {
        return type;
    }

    public Color getColor() {
        return suite.getColor();
    }

    @Override
    public String toString() {
        return suite.getColor().getCode()
                + type.getSymbol()
                + suite.getSymbol()
                + Color.RESET_CODE;
    }
}
