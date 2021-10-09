package ru.vsu.cs.elfimov_k_d.model;
import ru.vsu.cs.elfimov_k_d.iGameWatcherService.GameWatcherService;

import java.util.*;

public class Game {
    public static final int MINBET = 50;
    public static final int K = 3;

    private GameWatcherService gameWatcherService = new GameWatcherService();
    private StringBuilder gameComments = new StringBuilder();
    private List<Card> deck = new ArrayList<>();
    private Queue<Player> queue = new LinkedList<>();
    private Map<Player, List<Card>> playersWithCards = new HashMap<>();
    private Map<Player, Combo> playersCombo = new HashMap<>();
    private Map<Player, Integer> bets = new HashMap<>();
    private List<Card> cardsOnTheTable = new ArrayList<>();
    private GameState gameState = GameState.FLOP;
    private int prizeCash = 0;
    private Map<Player, Integer> winnersAndPrizes = new HashMap<>();

    public Game() {
        for (TypeOfSuit typeOfSuit : TypeOfSuit.values()) {
            for (Value value : Value.values()) {
                Card card = new Card(value, typeOfSuit);
                deck.add(card);
            }
        }
        Collections.shuffle(deck);
    }

    public StringBuilder getGameComments() {
        return gameComments;
    }
    public GameWatcherService getGameWatcherService() {
        return gameWatcherService;
    }
    public List<Card> getDeck() {
        return deck;
    }
    public Queue<Player> getQueue() {
        return queue;
    }
    public Map<Player, List<Card>> getPlayersWithCards() {
        return playersWithCards;
    }
    public Map<Player, Combo> getPlayersCombo() {
        return playersCombo;
    }
    public List<Card> getCardsOnTheTable() {
        return cardsOnTheTable;
    }
    public Map<Player, Integer> getBets() {
        return bets;
    }
    public GameState getGameState() {
        return gameState;
    }
    public Integer getPrizeCash() {
        return prizeCash;
    }
    public Map<Player, Integer> getWinnersAndTheirPrize() {
        return winnersAndPrizes;
    }

    public void setStartQueue(List<Player> players) {
        queue.addAll(players);
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    public void setPlayersWithCards(Map<Player, List<Card>> playersWithCards) {
        this.playersWithCards = playersWithCards;
    }
    public void setThePrize(int prizeCash) {
        this.prizeCash = prizeCash;
    }
    public void setQueue(Queue<Player> queue) {
        this.queue = queue;
    }
    public void setWinnersAndPrizes(Map<Player, Integer> winnersAndPrizes) {
        this.winnersAndPrizes = winnersAndPrizes;
    }
}
