package org.example.game;

import org.example.cards.Card;

import java.util.List;
import java.util.Scanner;

public class Communicator {

    private static final Scanner scanner = new Scanner(System.in);

    public static void displaySectionTitle(String title) {
        String halfSplit = "*****************************";
        System.out.printf("%s %s %s\n", halfSplit, title, halfSplit);
    }

    public static void displaySubsectionTitle(String title) {
        String halfSplit = "+++++";
        System.out.printf("%s %s %s\n", halfSplit, title, halfSplit);
    }

    public static int promptPlayersCount() {
        System.out.print("- Number of players: ");
        while(true) {
            try {
                int count = Integer.parseInt(scanner.nextLine().trim());
                if(count < 2) {
                    System.out.print("- There should be at least two players, try again: ");
                    continue;
                }
                return count;
            }
            catch (NumberFormatException e) {
                System.out.print("- Number invalid, try again: ");
            }
        }
    }

    public static void announceRanking(List<String> rankingInfo) {
        System.out.println("- Ranking: [rank. name : turn finished]");
        rankingInfo.forEach(System.out::println);
    }

    private static void printPlayerItem(String uname, String key, String value) {
        System.out.printf("- %s> %s: %s\n", uname, key, value);
    }

    public static void displayPlayerHand(String uname, String hand) {
        printPlayerItem(uname, "Hand", hand);
    }

    public static synchronized void displayThrownPair(String uname, Card c1, Card c2) {
        printPlayerItem(uname, "Throw pair",String.format("[%s, %s]", c1, c2));
    }

    public static void displayPulledCard(String uname, Card card) {
        printPlayerItem(uname, "Take card from the next player", String.format("[%s]", card));
    }
}
