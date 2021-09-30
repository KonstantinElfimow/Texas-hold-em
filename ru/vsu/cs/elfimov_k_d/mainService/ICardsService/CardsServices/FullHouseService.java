package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.model.Card;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;
import ru.vsu.cs.elfimov_k_d.model.Combo;
import ru.vsu.cs.elfimov_k_d.model.ComboEnum;

import java.util.ArrayList;
import java.util.List;

public class FullHouseService implements ICardsService {
    @Override
    public Combo isCombination(List<Card> hand, List<Card> table) {
        if (hand.get(0).getValue().equals(hand.get(1).getValue())) {
            return null;
        }

        List<Card> allCards = new ArrayList<>();
        allCards.addAll(hand);
        allCards.addAll(table);
        allCards.sort((c1, c2) -> c2.getValue().getKickerScore() - c1.getValue().getKickerScore());

        int countComboSet = 0;
        int countComboCouple = 0;
        Card card1 = allCards.get(0);
        Card kicker = null;
        for (int i = 1; i < allCards.size(); i++) {
            Card card2 = allCards.get(i);
            if (card1.getValue().equals(card2.getValue())) {
                countComboCouple++;
                if (countComboCouple == 3) {
                    kicker = card1;
                    countComboCouple = 0;
                    countComboSet = 3;
                }
                if (countComboCouple == 2 && countComboSet == 3) {
                    return new Combo(ComboEnum.FULLHOUSE, allCards, kicker);
                }
            }
        }
        return null;
    }
}
