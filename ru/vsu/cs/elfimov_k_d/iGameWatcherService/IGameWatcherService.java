package ru.vsu.cs.elfimov_k_d.iGameWatcherService;

import ru.vsu.cs.elfimov_k_d.model.Game;
import ru.vsu.cs.elfimov_k_d.model.Player;

public interface IGameWatcherService {
    void cardsOnTheTableComment(Game game);
    void playerWithCardsComment(Game game, Player player);
    void foldComment(Game game, Player player);
    void betComment(Game game, Player player);
    void winnersComment(Game game);
    void nextStateComment(Game game);
    void combinationComment(Game game, Player player);
}
