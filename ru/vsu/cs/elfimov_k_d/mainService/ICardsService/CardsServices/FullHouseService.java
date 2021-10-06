package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.model.*;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;

import java.util.*;

public class FullHouseService implements ICardsService {
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

        boolean couple = false;
        boolean set = false;

        hand.sort((c1, c2) -> c1.getValue().getKickerScore() - c2.getValue().getKickerScore());
        Card repeatCard = null;
        for (Card handCard : hand) {
            if (repeatCard == null) {
            } else if (repeatCard.getValue().equals(handCard.getValue())) {
                continue;
            }
            kicker = handCard;
            repeatCard = handCard;
            List<TypeOfSuit> suits = valueAndTypeOfSuitMap.get(handCard.getValue());
            if (suits.size() >= 2) {
                if (suits.size() == 2) {
                    couple = true;
                } else {
                    if (!set) {
                        set = true;
                    } else {
                        couple = true;
                    }
                }
            } else {
                continue;
            }
            if (couple && set) {
                return new Combo(ComboEnum.FULLHOUSE, allCards, kicker);
            }
        }
        return null;
    }
}
