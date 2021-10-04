package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.model.Card;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;
import ru.vsu.cs.elfimov_k_d.model.Combo;
import ru.vsu.cs.elfimov_k_d.model.ComboEnum;

import java.util.*;

public class KickerService implements ICardsService {
    @Override
    public Combo isCombination(List<Card> hand, List<Card> table) {
        Card kicker = hand.get(0);
        for (Card handCard : hand) {
            if (handCard.getValue().getKickerScore() - kicker.getValue().getKickerScore() > 0) {
                kicker = handCard;
            }
        }
        return new Combo(ComboEnum.KICKER, new ArrayList<>(Collections.singletonList(kicker)), kicker);
    }
}
