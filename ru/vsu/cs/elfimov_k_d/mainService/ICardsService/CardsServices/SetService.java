package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.model.Card;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;
import ru.vsu.cs.elfimov_k_d.model.Combo;
import ru.vsu.cs.elfimov_k_d.model.ComboEnum;

import java.util.ArrayList;
import java.util.List;

public class SetService implements ICardsService {
    @Override
    public Combo isCombination(List<Card> hand, List<Card> table) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(hand);
        allCards.addAll(table);
        allCards.sort((c1, c2) -> c2.getValue().getKickerScore() - c1.getValue().getKickerScore());

        int count = 0;
        Card card1 = allCards.get(0);
        Card kicker = null;
        for (int i = 1; i < allCards.size(); i++) {
            Card card2 = allCards.get(i);
            if (card1.getValue().equals(card2.getValue())) {
                count++;
                if (kicker == null) {
                    kicker = card1;
                }
                if (count == 2) {
                    if (!(hand.contains(card1) || hand.contains(card2))) {
                        card1 = card2;
                        continue;
                    }
                    return new Combo(ComboEnum.SET, allCards, kicker);
                }
            } else {
                count = 0;
                card1 = card2;
                kicker = null;
            }
        }
        return null;
    }
}