package ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices;

import ru.vsu.cs.elfimov_k_d.model.Card;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.ICardsService;
import ru.vsu.cs.elfimov_k_d.model.Combo;
import ru.vsu.cs.elfimov_k_d.model.ComboEnum;

import java.util.List;

public class FullHouseService implements ICardsService {
    @Override
    public Combo isCombination(List<Card> hand, List<Card> table) {
        return null;
    }
}
