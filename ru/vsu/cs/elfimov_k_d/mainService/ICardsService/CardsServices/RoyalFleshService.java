package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.model.Card;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;
import ru.vsu.cs.elfimov_k_d.model.Combo;
import ru.vsu.cs.elfimov_k_d.model.ComboEnum;
import ru.vsu.cs.elfimov_k_d.model.Value;

import java.util.ArrayList;
import java.util.List;

public class RoyalFleshService implements ICardsService {
    @Override
    public Combo isCombination(List<Card> hand, List<Card> table) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(hand);
        allCards.addAll(table);
        allCards.sort((c1, c2) -> {
            int dif = c1.getTypeOfSuit().getId() - c2.getTypeOfSuit().getId();
            if (dif == 0) {
                return c2.getValue().getKickerScore() - c1.getValue().getKickerScore();
            } else {
                return dif;
            }
        });

        int count = 0;
        Card card1 = allCards.get(0);
        Card kicker = null;
        for (int i = 1; i < allCards.size(); i++) {
            Card card2 = allCards.get(i);
            int difId = card1.getTypeOfSuit().getId() - card2.getTypeOfSuit().getId();
            int difKickerCount = card1.getValue().getKickerScore() - card2.getValue().getKickerScore();
            if (difId == 0 && difKickerCount == 1) {
                if (kicker == null) {
                    kicker = card1;
                }
                count++;
                if (count == 1 && !card1.getValue().equals(Value.ACE)) {
                    kicker = null;
                    card1 = card2;
                    count = 0;
                    continue;
                }
                if (count == 4) {
                    return new Combo(ComboEnum.ROYALFLESH, allCards, kicker);
                }
            } else {
                kicker = null;
                count = 0;
            }
            card1 = card2;
        }
        return null;
    }
}