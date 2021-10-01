package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.model.Card;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;
import ru.vsu.cs.elfimov_k_d.model.Combo;
import ru.vsu.cs.elfimov_k_d.model.ComboEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TwoCoupleService implements ICardsService {
    @Override
    public Combo isCombination(List<Card> hand, List<Card> table) {
        if (hand.get(0).getValue().equals(hand.get(1).getValue())) {
            return null;
        }

        List<Card> allCards = new ArrayList<>();
        allCards.addAll(hand);
        allCards.addAll(table);
        allCards.sort((c1, c2) -> c2.getValue().getKickerScore() - c1.getValue().getKickerScore());

        Card card1 = allCards.get(0);
        Card kicker = card1;
        boolean flag;

        boolean couple1 = false;
        boolean couple2 = false;

        List<Card> listToCheckValid = new ArrayList<>(Collections.singleton(card1));

        Comparator<Card> comparator = (c1, c2) -> c1.getValue().getKickerScore() - c2.getValue().getKickerScore();
        for (int i = 1; i < allCards.size(); i++) {
            Card card2 = allCards.get(i);
            if (comparator.compare(card1, card2) == 0) {
                if (!couple1) {
                    kicker = card1;
                }
                flag = true;
            } else {
                listToCheckValid.clear();
                if (!couple1) {
                    kicker = card2;
                }
                flag = false;
            }
            listToCheckValid.add(card2);
            card1 = card2;

            if (flag) {
                if (listToCheckValid.contains(hand.get(0)) || listToCheckValid.contains(hand.get(1))) {
                    if (couple1) {
                        couple2 = true;
                    } else {
                        couple1 = true;
                    }
                }
                listToCheckValid.clear();
            }

            if (couple1 && couple2) {
                return new Combo(ComboEnum.TWOCOUPLE, allCards, kicker);
            }
        }
        return null;
    }
}
