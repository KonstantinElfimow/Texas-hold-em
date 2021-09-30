package ru.vsu.cs.elfimov_k_d.model;

public enum Value {
    DEUCE("2", 0),      //Двойка
    TREY("3", 1),       //Тройка
    FOUR("4", 2),       //Четвёрка
    FIVE("5",3),        //Пятёрка
    SIX("6",4),         //Шестёрка
    SEVEN("7",5),       //Семёрка
    EIGHT("8",6),       //Восьмёрка
    NINE("9",7),        //Девятка
    TEN("10",8),        //Десятка
    JACK("J",9),        //Валет
    QUEEN("Q",10),      //Дама
    KING("K",11),       //Король
    ACE("A",12);        //Туз

    private String symbol;
    private int kickerScore;

    Value(String symbol, int kickerScore) {
        this.symbol = symbol;
        this.kickerScore = kickerScore;
    }
    public String getSymbol() {
        return symbol;
    }
    public int getKickerScore() {
        return kickerScore;
    }
}
