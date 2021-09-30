package ru.vsu.cs.elfimov_k_d.model;

public enum GameState {
    FLOP(3, "Флоп"), TURN(1, "Тёрн"), RIVER(1, "Ривер");

    private int cardCount;
    private String name;

    GameState (int cardCount, String name) {
        this.cardCount = cardCount;
        this.name = name;
    }

    public int getCardCount() {
        return cardCount;
    }
    public String getName() {
        return name;
    }
}
