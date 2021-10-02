package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.model.Card;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;
import ru.vsu.cs.elfimov_k_d.model.Combo;
import ru.vsu.cs.elfimov_k_d.model.ComboEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

        Card card1 = allCards.get(0);
        Card kicker = card1;

        boolean couple = false;
        boolean set = false;

        List<Card> listToCheckValid = new ArrayList<>(Collections.singleton(card1));

        Comparator<Card> comparator = (c1, c2) -> c1.getValue().getKickerScore() - c2.getValue().getKickerScore();
        for (int i = 1; i < allCards.size(); i++) {
            Card card2 = allCards.get(i);
            if (comparator.compare(card1, card2) == 0) {
                if (!couple && !set) {
                    kicker = card1;
                }
                listToCheckValid.add(card2);
                if (i + 1 != allCards.size()) {
                    i++;
                    Card afterCard2 = allCards.get(i);
                    card1 = afterCard2;
                    if (comparator.compare(card1, afterCard2) == 0) {
                        listToCheckValid.add(afterCard2);
                        if (!(listToCheckValid.contains(hand.get(0)) || listToCheckValid.contains(hand.get(1)))) {
                            listToCheckValid.clear();
                            continue;
                        }
                        if (!set) {
                            set = true;
                        } else {
                            couple = true;
                        }
                    } else {
                        card1 = card2;
                        if (!(listToCheckValid.contains(hand.get(0)) || listToCheckValid.contains(hand.get(1)))) {
                            listToCheckValid.clear();
                            card1 = card2;
                            continue;
                        }
                        couple = true;
                    }
                }
            } else {
                listToCheckValid.clear();
                listToCheckValid.add(card2);
                if (!couple && !set) {
                    kicker = card2;
                }
                card1 = card2;
            }
            if (couple && set) {
                return new Combo(ComboEnum.FULLHOUSE, allCards, kicker);
            }
        }
        return null;
    }
}
