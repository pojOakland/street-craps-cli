package com.benoakland.streetcrapscli.services;

import com.benoakland.streetcrapscli.Player;
import com.benoakland.streetcrapscli.dto.PlayerAuthenticationDto;
import com.benoakland.streetcrapscli.security.PasswordHasher;
import org.bouncycastle.util.encoders.Base64;

import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);
    private final PlayerService playerService = new PlayerService();
    private final PasswordHasher passwordHasher = new PasswordHasher();

    public String promptForString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public void displayString(String str) {
        System.out.println(str);
    }

    public Integer promptForPlayerType(int playerNumber) {
        int result = 0;
        String userInput = "";
        System.out.println("Select an option for Player " + playerNumber + ":");
        System.out.println("[1] Log in");
        System.out.println("[2] Register");
        System.out.println("[3] Play as guest");
        while (result == 0) {
            try {
                userInput = scanner.nextLine().trim();
                while (userInput.length() <= 0 || Integer.parseInt(userInput) < 1 || Integer.parseInt(userInput) > 3) {
                    System.out.println();
                    System.out.printf("Invalid entry!");
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

    public Player login() {
        Player returnedPlayer = null;
        PlayerAuthenticationDto playerAuthenticationDto = null;
        boolean sucess = false;
        String userInput = "";
        System.out.print("Enter Display Name: ");
        System.out.flush();
        String displayName = scanner.nextLine().trim();
        playerAuthenticationDto = playerService.authenticatePlayer(displayName);
        String storedSalt = playerAuthenticationDto.getSalt();
        String storedPassword = playerAuthenticationDto.getHashedPassword();
        System.out.println();
        System.out.print("Enter Password: ");
        System.out.flush();
        String password = scanner.nextLine().trim();
        String hashedPassword = passwordHasher.computeHash(password, Base64.decode(storedSalt));
        sucess = storedPassword.equals(hashedPassword);
        returnedPlayer = playerService.getPlayer(playerAuthenticationDto.getDisplayName());
        return returnedPlayer;
    }

    // TODO - encapsulate most interaction with the console
}
