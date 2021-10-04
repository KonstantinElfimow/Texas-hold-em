package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.model.*;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;

import java.util.*;

public class KareService implements ICardsService {
    @Override
    public Combo isCombination(List<Card> hand, List<Card> table) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(hand);
        allCards.addAll(table);

        Map<Value, List<TypeOfSuit>> valueAndTypeOfSuitMap = new HashMap<>();
        Card kicker;

        for (Card card : allCards) {
            if (!valueAndTypeOfSuitMap.containsKey(card.getValue())) {
                List<TypeOfSuit> singletonList = new ArrayList<>();
                singletonList.add(card.getTypeOfSuit());
                valueAndTypeOfSuitMap.put(card.getValue(), singletonList);
            } else {
                for (Map.Entry<Value, List<TypeOfSuit>> entry : valueAndTypeOfSuitMap.entrySet()) {
                    if (entry.getKey().equals(card.getValue())) {
                        List<TypeOfSuit> suits = valueAndTypeOfSuitMap.get(card.getValue());
                        suits.add(card.getTypeOfSuit());
                        entry.setValue(suits);
                    }
                }
            }
        }

        hand.sort((c1, c2) -> c2.getValue().getKickerScore() - c1.getValue().getKickerScore());
        for (Card handCard : hand) {
            kicker = handCard;
            List<TypeOfSuit> suits = valueAndTypeOfSuitMap.get(handCard.getValue());
            if (suits.size() == 4) {
                return new Combo(ComboEnum.KARE, allCards, kicker);
            }
        }
        return null;
    }
}
