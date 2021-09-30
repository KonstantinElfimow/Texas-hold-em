package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.model.Card;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;
import ru.vsu.cs.elfimov_k_d.model.Combo;
import ru.vsu.cs.elfimov_k_d.model.ComboEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FleshService implements ICardsService {
    @Override
    public Combo isCombination(List<Card> hand, List<Card> table) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(hand);
        allCards.addAll(table);
        allCards.sort((c1, c2) -> c1.getTypeOfSuit().getId() - c2.getTypeOfSuit().getId());

        int count = 0;
        Card card1 = allCards.get(0);
        Card kicker = card1;

        List<Card> listToCheckValid = new ArrayList<>(Collections.singleton(card1));
        for (int i = 1; i < allCards.size(); i++) {
            Card card2 = allCards.get(i);
            int dif = card1.getTypeOfSuit().getId() - card2.getTypeOfSuit().getId();
            if (dif == 0) {
                count++;
                if (kicker == null) {
                    kicker = card2;
                }
                listToCheckValid.add(card2);
                if (count == 4) {
                    if (listToCheckValid.contains(hand.get(0)) || listToCheckValid.contains(hand.get(1))) {
                        return new Combo(ComboEnum.FLESH, allCards, kicker);
                    } else {
                        listToCheckValid.clear();
                        kicker = null;
                        count = 0;
                    }
                }
            } else {
                listToCheckValid.clear();
                listToCheckValid.add(card2);
                kicker = null;
                count = 0;
            }
            card1 = card2;
        }
        return null;
    }
}
