package ru.vsu.cs.elfimov_k_d.model;

public enum GameState {
    PREFLOP(0, "Пре-флоп"),FLOP(3, "Флоп"), TURN(1, "Тёрн"), RIVER(1, "Ривер"), ENDING(0, "Подвод результатов");

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
