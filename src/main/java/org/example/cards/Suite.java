package org.example.cards;

public enum Suite {
    HEART ("❤", Color.RED),
    SPADE ("♠", Color.BLACK),
    CLUB ("♣", Color.BLACK),
    DIAMOND ("♦", Color.RED),
    NOSUITE("", Color.NOCOLOR);

    private final String symbol;
    private final Color color;

    Suite(String symbol, Color color) {
        this.symbol = symbol;
        this.color = color;
    }

    public String getSymbol() {
        return symbol;
    }

    public Color getColor() {
        return color;
    }
}
