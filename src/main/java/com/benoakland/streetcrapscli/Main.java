package com.benoakland.streetcrapscli;

import com.benoakland.streetcrapscli.dto.PlayerUpdateDto;
import com.benoakland.streetcrapscli.services.ConsoleService;
import com.benoakland.streetcrapscli.services.PlayerService;

//import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        String userInput;
        int stakes = 0;
        int startingPlayerIndex = ThreadLocalRandom.current().nextInt(0, 2);
        Player[] players = new Player[2];
        boolean keepPlaying;

        ConsoleService.consoleServiceInstance.printBlankLine();
        ConsoleService.consoleServiceInstance.printString("********************");
        ConsoleService.consoleServiceInstance.printString("*       Let's      *");
        ConsoleService.consoleServiceInstance.printString("*       Shoot      *");
        ConsoleService.consoleServiceInstance.printString("*       Dice       *");
        ConsoleService.consoleServiceInstance.printString("*       V1.2       *");
        ConsoleService.consoleServiceInstance.printString("********************");
        ConsoleService.consoleServiceInstance.printBlankLine();

        userInput = ConsoleService.consoleServiceInstance.promptForPlayerType(1).toString();
        if (userInput.equalsIgnoreCase("1")) {
            ConsoleService.consoleServiceInstance.printBlankLine();
            Player player1 = PlayerService.playerServiceInstance.login("");
            players[0] = player1;
        }
        else if (userInput.equalsIgnoreCase("2")) {
            ConsoleService.consoleServiceInstance.printBlankLine();
            Player player1 = PlayerService.playerServiceInstance.createPlayer();
            players[0] = player1;
        }
        else if (userInput.equalsIgnoreCase("3")) {
            ConsoleService.consoleServiceInstance.printBlankLine();
            Player player1 = new Player(ConsoleService.consoleServiceInstance.promptForPlayerDisplayName());
            players[0] = player1;
        }

        ConsoleService.consoleServiceInstance.printBlankLine();

        userInput = ConsoleService.consoleServiceInstance.promptForPlayerType(2).toString();
        if (userInput.equalsIgnoreCase("1")) {
            ConsoleService.consoleServiceInstance.printBlankLine();
            Player player2 = PlayerService.playerServiceInstance.login(players[0].getDisplayName());
            players[1] = player2;
        }
        else if (userInput.equalsIgnoreCase("2")) {
            ConsoleService.consoleServiceInstance.printBlankLine();
            Player player2 = PlayerService.playerServiceInstance.createPlayer();
            players[1] = player2;
        }
        else if (userInput.equalsIgnoreCase("3")) {
            ConsoleService.consoleServiceInstance.printBlankLine();
            Player player2 = new Player(ConsoleService.consoleServiceInstance.promptForPlayerDisplayName(players[0].getDisplayName()));
            players[1] = player2;
        }

        ConsoleService.consoleServiceInstance.printBlankLine();

        while (stakes == 0) {
            try {
                userInput = ConsoleService.consoleServiceInstance.promptForString("Enter the stakes for the game: ");
                while (userInput.length() <= 0 || Integer.parseInt(userInput) <= 0) {
                    if (Integer.parseInt(userInput) <= 0) {
                        ConsoleService.consoleServiceInstance.printBlankLine();
                        ConsoleService.consoleServiceInstance.printString("Stakes must be a positive whole number");
                    }
                    userInput = ConsoleService.consoleServiceInstance.promptForString("Enter the stakes for the game: ");
                }
                stakes = Integer.parseInt(userInput);
            } catch (Exception e) {
                ConsoleService.consoleServiceInstance.printBlankLine();
                ConsoleService.consoleServiceInstance.printString("Stakes must be a positive whole number");
            }
        }

        ConsoleService.consoleServiceInstance.printBlankLine();
        ConsoleService.consoleServiceInstance.printString(players[startingPlayerIndex].getDisplayName() + " goes first");

        Game game = new Game(players, stakes, startingPlayerIndex);

        do {
            String currentPlayer = game.run().getDisplayName();

            ConsoleService.consoleServiceInstance.printBlankLine();
            userInput = ConsoleService.consoleServiceInstance.promptForString(currentPlayer + " is the shooter, do you want to keep playing? ");
            while (userInput.length() <= 0 || !userInput.equalsIgnoreCase("yes") &&
                    !userInput.equalsIgnoreCase("it was a good day") &&
                    !userInput.equalsIgnoreCase("no")){
                ConsoleService.consoleServiceInstance.printBlankLine();
                userInput = ConsoleService.consoleServiceInstance.promptForString(currentPlayer + " is the shooter, do you want to keep playing? ");
            }

            while (userInput.equalsIgnoreCase("it was a good day")){
                currentPlayer = game.itWasAGoodDay().getDisplayName();
                userInput = ConsoleService.consoleServiceInstance.promptForString(currentPlayer + " is the shooter, do you want to keep playing? ");
                while (userInput.length() <= 0 || !userInput.equalsIgnoreCase("yes") &&
                        !userInput.equalsIgnoreCase("it was a good day") &&
                        !userInput.equalsIgnoreCase("no")){
                    ConsoleService.consoleServiceInstance.printBlankLine();
                    userInput = ConsoleService.consoleServiceInstance.promptForString(currentPlayer + " is the shooter, do you want to keep playing? ");
                }
            }
            keepPlaying = userInput.equalsIgnoreCase("yes");

        } while (keepPlaying);

        if (players[0].getBankroll() == players[1].getBankroll()) {

            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString("The game ends all square.");

        }
        else if (players[0].getBankroll() > players[1].getBankroll()) {

            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString(players[1].getDisplayName() + " owes " + players[0].getDisplayName() + " $" + players[0].getBankroll() + ".");

        }
        else {

            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString(players[0].getDisplayName() + " owes " + players[1].getDisplayName() + " $" + players[1].getBankroll() + ".");

        }
        ConsoleService.consoleServiceInstance.printBlankLine();
        if (players[0].getId() != 0) {
            PlayerUpdateDto player1Update = new PlayerUpdateDto(players[0].getId(),players[0].getBankroll());
            boolean isSuccessful = PlayerService.playerServiceInstance.updatePlayer(player1Update);
            if (isSuccessful) {
                players[0] = PlayerService.playerServiceInstance.getPlayer(players[0].getDisplayName());
                ConsoleService.consoleServiceInstance.printString(players[0].getDisplayName() + " has now played " + players[0].getLifetimeGames()
                        + " game" + (players[0].getLifetimeGames() == 1 ? "" : "s") + "!");
                ConsoleService.consoleServiceInstance.printString(players[0].getDisplayName() + "'s lifetime balance is $" + players[0].getLifetimeBalance());
            }
        }
        ConsoleService.consoleServiceInstance.printBlankLine();
        if (players[1].getId() != 0) {
            PlayerUpdateDto player1Update = new PlayerUpdateDto(players[1].getId(),players[1].getBankroll());
            boolean isSuccessful = PlayerService.playerServiceInstance.updatePlayer(player1Update);
            if (isSuccessful) {
                players[1] = PlayerService.playerServiceInstance.getPlayer(players[1].getDisplayName());
                ConsoleService.consoleServiceInstance.printString(players[1].getDisplayName() + " has now played " + players[1].getLifetimeGames()
                        + " game" + (players[1].getLifetimeGames() == 1 ? "" : "s") + "!");
                ConsoleService.consoleServiceInstance.printString(players[1].getDisplayName() + "'s lifetime balance is $" + players[1].getLifetimeBalance());
            }
        }
    }
}
