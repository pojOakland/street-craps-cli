package com.techelevator.streetcrapscli;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        String userInput;
        int stakes;
        int startingPlayer = ThreadLocalRandom.current().nextInt(0, 2);
        Player[] players = new Player[2];

        System.out.println();
        System.out.println("********************");
        System.out.println("*       Lets       *");
        System.out.println("*       Play       *");
        System.out.println("*       Dice       *");
        System.out.println("*       V0.2       *");
        System.out.println("********************");
        System.out.println();
        System.out.println("Enter Player 1's name:");

        userInput = inputScanner.nextLine().trim();
        Player player1 = new Player(userInput);
        players[0] = player1;

        System.out.println();
        System.out.println("Enter Player 2's name:");

        userInput = inputScanner.nextLine().trim();
        Player player2 = new Player(userInput);
        players[1] = player2;

        System.out.println("");
        System.out.println("Enter the stakes for the game (whole dollars):");

        userInput = inputScanner.nextLine().trim();
        stakes = Integer.parseInt(userInput);

        System.out.println("");
        System.out.printf(players[startingPlayer].getName() + " goes first");

        inputScanner.close();
    }
}
