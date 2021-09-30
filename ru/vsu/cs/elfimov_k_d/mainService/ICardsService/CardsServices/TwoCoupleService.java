package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.model.Card;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;
import ru.vsu.cs.elfimov_k_d.model.Combo;
import ru.vsu.cs.elfimov_k_d.model.ComboEnum;

import java.util.ArrayList;
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

        int count = 0;
        Card card1 = allCards.get(0);
        Card kicker = card1;
        for (int i = 1; i < allCards.size(); i++) {
            Card card2 = allCards.get(i);
            if (card1.getValue().equals(card2.getValue())) {
                if (hand.contains(card1) || hand.contains(card2)) {
                    count++;
                    if (count == 2) {
                        return new Combo(ComboEnum.TWOCOUPLE, allCards, kicker);
                    }
                    if (++i != allCards.size()) {
                        card1 = allCards.get(i);
                    }
                }
            } else {
                kicker = card2;
                card1 = card2;
            }
        }
        return null;
    }
}
