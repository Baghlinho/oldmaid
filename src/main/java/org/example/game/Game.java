package org.example.game;

import java.util.List;

public class Game {
    private Thread[] threads;
    private GameState gameState;
    private static final String BOT_PREFIX = "Com";

    public void play() {
        setup();
        beginGame();
        endGame();
    }

    private void setup() {
        Communicator.displaySectionTitle("Game Setup");
        gameState = new GameState();
        int playersCount = Communicator.promptPlayersCount();
        threads = new Thread[playersCount];
        for (int i = 0; i < playersCount; i++) {
            Player player = new Player(BOT_PREFIX+i, gameState);
            threads[i] = new Thread(player);
            gameState.addPlayer(player);
        }
        gameState.dealCards();
        List<String> hands = gameState.getAllHands();
        for (int i = 0; i < playersCount; i++)
            Communicator.displayPlayerHand(BOT_PREFIX+i, hands.get(i));
    }

    private void beginGame() {
        Communicator.displaySectionTitle("Begin Game");
        for (Thread thread : threads)
            thread.start();
    }

    private void endGame() {
        try {
            for (Thread thread : threads)
                thread.join();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        Communicator.displaySectionTitle("End Game");
        List<String> rankingInfo = gameState.getRankingInfo();
        Communicator.announceRanking(rankingInfo);
    }
}
