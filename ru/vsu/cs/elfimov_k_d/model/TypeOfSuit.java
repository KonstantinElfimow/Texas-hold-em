package ru.vsu.cs.elfimov_k_d.model;

public enum TypeOfSuit {
    HEARTS("♥", 0),     //Червы
    SPADES("♣", 1),     //Трефы
    DIAMONDS("♦", 2),   //Бубны
    CLUBS("♠", 3);      //Пики

    private String symbol;
    private int id;

    TypeOfSuit(String symbol, int id) {
        this.symbol = symbol;
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }
    public int getId() {
        return id;
    }
}