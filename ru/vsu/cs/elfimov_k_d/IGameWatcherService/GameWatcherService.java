package ru.vsu.cs.elfimov_k_d.IGameWatcherService;

import ru.vsu.cs.elfimov_k_d.model.Card;
import ru.vsu.cs.elfimov_k_d.model.Combo;
import ru.vsu.cs.elfimov_k_d.model.Game;
import ru.vsu.cs.elfimov_k_d.model.Player;

import java.util.List;
import java.util.Map;

public class GameWatcherService implements IGameWatcherService {

    @Override
    public void nextStateComment(Game game) {
        StringBuilder comments = game.getGameComments();
        comments.append("\n");
        comments.append("Начинается ").append(game.getGameState().getName());
        comments.append("\n");
        comments.append("\n");
    }

    @Override
    public void combinationComment(Game game, Player player) {
        StringBuilder comments = game.getGameComments();

        Map<Player, Combo> playersCombo = game.getPlayersCombo();
        comments.append("Старшая комбинация у игрока ").append(player.getName()).append(": ").append(playersCombo.get(player).getComboEnum().getName());
        comments.append("\n");
    }

    @Override
    public void cardsOnTheTableComment(Game game) {
        StringBuilder comments = game.getGameComments();
        comments.append("Карты на столе: ");
        comments.append("\n");
        List<Card> cardsOnTable = game.getCardsOnTheTable();
        for (Card card : cardsOnTable) {
            comments.append(card.getInfoOfTheCard());
            comments.append("\n");
        }
    }

    @Override
    public void playerWithCardsComment(Game game, Player player) {
        StringBuilder comments = game.getGameComments();
        comments.append("\n");
        comments.append("Карты у игрока ").append(player.getName()).append(": ");
        comments.append("\n");
        for (Card card : game.getPlayersWithCards().get(player)) {
            comments.append(card.getInfoOfTheCard());
            comments.append("\n");
        }
    }

    @Override
    public void foldComment(Game game, Player player) {
        StringBuilder comments = game.getGameComments();
        comments.append("О, нет! Игрок ").append(player.getName()).append(" выбывает из игры");
        Map<Player, Integer> bets = game.getBets();
        if (bets.containsKey(player)) {
            for (Map.Entry<Player, Integer> bet : bets.entrySet()) {
                if (bet.getKey().equals(player)) {
                    comments.append(", потеряв ").append(bet.getValue());
                    break;
                }
            }
        }
        comments.append("\n");
    }

    @Override
    public void betComment(Game game, Player player) {
        StringBuilder comments = game.getGameComments();
        Map<Player, Integer> bets = game.getBets();
        for (Map.Entry<Player, Integer> bet : bets.entrySet()) {
            if (bet.getKey().equals(player)) {
                comments.append("Общая ставка игрока ").append(player.getName()).append(" составила: ").append(bet.getValue());
                break;
            }
        }
        comments.append("\n");
    }

    @Override
    public void winnersComment(Game game) {
        StringBuilder comments = game.getGameComments();
        comments.append("\n");
        for (Map.Entry<Player, Integer> winnerAndPrize : game.getWinnersAndTheirPrize().entrySet()) {
            comments.append("Игрок ").append(winnerAndPrize.getKey().getName()).append(" выигрывает свой куш в размере ").append(winnerAndPrize.getValue()).append(". Поздравляем победителя!");
            comments.append("\n");
        }
        comments.append("\n");
        comments.append("Наша партия подошла к концу! Всем спасибо!");
    }
}
