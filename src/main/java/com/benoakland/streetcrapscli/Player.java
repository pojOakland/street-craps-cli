package com.benoakland.streetcrapscli;

public class Player {

    // Properties
    private int id;
    private String displayName;
    private int bankroll;
    private int lifetimeBalance;
    private int lifetimeGames;
    private String hashedPassword;
    private String salt;

    // Constructors


    public Player() {
    }

    public Player(String displayName) {
        this.displayName = displayName;
        this.bankroll = 0;
    }



    // Getters/Setters
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getBankroll() {
        return bankroll;
    }

    public void setBankroll(int bankroll) {
        this.bankroll = bankroll;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLifetimeBalance() {
        return lifetimeBalance;
    }

    public void setLifetimeBalance(int lifetimeBalance) {
        this.lifetimeBalance = lifetimeBalance;
    }

    public int getLifetimeGames() {
        return lifetimeGames;
    }

    public void setLifetimeGames(int lifetimeGames) {
        this.lifetimeGames = lifetimeGames;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
