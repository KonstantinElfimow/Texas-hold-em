package ru.vsu.cs.elfimov_k_d.mainService.ICardsService;

import ru.vsu.cs.elfimov_k_d.model.Card;
import ru.vsu.cs.elfimov_k_d.model.Combo;

import java.util.List;

public interface ICardsService {
    Combo isCombination(List<Card> hand, List<Card> table);
}
