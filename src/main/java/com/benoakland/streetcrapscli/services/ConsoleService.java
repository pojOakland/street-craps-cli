package com.benoakland.streetcrapscli.services;

import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public String promptForString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public void printString(String str) {
        System.out.println(str);
    }

    public void printBlankLine() {
        System.out.println();
    }

    public Integer promptForPlayerType(int playerNumber) {
        int result = 0;
        String userInput = "";
        System.out.println("[1] Log in");
        System.out.println("[2] Register");
        System.out.println("[3] Play as guest");
        System.out.print("Select an option for Player " + playerNumber + ": ");
        while (result == 0) {
            try {
                userInput = scanner.nextLine().trim();
                while (userInput.length() <= 0 || Integer.parseInt(userInput) < 1 || Integer.parseInt(userInput) > 3) {
                    System.out.println();
                    System.out.println("Invalid entry!");
                    System.out.println();
                    userInput = scanner.nextLine().trim();
                }
                result = Integer.parseInt(userInput);
            } catch (Exception e) {
                System.out.println();
                System.out.println("Invalid entry!");
                System.out.println();
            }
        }
        return result;
    }



    // TODO - encapsulate most interaction with the console
}
