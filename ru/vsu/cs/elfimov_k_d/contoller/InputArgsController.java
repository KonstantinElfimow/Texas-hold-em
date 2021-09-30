package ru.vsu.cs.elfimov_k_d.contoller;

import ru.vsu.cs.elfimov_k_d.model.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class InputArgsController {
    private InputArgs parameters = new InputArgs();

    public InputArgsController(String[] args) {
        parseCmdArgs(args);
    }


    private static class InputArgs {
        String input;           // название входного файла
        String output;          // название выходного файла
        int numberOfPlayers;    // количество игроков
        boolean help;           // если необходимо вывести все команды
    }

    private void parseCmdArgs(String[] args) {
        Locale.setDefault(Locale.ROOT);
        if (args.length == 6) {
            if (args[0].equals("--help")) {
                parameters.help = true;
                System.out.println("Использовать:");
                System.out.println("-i       // ввод первого списка");
                System.out.println("-o        // название файла для вывода");
                System.exit(1);
            }
            if (args[0].equals("-i")) {
                parameters.input = args[1];
            }
            if (args[2].equals("-o")) {
                parameters.output = args[3];
            }
            if (args[4].equals("-players")) {
                parameters.numberOfPlayers = Integer.parseInt(args[5]);
                checkForNumberOfPlayers(parameters.numberOfPlayers);
            }
        } else {
            parameters.help = true;
        }
    }

    private void checkForNumberOfPlayers(int numberOfPlayers) {
        if (numberOfPlayers > 10 || numberOfPlayers < 2) {
            System.out.println("Найдено неверное количество участников");
            System.exit(1);
        }
    }

    public List<Player> readNamesFromFileAndFormPlayerList() {
        try {
            return formPlayerList(readArrayAsNamesFromFile());
        } catch (FileNotFoundException e) {
            e.getMessage();
            System.exit(1);
            return null;
        }
    }

    private List<String> readArrayAsNamesFromFile() throws FileNotFoundException {
        List<String> namesOfPlayers = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(parameters.input.trim()), "UTF-8")) {
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
        }
        String[] names = lines.toArray(new String[0]);
        for (String name : names) {
            namesOfPlayers.addAll(Arrays.asList(name.trim().split(",")));
        }
        return namesOfPlayers;
    }

    private List<Player> formPlayerList(List<String> names) {
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < parameters.numberOfPlayers; i++) {
            playerList.add(new Player(names.get(i).trim()));
        }
        return playerList;
    }

    public void writeIn(String allGameComments) {
        try {
            FileWriter fileWriter = new FileWriter(parameters.output);
            fileWriter.append(allGameComments);
            fileWriter.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }
}

