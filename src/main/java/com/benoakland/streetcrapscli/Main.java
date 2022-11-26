package com.benoakland.streetcrapscli;

import com.benoakland.streetcrapscli.services.ConsoleService;
import com.benoakland.streetcrapscli.services.PlayerService;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        String userInput;
        int stakes = 0;
        int startingPlayerIndex = ThreadLocalRandom.current().nextInt(0, 2);
        Player[] players = new Player[2];
        boolean keepPlaying;

        ConsoleService.getInstance().printString("\n********************");
        ConsoleService.getInstance().printString("*       Let's      *");
        ConsoleService.getInstance().printString("*       Shoot      *");
        ConsoleService.getInstance().printString("*       Dice       *");
        ConsoleService.getInstance().printString("*       V1.2       *");
        ConsoleService.getInstance().printString("********************");

        userInput = ConsoleService.getInstance().promptForPlayerType(1).toString();
        if (userInput.equalsIgnoreCase("1")) {
            Player player1 = PlayerService.getInstance().login("");
            players[0] = player1;
        }
        else if (userInput.equalsIgnoreCase("2")) {
            Player player1 = PlayerService.getInstance().createPlayer();
            players[0] = player1;
        }
        else if (userInput.equalsIgnoreCase("3")) {
            Player player1 = new Player(ConsoleService.getInstance().promptForPlayerDisplayName());
            players[0] = player1;
        }

        userInput = ConsoleService.getInstance().promptForPlayerType(2).toString();
        if (userInput.equalsIgnoreCase("1")) {
            Player player2 = PlayerService.getInstance().login(players[0].getDisplayName());
            players[1] = player2;
        }
        else if (userInput.equalsIgnoreCase("2")) {
            Player player2 = PlayerService.getInstance().createPlayer();
            players[1] = player2;
        }
        else if (userInput.equalsIgnoreCase("3")) {
            Player player2 = new Player(ConsoleService.getInstance().promptForPlayerDisplayName(players[0].getDisplayName()));
            players[1] = player2;
        }

        while (stakes == 0) {
            try {
                userInput = ConsoleService.getInstance().promptForString("\nEnter the stakes for the game: ");
                while (userInput.length() <= 0 || Integer.parseInt(userInput) <= 0) {
                    if (Integer.parseInt(userInput) <= 0) {
                        ConsoleService.getInstance().printString("\nStakes must be a positive whole number!\n");
                    }
                    userInput = ConsoleService.getInstance().promptForString("Enter the stakes for the game: ");
                }
                stakes = Integer.parseInt(userInput);
            } catch (Exception e) {
                ConsoleService.getInstance().printString("\nStakes must be a positive whole number!");
            }
        }

        ConsoleService.getInstance().printString("\n" + players[startingPlayerIndex].getDisplayName() + " goes first");

        Game game = new Game(players, stakes, startingPlayerIndex);

        do {
            String currentPlayer = game.run().getDisplayName();

            userInput = ConsoleService.getInstance().promptForString("\n" + currentPlayer + " is the shooter, do you want to keep playing? ");
            while (userInput.length() <= 0 || !userInput.equalsIgnoreCase("yes") &&
                    !userInput.equalsIgnoreCase("it was a good day") &&
                    !userInput.equalsIgnoreCase("no")){
                userInput = ConsoleService.getInstance().promptForString("\n" + currentPlayer + " is the shooter, do you want to keep playing? ");
            }

            while (userInput.equalsIgnoreCase("it was a good day")){
                currentPlayer = game.itWasAGoodDay().getDisplayName();
                userInput = ConsoleService.getInstance().promptForString("\n" + currentPlayer + " is the shooter, do you want to keep playing? ");
                while (userInput.length() <= 0 || !userInput.equalsIgnoreCase("yes") &&
                        !userInput.equalsIgnoreCase("it was a good day") &&
                        !userInput.equalsIgnoreCase("no")){
                    userInput = ConsoleService.getInstance().promptForString("\n" + currentPlayer + " is the shooter, do you want to keep playing? ");
                }
            }
            keepPlaying = userInput.equalsIgnoreCase("yes");

        } while (keepPlaying);

        if (players[0].getBankroll() == players[1].getBankroll()) {

            ConsoleService.getInstance().printString("\nThe game ends all square.\n");

        }
        else if (players[0].getBankroll() > players[1].getBankroll()) {

            ConsoleService.getInstance().printString("\n" + players[1].getDisplayName() + " owes " + players[0].getDisplayName() + " $" + players[0].getBankroll() + ".\n");

        }
        else {

            ConsoleService.getInstance().printString("\n" + players[0].getDisplayName() + " owes " + players[1].getDisplayName() + " $" + players[1].getBankroll() + ".\n");

        }
        if (players[0].getId() != 0) {

            PlayerService.getInstance().updatePlayer(players[0]);

        }
        if (players[1].getId() != 0) {

            PlayerService.getInstance().updatePlayer(players[1]);

        }
    }
}
