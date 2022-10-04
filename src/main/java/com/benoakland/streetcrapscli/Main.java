package com.benoakland.streetcrapscli;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        String userInput;
        int stakes = 0;
        int startingPlayerIndex = ThreadLocalRandom.current().nextInt(0, 2);
        Player[] players = new Player[2];
        boolean keepPlaying;

        System.out.println();
        System.out.println("********************");
        System.out.println("*       Let's      *");
        System.out.println("*       Shoot      *");
        System.out.println("*       Dice       *");
        System.out.println("*       V1.0       *");
        System.out.println("********************");
        System.out.println();

        System.out.print("Enter Player 1's name: ");

        userInput = inputScanner.nextLine().trim();
        while (userInput.length() <= 0) {
            System.out.print("Enter Player 1's name: ");
            userInput = inputScanner.nextLine().trim();
        }
        Player player1 = new Player(userInput);
        players[0] = player1;

        System.out.println();
        System.out.print("Enter Player 2's name: ");

        userInput = inputScanner.nextLine().trim();
        while (userInput.length() <= 0 || userInput.equalsIgnoreCase(player1.getName())) {
            if (userInput.equalsIgnoreCase(player1.getName())) {
                System.out.println();
                System.out.println("Players must have different names.");
            }
            System.out.print("Enter Player 2's name: ");
            userInput = inputScanner.nextLine().trim();
        }
        Player player2 = new Player(userInput);
        players[1] = player2;

        System.out.println();
        System.out.print("Enter the stakes for the game: ");

        while (stakes == 0) {
            try {
                userInput = inputScanner.nextLine().trim();
                while (userInput.length() <= 0 || Integer.parseInt(userInput) <= 0) {
                    if (Integer.parseInt(userInput) <= 0) {
                        System.out.println();
                        System.out.println("Stakes must be a positive whole number");
                        System.out.print("Enter the stakes for the game: ");
                    }
                    userInput = inputScanner.nextLine().trim();
                }
                stakes = Integer.parseInt(userInput);
            } catch (Exception e) {
                System.out.println();
                System.out.println("Stakes must be a positive whole number");
                System.out.print("Enter the stakes for the game: ");
            }
        }

        System.out.println();
        System.out.println(players[startingPlayerIndex].getName() + " goes first");

        Game game = new Game(players, stakes, startingPlayerIndex);

        do {
            String currentPlayer = game.run().getName();

            System.out.println();
            System.out.print(currentPlayer + " is the shooter, do you want to keep playing? ");

            userInput = inputScanner.nextLine().trim();
            while (userInput.length() <= 0 || !userInput.equalsIgnoreCase("yes") &&
                    !userInput.equalsIgnoreCase("it was a good day") &&
                    !userInput.equalsIgnoreCase("no")){
                System.out.println();
                System.out.print(currentPlayer + " is the shooter, do you want to keep playing? ");
                userInput = inputScanner.nextLine().trim();
            }

            while (userInput.equalsIgnoreCase("it was a good day")){
                currentPlayer = game.itWasAGoodDay().getName();
                userInput = inputScanner.nextLine().trim();
                while (userInput.length() <= 0 || !userInput.equalsIgnoreCase("yes") &&
                        !userInput.equalsIgnoreCase("it was a good day") &&
                        !userInput.equalsIgnoreCase("no")){
                    System.out.println();
                    System.out.print(currentPlayer + " is the shooter, do you want to keep playing? ");
                    userInput = inputScanner.nextLine().trim();
                }
            }
            keepPlaying = userInput.equalsIgnoreCase("yes");

        } while (keepPlaying);

        if (player1.getBankroll() == player2.getBankroll()) {

            System.out.println();
            System.out.println("The game ends all square.");

        }
        else if (player1.getBankroll() > player2.getBankroll()) {

            System.out.println();
            System.out.println(player2.getName() + " owes " + player1.getName() + " $" + player1.getBankroll() + ".");

        }
        else {

            System.out.println();
            System.out.println(player1.getName() + " owes " + player2.getName() + " $" + player2.getBankroll() + ".");

        }

        inputScanner.close();
    }
}
