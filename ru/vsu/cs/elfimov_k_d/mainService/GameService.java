package ru.vsu.cs.elfimov_k_d.mainService;

import ru.vsu.cs.elfimov_k_d.model.*;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.*;
import ru.vsu.cs.elfimov_k_d.mainService.ICardsService.CardsServices.*;

import java.util.*;

class GameService {
    private ICardsService[] combinations;

    GameService() {
        combinations = new ICardsService[] { new RoyalFleshService(), new StreetFleshService(), new KareService(),
                new FullHouseService(), new FleshService(), new StreetService(),
                new SetService(), new TwoCoupleService(), new CoupleService(), new KickerService() };
    }

    Game createNewGame(List<Player> players) {
        return new Game(players);
    }

    void play(Game game) {
        for (GameState state : EnumSet.allOf(GameState.class)) {
            game.setGameState(state);
            game.getGameWatcherService().nextStateComment(game);
            if (state.equals(GameState.FLOP)) {
                distributionForPlayers(game);
            }
            distributionForTheTable(game);
            game.getGameWatcherService().cardsOnTheTableComment(game);

            doStep(game);

            if (state.equals(GameState.RIVER) || !(game.getQueue().size() > 1)) {
                findWinners(game);
                game.getGameWatcherService().winnersComment(game);
                break;
            }
        }
    }

    private void doStep(Game game) {
        Queue<Player> newQueue = new LinkedList<>();
        for (Player player : game.getQueue()) {
            boolean playerInGame = true;
            List<Card> hisHand = game.getPlayersWithCards().get(player);
            game.getGameWatcherService().playerWithCardsComment(game, player);

            findCombinationForThePlayer(game, player);
            game.getGameWatcherService().combinationComment(game, player);
            switch (game.getGameState()) {
                case FLOP:
                    playerInGame = !hisHand.contains(new Card(Value.FOUR, TypeOfSuit.HEARTS));
                    break;
                case TURN:
                    playerInGame = !hisHand.contains(new Card(Value.FIVE, TypeOfSuit.CLUBS));
                    break;
                case RIVER:
                    playerInGame = !hisHand.contains(new Card(Value.SIX, TypeOfSuit.DIAMONDS));
                    break;
            }
            if (playerInGame) {
                newQueue.add(player);
                putDownBetsAndCalculateNewPrize(game, player);
                game.getGameWatcherService().betComment(game, player);
            } else {
                fold(game, player);
                game.getGameWatcherService().foldComment(game, player);
            }
        }
        game.setQueue(newQueue);
    }

    private void distributionForPlayers(Game game) {
        Map<Player, List<Card>> playersWithCards = new HashMap<>();
        Queue<Player> queue = game.getQueue();
        for (Player player : queue) {
            List<Card> cards = getAFewCardsFromTheDeck(game, 2);
            playersWithCards.put(player, cards);
        }
        game.setPlayersWithCards(playersWithCards);
    }
    private void distributionForTheTable(Game game) {
        List<Card> cardsOnTheTable = game.getCardsOnTheTable();
        List<Card> cards = getAFewCardsFromTheDeck(game, game.getGameState().getCardCount());
        cardsOnTheTable.addAll(cards);
    }
    private List<Card> getAFewCardsFromTheDeck(Game game, int number) {
        List<Card> cards = new ArrayList<>();
        List<Card> deck = game.getDeck();
        for (int i = 0; i < number; i++) {
            Card card = deck.get(0);
            deck.remove(0);
            cards.add(card);
        }
        return cards;
    }

    private void findCombinationForThePlayer(Game game, Player player) {
        Map<Player, Combo> playersCombo = game.getPlayersCombo();
        Map<Player, List<Card>> playersWithCards = game.getPlayersWithCards();

        List<Card>  hand = playersWithCards.get(player);
        List<Card>  table = game.getCardsOnTheTable();

        for (ICardsService combination : combinations) {
            Combo combo = combination.isCombination(hand, table);
            if (combo != null) {
                playersCombo.put(player, combo);
                break;
            }
        }
    }
    private void putDownBetsAndCalculateNewPrize(Game game, Player player) {
        int prizeCash = game.getPrizeCash();
        Map<Player, Integer> bets = game.getBets();
        int newBetOfThePlayer;

        if (bets.containsKey(player)) {
            int oldBetOfThePlayer = bets.get(player);
            newBetOfThePlayer = oldBetOfThePlayer * Game.K;
            prizeCash += newBetOfThePlayer - oldBetOfThePlayer;
        } else {
            newBetOfThePlayer = Game.MINBET;
            prizeCash += newBetOfThePlayer;
        }
        bets.put(player, newBetOfThePlayer);
        game.setThePrize(prizeCash);
    }
    private void fold(Game game, Player player) {
        Map<Player, List<Card>> playersWithCards = game.getPlayersWithCards();
        Map<Player, Integer> bets = game.getBets();
        Map<Player, Combo> playersCombo = game.getPlayersCombo();

        playersWithCards.remove(player);
        bets.remove(player);
        playersCombo.remove(player);
    }

    private void findWinners(Game game) {
        Map<Player, Integer> winnersAndPrizes = new HashMap<>();
        Map<Player, Combo> playersCombo = game.getPlayersCombo();
        List<Player> tempWinners = new ArrayList<>();
        Combo seniorCombo = null;

        for (Map.Entry<Player, Combo> playerComboEntry : playersCombo.entrySet()) {
            Player player = playerComboEntry.getKey();
            Combo combo = playerComboEntry.getValue();

            if (seniorCombo == null) {
                seniorCombo = combo;
            }
            if (seniorCombo.getComboEnum().equals(combo.getComboEnum())) {
                tempWinners.add(player);
            } else if (combo.getComboEnum().compareTo(seniorCombo.getComboEnum()) > 0) {
                seniorCombo = combo;
                tempWinners.clear();
                tempWinners.add(player);
            }
        }

        List<Player> finalWinners = new ArrayList<>();
        if (tempWinners.size() > 1) {
            Player player1 = tempWinners.get(0);
            Player player2 = tempWinners.get(1);
            for (int i = 1; i < tempWinners.size(); i++) {
                Combo players1Combo = playersCombo.get(player1);
                Combo players2Combo = playersCombo.get(player2);

                Comparator<Card> comparator = (player1Card, player2Card) -> player1Card.getValue().getKickerScore() - player2Card.getValue().getKickerScore();
                Card kicker1 = players1Combo.getKicker();
                Card kicker2 = players2Combo.getKicker();
                if (!player1.equals(player2)) {
                    int compare = comparator.compare(kicker1, kicker2);
                    if (compare > 0) {
                        finalWinners.add(player1);
                        finalWinners.remove(player2);
                        if (i + 1 != tempWinners.size()) {
                            player2 = tempWinners.get(i + 1);
                        }
                    } else if (compare < 0) {
                        finalWinners.add(player2);
                        finalWinners.remove(player1);
                        if (i + 1 != tempWinners.size()) {
                            player1 = tempWinners.get(i + 1);
                        }
                    } else {
                        finalWinners.addAll(Arrays.asList(player1, player2));
                        if (i + 1 != tempWinners.size()) {
                            player2 = tempWinners.get(i + 1);
                        }
                    }
                } else player2 = tempWinners.get(i + 1);
            }
        } else finalWinners = tempWinners;

        int numberOfWinners = finalWinners.size();
        int commonPrize = game.getPrizeCash();

        for (Player player : finalWinners) {
            winnersAndPrizes.put(player, commonPrize / numberOfWinners);
        }
        game.setWinnersAndPrizes(winnersAndPrizes);
    }
}
