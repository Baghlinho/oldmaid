package org.example.game;

import org.example.cards.Card;

import java.util.ArrayList;
import java.util.Iterator;

public class Player implements Runnable {
    private static final int TURN_DELAY_MS = 500;
    private final ArrayList<Card> hand;
    private final String uname;
    private final GameState gameState;

    public Player(String uname, GameState gameState) {
        this.hand = new ArrayList<>();
        this.uname = uname;
        this.gameState = gameState;
    }

    public String getUname() {
        return uname;
    }

    public String getHand() {
        return hand.toString();
    }

    public int getHandSize() {
        return hand.size();
    }

    public void takeCard(Card card) {
        if(card == null)
            throw new IllegalArgumentException("Card can't be null");
        hand.add(card);
    }

    public Card giveCard(int index) {
        return hand.remove(index);
    }

    @Override
    public void run() {
        throwMatchingPairs();
        while(!hand.isEmpty()) {
            synchronized (gameState) {
                while(!gameState.isTurn(this)) {
                    try {
                        gameState.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(gameState.isOnePlayerLeft()) hand.clear();
                if(hand.isEmpty()) gameState.removePlayer(this);
                else {
                    delayTurn();
                    playTurn();
                    gameState.incrementNumberOfTurns();
                    if(hand.isEmpty()) gameState.removePlayer(this);
                    else gameState.proceedNextTurn();
                }
                gameState.notifyAll();
            }
        }
    }

    private void delayTurn() {
        try {
            Thread.sleep(TURN_DELAY_MS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void playTurn() {
        Communicator.displaySubsectionTitle(String.format("Turn %d: %s", gameState.getNumberOfTurns(), uname));
        Communicator.displayPlayerHand(uname, getHand());
        Card pulledCard = gameState.pullCardFromNextPlayer();
        Communicator.displayPulledCard(uname, pulledCard);
        throwMatchingPairIfExist(pulledCard);
    }

    private void throwMatchingPairIfExist(Card c1) {
        Card c2 = null;
        for(Card card : hand) {
            if (doCardsMatch(c1, card)) {
                c2 = card;
                break;
            }
        }
        if(c2 == null)
            hand.add(c1);
        else {
            hand.remove(c2);
            Communicator.displayThrownPair(uname, c1, c2);
        }
    }

    private void throwMatchingPairs() {
        ArrayList<Card> matchingPairs = new ArrayList<>();
        for (int i = 0; i < getHandSize()-1; i++) {
            Card c1 = hand.get(i);
            for (int j = i+1; j < getHandSize(); j++) {
                Card c2 = hand.get(j);
                if(doCardsMatch(c1, c2)) {
                    matchingPairs.add(c1);
                    matchingPairs.add(c2);
                }
            }
        }
        Iterator<Card> iterator = matchingPairs.iterator();
        while(iterator.hasNext()) {
            Card c1 = iterator.next();
            Card c2 = iterator.next();
            hand.remove(c1);
            hand.remove(c2);
            Communicator.displayThrownPair(uname, c1, c2);
        }
    }

    private boolean doCardsMatch(Card c1, Card c2) {
        if(c1 == null || c2 == null)
            throw new IllegalArgumentException("Cards to match can't be null");
        return c1.getColor()==c2.getColor()
                && c1.getType() == c2.getType();
    }
}
