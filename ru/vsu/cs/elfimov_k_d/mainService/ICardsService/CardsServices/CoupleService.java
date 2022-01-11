package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.model.*;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;

import java.util.*;

public class CoupleService implements ICardsService {
    @Override
    public Combo isCombination(List<Card> hand, List<Card> table) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(hand);
        allCards.addAll(table);

        allCards.sort((c1, c2) -> c2.getValue().getKickerScore() - c1.getValue().getKickerScore());
        Card kicker = null;
        for (Card card : allCards) {
            if (kicker == null) {
                kicker = card;
                continue;
            }
            if (kicker.getValue() == card.getValue() && (hand.contains(kicker) || hand.contains(card))) {
                return new Combo(ComboEnum.COUPLE, allCards, kicker);
            } else {
                kicker = card;
            }
        }
        return null;
    }
}
