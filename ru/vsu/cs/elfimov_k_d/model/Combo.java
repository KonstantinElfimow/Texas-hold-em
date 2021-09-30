package ru.vsu.cs.elfimov_k_d.model;

import java.util.List;

public class Combo {
    private ComboEnum comboEnum;
    private List<Card> winCombo;
    private Card kicker;

    public Combo (ComboEnum comboEnum, List<Card> winCombo, Card kicker) {
        this.comboEnum = comboEnum;
        this.winCombo = winCombo;
        this.kicker = kicker;
    }

    public ComboEnum getComboEnum() {
        return comboEnum;
    }
    public List<Card> getWinCombo() {
        return winCombo;
    }
    public Card getKicker() {
        return kicker;
    }
}
