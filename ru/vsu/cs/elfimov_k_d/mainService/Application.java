package ru.vsu.cs.elfimov_k_d.mainService;

import ru.vsu.cs.elfimov_k_d.contoller.InputArgsController;
import ru.vsu.cs.elfimov_k_d.model.Game;
import ru.vsu.cs.elfimov_k_d.model.Player;

import java.util.List;
import java.util.Locale;

public class Application {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);
        InputArgsController inputOutput = new InputArgsController(args);
        GameService gameService = new GameService();
        Game game = gameService.createNewGame();
        List<Player> players = inputOutput.readNamesFromFileAndFormPlayerList();
        gameService.play(game, players);
        String gameAsString = game.getGameComments().toString();
        inputOutput.writeIn(gameAsString);
        System.out.println(gameAsString);
    }
}
