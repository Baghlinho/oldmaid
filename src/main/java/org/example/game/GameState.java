package org.example.game;

import org.example.cards.Card;
import org.example.cards.Deck;
import org.example.cards.DeckGenerator;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameState {
    private final List<Player> currentPlayers = new ArrayList<>();
    private final List<String> rankingInfo = new ArrayList<>();
    private int turn;
    private static int turns = 1;

    public void addPlayer(Player player) {
        currentPlayers.add(player);
    }

    public void dealCards() {
        DeckGenerator deckGenerator = new DeckGenerator();
        Deck deck = deckGenerator.generate();
        for (int i = 1; !deck.isEmpty(); i = (i + 1) % currentPlayers.size())
            currentPlayers.get(i).takeCard(deck.popCard());
    }

    public List<String> getAllHands() {
        return currentPlayers.stream().map(Player::getHand).collect(Collectors.toList());
    }

    private int getNextTurn() {
        return (turn+1)% currentPlayers.size();
    }

    public boolean isTurn(Player player) {
        return currentPlayers.get(turn) == player;
    }

    public void proceedNextTurn() {
        turn = getNextTurn();
    }

    public int getNumberOfTurns() {
        return turns;
    }

    public void incrementNumberOfTurns() {
        turns++;
    }

    public Card pullCardFromNextPlayer() {
        Random rand = new Random();
        Player nextPlayer = currentPlayers.get(getNextTurn());
        return nextPlayer.giveCard(rand.nextInt(nextPlayer.getHandSize()));
    }

    public void removePlayer(Player player) {
        currentPlayers.remove(player);
        rankingInfo.add(String.format("%d. %s : %s", rankingInfo.size()+1, player.getUname(), currentPlayers.isEmpty()? "THE OLD MAID" : turns-1));
        if(turn == currentPlayers.size()) turn = 0;
    }

    public boolean isOnePlayerLeft() {
        return currentPlayers.size() == 1;
    }

    public List<String> getRankingInfo() {
        return rankingInfo;
    }
}
