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

        int l = 1;
        int lCountTheSameCards = 0;
        int r = allCards.size() - 2;
        int rCountTheSameCards = 0;

        Card cardL1 = allCards.get(l--);
        Card kicker = cardL1;
        boolean flag1 = true;
        Card cardR1 = allCards.get(r++);
        boolean flag2 = true;

        List<Card> lListToCheckValid = new ArrayList<>(Collections.singleton(cardL1));
        List<Card> rListToCheckValid = new ArrayList<>(Collections.singleton(cardR1));
        Comparator<Card> comparator = (c1, c2) -> c1.getValue().getKickerScore() - c2.getValue().getKickerScore();
        for (int i = 0; i < allCards.size(); i++) {
            if (lCountTheSameCards < 1) {
                Card cardL2 = allCards.get(l);
                if (comparator.compare(cardL1, cardL2) == 0) {
                    kicker = cardL1;
                    lCountTheSameCards++;
                    if (lCountTheSameCards == 1) {
                        flag1 = true;
                    }
                    lListToCheckValid.add(cardL2);
                } else {
                    lListToCheckValid.clear();
                    lListToCheckValid.add(cardL2);
                    cardL1 = cardL2;
                    kicker = null;
                    lCountTheSameCards = 0;
                }
                l++;
            }
            if (flag1 && lCountTheSameCards == 1) {
                if (!(lListToCheckValid.contains(hand.get(0)) || lListToCheckValid.contains(hand.get(1)))) {
                    kicker = null;
                    lListToCheckValid.clear();
                    lCountTheSameCards = 0;
                }
                flag1 = false;
            }

            if (rCountTheSameCards < 1) {
                Card cardR2 = allCards.get(r);
                if (comparator.compare(cardR1, cardR2) == 0) {
                    rCountTheSameCards++;
                    if (rCountTheSameCards == 1) {
                        flag2 = true;
                    }
                    rListToCheckValid.add(cardR2);
                } else {
                    rListToCheckValid.clear();
                    rListToCheckValid.add(cardR2);
                    cardR1 = cardR2;
                    rCountTheSameCards = 0;
                }
                r--;
            }
            if (flag2 && rCountTheSameCards == 1) {
                if (!(rListToCheckValid.contains(hand.get(0)) || rListToCheckValid.contains(hand.get(1)))) {
                    rListToCheckValid.clear();
                    rCountTheSameCards = 0;
                }
                flag2 = false;
            }

            if (lCountTheSameCards == 1 && rCountTheSameCards == 1) {
                return new Combo(ComboEnum.TWOCOUPLE, allCards, kicker);
            }
        }
        return null;
    }
}
