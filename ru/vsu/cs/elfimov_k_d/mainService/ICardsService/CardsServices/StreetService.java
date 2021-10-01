package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;
import ru.vsu.cs.elfimov_k_d.model.Card;
import ru.vsu.cs.elfimov_k_d.model.Combo;
import ru.vsu.cs.elfimov_k_d.model.ComboEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StreetService implements ICardsService {
    @Override
    public Combo isCombination(List<Card> hand, List<Card> table) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(hand);
        allCards.addAll(table);
        allCards.sort((c1, c2) -> c2.getValue().getKickerScore() - c1.getValue().getKickerScore());

        Comparator<Card> comparator = ((c1, c2) -> {
            if (c1.getValue().getKickerScore() - c2.getValue().getKickerScore() == 1) {
                return 1;
            }
            return 0;
        });

        int count = 0;
        Card card1 = allCards.get(0);
        Card kicker = card1;

        List<Card> listToCheckValid = new ArrayList<>(Collections.singleton(card1));
        for (int i = 1; i < allCards.size(); i++) {
            Card card2 = allCards.get(i);
            if (comparator.compare(card1, card2) > 0) {
                count++;
                if (kicker == null) {
                    kicker = card2;
                }
                listToCheckValid.add(card2);
                if (count == 4) {
                    if (listToCheckValid.contains(hand.get(0)) || listToCheckValid.contains(hand.get(1))) {
                        return new Combo(ComboEnum.STREET, allCards, kicker);
                    } else {
                        listToCheckValid.clear();
                        kicker = null;
                        count = 0;
                    }
                }
            } else {
                listToCheckValid.clear();
                listToCheckValid.add(card2);
                kicker = card2;
                count = 0;
            }
            card1 = card2;
        }
        return null;
    }
}
