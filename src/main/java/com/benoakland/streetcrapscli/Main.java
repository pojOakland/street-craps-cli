package com.benoakland.streetcrapscli;

import com.benoakland.streetcrapscli.dto.PlayerUpdateDto;
import com.benoakland.streetcrapscli.services.ConsoleService;
import com.benoakland.streetcrapscli.services.PlayerService;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        ConsoleService consoleService = new ConsoleService();
        PlayerService playerService = new PlayerService();
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
        System.out.println("*       V1.1       *");
        System.out.println("********************");
        System.out.println();

        userInput = consoleService.promptForPlayerType(1).toString();
        if (userInput.equalsIgnoreCase("1")) {
            Player player1 = playerService.login();
            players[0] = player1;
        }
        else if (userInput.equalsIgnoreCase("2")) {
            Player player1 = playerService.createPlayer();
            players[0] = player1;
        }
        else if (userInput.equalsIgnoreCase("3")) {
            System.out.println();
            System.out.print("Enter Player 1's name: ");

            userInput = inputScanner.nextLine().trim();
            while (userInput.length() <= 0) {
                System.out.print("Enter Player 1's name: ");
                userInput = inputScanner.nextLine().trim();
            }
            Player player1 = new Player(userInput);
            players[0] = player1;
        }

        userInput = consoleService.promptForPlayerType(2).toString();
        if (userInput.equalsIgnoreCase("1")) {
            Player player2 = playerService.login();
            players[1] = player2;
        }
        else if (userInput.equalsIgnoreCase("2")) {
            Player player2 = playerService.createPlayer();
            players[1] = player2;
        }
        else if (userInput.equalsIgnoreCase("3")) {
            System.out.println();
            System.out.print("Enter Player 2's name: ");

            userInput = inputScanner.nextLine().trim();
            while (userInput.length() <= 0 || userInput.equalsIgnoreCase(players[0].getDisplayName())) {
                if (userInput.equalsIgnoreCase(players[0].getDisplayName())) {
                    System.out.println();
                    System.out.println("Players must have different names.");
                }
                System.out.print("Enter Player 2's name: ");
                userInput = inputScanner.nextLine().trim();
            }
            Player player2 = new Player(userInput);
            players[1] = player2;
        }

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
        System.out.println(players[startingPlayerIndex].getDisplayName() + " goes first");

        Game game = new Game(players, stakes, startingPlayerIndex);

        do {
            String currentPlayer = game.run().getDisplayName();

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
                currentPlayer = game.itWasAGoodDay().getDisplayName();
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

        if (players[0].getBankroll() == players[1].getBankroll()) {

            System.out.println();
            System.out.println("The game ends all square.");

        }
        else if (players[0].getBankroll() > players[1].getBankroll()) {

            System.out.println();
            System.out.println(players[1].getDisplayName() + " owes " + players[0].getDisplayName() + " $" + players[0].getBankroll() + ".");

        }
        else {

            System.out.println();
            System.out.println(players[0].getDisplayName() + " owes " + players[1].getDisplayName() + " $" + players[1].getBankroll() + ".");

        }
        System.out.println();
        if (players[0].getId() != 0) {
            PlayerUpdateDto player1Update = new PlayerUpdateDto(players[0].getId(),players[0].getBankroll());
            boolean isSuccessful = playerService.updatePlayer(player1Update);
            if (isSuccessful) {
                players[0] = playerService.getPlayer(players[0].getDisplayName());
                consoleService.displayString(players[0].getDisplayName() + " has now played " + players[0].getLifetimeGames() + " games!");
                consoleService.displayString(players[0].getDisplayName() + "'s lifetime balance is $" + players[0].getLifetimeBalance());
            }
        }
        System.out.println();
        if (players[1].getId() != 0) {
            PlayerUpdateDto player1Update = new PlayerUpdateDto(players[1].getId(),players[1].getBankroll());
            boolean isSuccessful = playerService.updatePlayer(player1Update);
            if (isSuccessful) {
                players[1] = playerService.getPlayer(players[1].getDisplayName());
                consoleService.displayString(players[1].getDisplayName() + " has now played " + players[1].getLifetimeGames() + " games!");
                consoleService.displayString(players[1].getDisplayName() + "'s lifetime balance is $" + players[1].getLifetimeBalance());
            }
        }

        inputScanner.close();
    }
}
