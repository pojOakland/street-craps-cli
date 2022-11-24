package com.benoakland.streetcrapscli.services;

import java.util.Scanner;

public class ConsoleService {
    public static ConsoleService consoleServiceInstance = new ConsoleService();

    private final Scanner scanner = new Scanner(System.in);

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void printString(String str) {
        System.out.println(str);
    }

    public void printBlankLine() {
        System.out.println();
    }

    public String promptForPlayerDisplayName() {
        String userInput = promptForString("Enter Player 1's name: ");
        while (userInput.length() <= 0) {
            userInput = promptForString("Enter Player 1's name: ");
        }
        return userInput;
    }

    public String promptForPlayerDisplayName(String playerDisplayName) {
        String userInput = promptForString("Enter Player 2's name: ");
        while (userInput.length() <= 0 || userInput.equalsIgnoreCase(playerDisplayName)) {
            if (userInput.equalsIgnoreCase(playerDisplayName)) {
                printString("\nPlayers must have different names!\n");
            }
            userInput = promptForString("Enter Player 2's name: ");
        }
        return userInput;
    }

    public Integer promptForPlayerType(int playerNumber) {
        int result = 0;
        String userInput = "";
        printString("\n[1] Log in");
        printString("[2] Register");
        printString("[3] Play as guest");
        while (result == 0) {
            try {
                userInput = promptForString("Select an option for Player " + playerNumber + ": ");
                while (userInput.length() <= 0 || Integer.parseInt(userInput) < 1 || Integer.parseInt(userInput) > 3) {
                    printString("\nInvalid entry!\n");
                    userInput = promptForString("Select an option for Player " + playerNumber + ": ");
                }
                result = Integer.parseInt(userInput);
            } catch (Exception e) {
                printString("\nInvalid entry!\n");
            }
        }
        printBlankLine(); // TODO - I'd really like to get rid of this
        return result;
    }
}