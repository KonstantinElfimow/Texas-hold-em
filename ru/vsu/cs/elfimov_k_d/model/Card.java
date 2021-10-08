package ru.vsu.cs.elfimov_k_d.model;

public class Card {
    private Value value;
    private TypeOfSuit typeOfSuit;

    public Card(Value value, TypeOfSuit typeOfSuit) {
        this.value = value;
        this.typeOfSuit = typeOfSuit;
    }

    public TypeOfSuit getTypeOfSuit() {
        return typeOfSuit;
    }
    public Value getValue() {
        return value;
    }
    public String getInfoOfTheCard() {
        return " " + value.getSymbol() + " " + typeOfSuit.getSymbol() + " ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        return getValue() == card.getValue() && getTypeOfSuit() == card.getTypeOfSuit();
    }
    @Override
    public int hashCode() {
        return (value.getKickerScore() + 10) * (typeOfSuit.getId() + 1);
    }
}
