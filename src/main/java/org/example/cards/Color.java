package org.example.cards;

public enum Color {
    RED ("\u001B[31m"),
    BLACK ("\u001B[30m"),
    NOCOLOR("");

    public static final String RESET_CODE = "\u001B[0m";
    private final String code;

    Color(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
