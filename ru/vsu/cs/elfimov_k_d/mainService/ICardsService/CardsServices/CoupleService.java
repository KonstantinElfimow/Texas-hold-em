package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.model.Card;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;
import ru.vsu.cs.elfimov_k_d.model.Combo;
import ru.vsu.cs.elfimov_k_d.model.ComboEnum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CoupleService implements ICardsService {
    @Override
    public Combo isCombination(List<Card> hand, List<Card> table) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(hand);
        allCards.addAll(table);
        allCards.sort((c1, c2) -> c2.getValue().getKickerScore() - c1.getValue().getKickerScore());

        Comparator<Card> comparator = ((c1, c2) -> c1.getValue().getKickerScore() - c2.getValue().getKickerScore());

        Card card1 = allCards.get(0);
        Card kicker = card1;
        for (int i = 1; i < allCards.size(); i++) {
            Card card2 = allCards.get(i);
            if (comparator.compare(card1, card2) == 0) {
                if (hand.contains(card1) || hand.contains(card2)) {
                    return new Combo(ComboEnum.COUPLE, allCards, kicker);
                }
            } else {
                kicker = card2;
                card1 = card2;
            }
        }
        return null;
    }
}
