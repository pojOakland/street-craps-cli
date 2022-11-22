package com.benoakland.streetcrapscli;

import com.benoakland.streetcrapscli.dto.PlayerUpdateDto;
import com.benoakland.streetcrapscli.services.ConsoleService;
import com.benoakland.streetcrapscli.services.PlayerService;

//import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        ConsoleService consoleService = new ConsoleService();
        PlayerService playerService = new PlayerService();
        String userInput;
        int stakes = 0;
        int startingPlayerIndex = ThreadLocalRandom.current().nextInt(0, 2);
        Player[] players = new Player[2];
        boolean keepPlaying;

        consoleService.printBlankLine();
        consoleService.printString("********************");
        consoleService.printString("*       Let's      *");
        consoleService.printString("*       Shoot      *");
        consoleService.printString("*       Dice       *");
        consoleService.printString("*       V1.2       *");
        consoleService.printString("********************");
        consoleService.printBlankLine();

        userInput = consoleService.promptForPlayerType(1).toString();
        if (userInput.equalsIgnoreCase("1")) {
            consoleService.printBlankLine();
            Player player1 = playerService.login("");
            players[0] = player1;
        }
        else if (userInput.equalsIgnoreCase("2")) {
            consoleService.printBlankLine();
            Player player1 = playerService.createPlayer();
            players[0] = player1;
        }
        else if (userInput.equalsIgnoreCase("3")) {
            consoleService.printBlankLine();
            Player player1 = new Player(consoleService.promptForPlayerDisplayName());
            players[0] = player1;
        }

        consoleService.printBlankLine();

        userInput = consoleService.promptForPlayerType(2).toString();
        if (userInput.equalsIgnoreCase("1")) {
            consoleService.printBlankLine();
            Player player2 = playerService.login(players[0].getDisplayName());
            players[1] = player2;
        }
        else if (userInput.equalsIgnoreCase("2")) {
            consoleService.printBlankLine();
            Player player2 = playerService.createPlayer();
            players[1] = player2;
        }
        else if (userInput.equalsIgnoreCase("3")) {
            consoleService.printBlankLine();
            Player player2 = new Player(consoleService.promptForPlayerDisplayName(players[0].getDisplayName()));
            players[1] = player2;
        }

        consoleService.printBlankLine();

        while (stakes == 0) {
            try {
                userInput = consoleService.promptForString("Enter the stakes for the game: ");
                while (userInput.length() <= 0 || Integer.parseInt(userInput) <= 0) {
                    if (Integer.parseInt(userInput) <= 0) {
                        consoleService.printBlankLine();
                        consoleService.printString("Stakes must be a positive whole number");
                    }
                    userInput = consoleService.promptForString("Enter the stakes for the game: ");
                }
                stakes = Integer.parseInt(userInput);
            } catch (Exception e) {
                consoleService.printBlankLine();
                consoleService.printString("Stakes must be a positive whole number");
            }
        }

        consoleService.printBlankLine();
        consoleService.printString(players[startingPlayerIndex].getDisplayName() + " goes first");

        Game game = new Game(players, stakes, startingPlayerIndex);

        do {
            String currentPlayer = game.run().getDisplayName();

            consoleService.printBlankLine();
            userInput = consoleService.promptForString(currentPlayer + " is the shooter, do you want to keep playing? ");
            while (userInput.length() <= 0 || !userInput.equalsIgnoreCase("yes") &&
                    !userInput.equalsIgnoreCase("it was a good day") &&
                    !userInput.equalsIgnoreCase("no")){
                consoleService.printBlankLine();
                userInput = consoleService.promptForString(currentPlayer + " is the shooter, do you want to keep playing? ");
            }

            while (userInput.equalsIgnoreCase("it was a good day")){
                currentPlayer = game.itWasAGoodDay().getDisplayName();
                userInput = consoleService.promptForString(currentPlayer + " is the shooter, do you want to keep playing? ");
                while (userInput.length() <= 0 || !userInput.equalsIgnoreCase("yes") &&
                        !userInput.equalsIgnoreCase("it was a good day") &&
                        !userInput.equalsIgnoreCase("no")){
                    consoleService.printBlankLine();
                    userInput = consoleService.promptForString(currentPlayer + " is the shooter, do you want to keep playing? ");
                }
            }
            keepPlaying = userInput.equalsIgnoreCase("yes");

        } while (keepPlaying);

        if (players[0].getBankroll() == players[1].getBankroll()) {

            consoleService.printBlankLine();
            consoleService.printString("The game ends all square.");

        }
        else if (players[0].getBankroll() > players[1].getBankroll()) {

            consoleService.printBlankLine();
            consoleService.printString(players[1].getDisplayName() + " owes " + players[0].getDisplayName() + " $" + players[0].getBankroll() + ".");

        }
        else {

            consoleService.printBlankLine();
            consoleService.printString(players[0].getDisplayName() + " owes " + players[1].getDisplayName() + " $" + players[1].getBankroll() + ".");

        }
        consoleService.printBlankLine();
        if (players[0].getId() != 0) {
            PlayerUpdateDto player1Update = new PlayerUpdateDto(players[0].getId(),players[0].getBankroll());
            boolean isSuccessful = playerService.updatePlayer(player1Update);
            if (isSuccessful) {
                players[0] = playerService.getPlayer(players[0].getDisplayName());
                consoleService.printString(players[0].getDisplayName() + " has now played " + players[0].getLifetimeGames()
                        + " game" + (players[0].getLifetimeGames() == 1 ? "" : "s") + "!");
                consoleService.printString(players[0].getDisplayName() + "'s lifetime balance is $" + players[0].getLifetimeBalance());
            }
        }
        consoleService.printBlankLine();
        if (players[1].getId() != 0) {
            PlayerUpdateDto player1Update = new PlayerUpdateDto(players[1].getId(),players[1].getBankroll());
            boolean isSuccessful = playerService.updatePlayer(player1Update);
            if (isSuccessful) {
                players[1] = playerService.getPlayer(players[1].getDisplayName());
                consoleService.printString(players[1].getDisplayName() + " has now played " + players[1].getLifetimeGames()
                        + " game" + (players[1].getLifetimeGames() == 1 ? "" : "s") + "!");
                consoleService.printString(players[1].getDisplayName() + "'s lifetime balance is $" + players[1].getLifetimeBalance());
            }
        }
    }
}
