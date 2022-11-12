package com.benoakland.streetcrapscli;

import com.benoakland.streetcrapscli.security.PasswordHasher;
import com.benoakland.streetcrapscli.services.ConsoleService;
import org.bouncycastle.util.encoders.Base64;

import java.util.Scanner;

public class Player {

    // Properties
    private final String displayName;
    private int bankroll;
    private int lifetimeBalance;
    private int lifetimeGames;
    private String hashedPassword;
    private String salt;

    // Constructors
    public Player(String displayName) {
        this.displayName = displayName;
        this.bankroll = 0;
    }

    public Player(String displayName, String hashedPassword, String salt) {
        this.displayName = displayName;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public Player(String displayName, int lifetimeBalance, int lifetimeGames, String hashedPassword, String salt) {
        this.displayName = displayName;
        this.lifetimeBalance = lifetimeBalance;
        this.lifetimeGames = lifetimeGames;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    // Getters/Setters
    public String getDisplayName() {
        return displayName;
    }

    public int getBankroll() {
        return bankroll;
    }

    public void setBankroll(int bankroll) {
        this.bankroll = bankroll;
    }


}
