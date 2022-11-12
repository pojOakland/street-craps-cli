package com.benoakland.streetcrapscli.services;

import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public String promptForString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public void displayString(String str) {
        System.out.println(str);
    }

    // TODO - encapsulate most interaction with the console
}
