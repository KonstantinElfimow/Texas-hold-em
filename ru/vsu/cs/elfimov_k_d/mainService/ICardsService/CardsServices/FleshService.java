package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.model.*;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;

import java.util.*;

public class FleshService implements ICardsService {
    @Override
    public Combo isCombination(List<Card> hand, List<Card> table) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(hand);
        allCards.addAll(table);

        Map<TypeOfSuit, List<Value>> typeOfSuitAndValueListMap = new HashMap<>();
        Card kicker = null;

        for (Card card : allCards) {
            if (!typeOfSuitAndValueListMap.containsKey(card.getTypeOfSuit())) {
                List<Value> singletonList = new ArrayList<>();
                singletonList.add(card.getValue());
                typeOfSuitAndValueListMap.put(card.getTypeOfSuit(), singletonList);
            } else {
                for (Map.Entry<TypeOfSuit, List<Value>> entry : typeOfSuitAndValueListMap.entrySet()) {
                    if (entry.getKey().equals(card.getTypeOfSuit())) {
                        List<Value> values =  typeOfSuitAndValueListMap.get(card.getTypeOfSuit());
                        values.add(card.getValue());
                        entry.setValue(values);
                    }
                }
            }
        }

        for (Map.Entry<TypeOfSuit, List<Value>> entry : typeOfSuitAndValueListMap.entrySet()) {
            List<Value> valueList = entry.getValue();
            if (valueList.size() >= 5) {
                boolean flag = false;
                TypeOfSuit typeOfSuit = entry.getKey();
                for (Card card : hand) {
                    if (card.getTypeOfSuit().equals(typeOfSuit)) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    for (Value valueOfCard : valueList) {
                        if (kicker == null || valueOfCard.getKickerScore() - kicker.getValue().getKickerScore() > 0) {
                            kicker = new Card(valueOfCard, typeOfSuit);
                        }
                    }
                    return new Combo(ComboEnum.FLESH, allCards, kicker);
                }
            }
        }
        return null;
    }
}
