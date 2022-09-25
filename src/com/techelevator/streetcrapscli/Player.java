package com.techelevator.streetcrapscli;

public class Player {

    // Properties
    private String name;
    private int bankroll;

    //Constructors
    public Player(String name) {
        this.name = name;
        this.bankroll = 0;
    }

    // Getters/Setters
    public String getName() {
        return name;
    }

    public int getBankroll() {
        return bankroll;
    }

    public void setBankroll(int bankroll) {
        this.bankroll = bankroll;
    }
}
