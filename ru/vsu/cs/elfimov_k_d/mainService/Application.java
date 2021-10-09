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
        List<Player> players = inputOutput.readNamesFromFileAndFormPlayerList();
        GameService gameService = new GameService();
        Game game = gameService.createNewGame(players);
        gameService.play(game);
        String gameAsString = game.getGameComments().toString();
        inputOutput.writeIn(gameAsString);
        System.out.println(gameAsString);
    }
}
