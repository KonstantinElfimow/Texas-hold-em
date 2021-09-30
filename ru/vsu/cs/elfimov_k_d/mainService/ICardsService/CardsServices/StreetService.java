package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.model.Card;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;
import ru.vsu.cs.elfimov_k_d.model.Combo;
import ru.vsu.cs.elfimov_k_d.model.ComboEnum;
import ru.vsu.cs.elfimov_k_d.model.Value;

import java.util.ArrayList;
import java.util.List;

public class StreetService implements ICardsService {
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
            int dif = card1.getValue().getKickerScore() - card2.getValue().getKickerScore();
            if (dif == 1) {
                if (kicker == null) {
                    kicker = card1;
                }
                count++;
                if (count == 4) {
                    return new Combo(ComboEnum.STREET, allCards, kicker);
                }
            } else if (dif > 1) {
                kicker = null;
                count = 0;
            }
            card1 = card2;
        }
        return null;
    }
}
