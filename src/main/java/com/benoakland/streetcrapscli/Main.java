package com.benoakland.streetcrapscli;

import com.benoakland.streetcrapscli.dto.PlayerUpdateDto;
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

        ConsoleService.consoleServiceInstance.printString("\n********************");
        ConsoleService.consoleServiceInstance.printString("*       Let's      *");
        ConsoleService.consoleServiceInstance.printString("*       Shoot      *");
        ConsoleService.consoleServiceInstance.printString("*       Dice       *");
        ConsoleService.consoleServiceInstance.printString("*       V1.2       *");
        ConsoleService.consoleServiceInstance.printString("********************");

        userInput = ConsoleService.consoleServiceInstance.promptForPlayerType(1).toString();
        if (userInput.equalsIgnoreCase("1")) {
            Player player1 = PlayerService.playerServiceInstance.login("");
            players[0] = player1;
        }
        else if (userInput.equalsIgnoreCase("2")) {
            Player player1 = PlayerService.playerServiceInstance.createPlayer();
            players[0] = player1;
        }
        else if (userInput.equalsIgnoreCase("3")) {
            Player player1 = new Player(ConsoleService.consoleServiceInstance.promptForPlayerDisplayName());
            players[0] = player1;
        }

        userInput = ConsoleService.consoleServiceInstance.promptForPlayerType(2).toString();
        if (userInput.equalsIgnoreCase("1")) {
            Player player2 = PlayerService.playerServiceInstance.login(players[0].getDisplayName());
            players[1] = player2;
        }
        else if (userInput.equalsIgnoreCase("2")) {
            Player player2 = PlayerService.playerServiceInstance.createPlayer();
            players[1] = player2;
        }
        else if (userInput.equalsIgnoreCase("3")) {
            Player player2 = new Player(ConsoleService.consoleServiceInstance.promptForPlayerDisplayName(players[0].getDisplayName()));
            players[1] = player2;
        }

        while (stakes == 0) {
            try {
                userInput = ConsoleService.consoleServiceInstance.promptForString("\nEnter the stakes for the game: ");
                while (userInput.length() <= 0 || Integer.parseInt(userInput) <= 0) {
                    if (Integer.parseInt(userInput) <= 0) {
                        ConsoleService.consoleServiceInstance.printString("\nStakes must be a positive whole number!\n");
                    }
                    userInput = ConsoleService.consoleServiceInstance.promptForString("Enter the stakes for the game: ");
                }
                stakes = Integer.parseInt(userInput);
            } catch (Exception e) {
                ConsoleService.consoleServiceInstance.printString("\nStakes must be a positive whole number!");
            }
        }

        ConsoleService.consoleServiceInstance.printString("\n" +players[startingPlayerIndex].getDisplayName() + " goes first");

        Game game = new Game(players, stakes, startingPlayerIndex);

        do {
            String currentPlayer = game.run().getDisplayName();

            userInput = ConsoleService.consoleServiceInstance.promptForString("\n" + currentPlayer + " is the shooter, do you want to keep playing? ");
            while (userInput.length() <= 0 || !userInput.equalsIgnoreCase("yes") &&
                    !userInput.equalsIgnoreCase("it was a good day") &&
                    !userInput.equalsIgnoreCase("no")){
                userInput = ConsoleService.consoleServiceInstance.promptForString("\n" + currentPlayer + " is the shooter, do you want to keep playing? ");
            }

            while (userInput.equalsIgnoreCase("it was a good day")){
                currentPlayer = game.itWasAGoodDay().getDisplayName();
                userInput = ConsoleService.consoleServiceInstance.promptForString("\n" + currentPlayer + " is the shooter, do you want to keep playing? ");
                while (userInput.length() <= 0 || !userInput.equalsIgnoreCase("yes") &&
                        !userInput.equalsIgnoreCase("it was a good day") &&
                        !userInput.equalsIgnoreCase("no")){
                    userInput = ConsoleService.consoleServiceInstance.promptForString("\n" + currentPlayer + " is the shooter, do you want to keep playing? ");
                }
            }
            keepPlaying = userInput.equalsIgnoreCase("yes");

        } while (keepPlaying);

        if (players[0].getBankroll() == players[1].getBankroll()) {

            ConsoleService.consoleServiceInstance.printString("\nThe game ends all square.\n");

        }
        else if (players[0].getBankroll() > players[1].getBankroll()) {

            ConsoleService.consoleServiceInstance.printString("\n" + players[1].getDisplayName() + " owes " + players[0].getDisplayName() + " $" + players[0].getBankroll() + ".\n");

        }
        else {

            ConsoleService.consoleServiceInstance.printString("\n" + players[0].getDisplayName() + " owes " + players[1].getDisplayName() + " $" + players[1].getBankroll() + ".\n");

        }
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
        if (players[1].getId() != 0) {
            PlayerUpdateDto player1Update = new PlayerUpdateDto(players[1].getId(),players[1].getBankroll());
            boolean isSuccessful = PlayerService.playerServiceInstance.updatePlayer(player1Update);
            if (isSuccessful) {
                players[1] = PlayerService.playerServiceInstance.getPlayer(players[1].getDisplayName());
                ConsoleService.consoleServiceInstance.printString("\n" + players[1].getDisplayName() + " has now played " + players[1].getLifetimeGames()
                        + " game" + (players[1].getLifetimeGames() == 1 ? "" : "s") + "!");
                ConsoleService.consoleServiceInstance.printString(players[1].getDisplayName() + "'s lifetime balance is $" + players[1].getLifetimeBalance());
            }
        }
    }
}
