package ru.vsu.cs.elfimov_k_d.iGameWatcherService;

import ru.vsu.cs.elfimov_k_d.model.*;

import java.util.List;
import java.util.Map;

public class GameWatcherService implements IGameWatcherService {

    @Override
    public void lineComment(Game game) {
        StringBuilder comments = game.getGameComments();
        comments.append("\n");
        comments.append("----------------------------");
        comments.append("\n");
    }

    @Override
    public void nextStateComment(Game game) {
        StringBuilder comments = game.getGameComments();
        comments.append("\n");
        comments.append("Начинается: ").append(game.getGameState().getName());
        comments.append("\n");
    }

    @Override
    public void combinationComment(Game game, Player player) {
        StringBuilder comments = game.getGameComments();
        Combo playerCombo = game.getPlayersCombo().get(player);
        comments.append("\n");
        comments.append("Старшая комбинация у игрока ").append(player.getName()).append(": ").append(playerCombo.getComboEnum().getName());
        if (!playerCombo.getComboEnum().equals(ComboEnum.KICKER)) {
            comments.append("\n");
            comments.append("Карты: ");
            comments.append("\n");
            for (Card card : playerCombo.getWinCombo()) {
                comments.append(card.toString());
            }
        }
        comments.append("\n");
        comments.append("Старшая карта: ").append(playerCombo.getKicker().toString());
        comments.append("\n");
    }

    @Override
    public void cardsOnTheTableComment(Game game) {
        StringBuilder comments = game.getGameComments();
        comments.append("\n");
        comments.append("Карты на столе: ");
        comments.append("\n");
        List<Card> cardsOnTable = game.getCardsOnTheTable();
        for (Card card : cardsOnTable) {
            comments.append(card.toString());
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
            comments.append(card.toString());
            comments.append("\n");
        }
    }

    @Override
    public void foldComment(Game game, Player player) {
        StringBuilder comments = game.getGameComments();
        comments.append("Игрок ").append(player.getName()).append(" выбывает из игры");
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
                comments.append("Игрок сделал ставку. Общая ставка игрока ").append(player.getName()).append(" составила: ").append(bet.getValue());
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
