package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.model.Card;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;
import ru.vsu.cs.elfimov_k_d.model.Combo;
import ru.vsu.cs.elfimov_k_d.model.ComboEnum;

import java.util.*;

public class KickerService implements ICardsService {
    @Override
    public Combo isCombination(List<Card> hand, List<Card> table) {
        Card kicker;
        Comparator<Card> comparator = (player1Card, player2Card) -> player1Card.getValue().getKickerScore() - player2Card.getValue().getKickerScore();
        int compare = comparator.compare(hand.get(0), hand.get(1));
        if (compare >= 0) {
            kicker = hand.get(0);
        } else {
            kicker = hand.get(1);
        }
        return new Combo(ComboEnum.KICKER, new ArrayList<>(Collections.singletonList(kicker)), kicker);
    }
}
