package com.benoakland.streetcrapscli;

import com.benoakland.streetcrapscli.services.ConsoleService;

import java.util.Scanner;

public class SideBet {

    // Properties
    private boolean sideBetActive;
    private boolean activePlayerWin;
    private boolean inactivePlayerWin;

    // Constructors
    public SideBet() {
        sideBetActive = false;
        activePlayerWin = false;
        inactivePlayerWin = false;

    }

    // Getters/Setters
    public boolean isSideBetActive() {
        return sideBetActive;
    }

    public void setSideBetActive(boolean sideBetActive) {
        this.sideBetActive = sideBetActive;
    }

    public boolean didActivePlayerWin() {
        return activePlayerWin;
    }

    public boolean didInactivePlayerWin() {
        return inactivePlayerWin;
    }

    // Methods
    public void checkSideBet(int point, int rollResult) {
        switch (point) {
            case 4: case 10:
                handleSideBetWhenPointIs4Or10(rollResult); break;
            case 5: case 9:
                handleSideBetWhenPointIs5Or9(rollResult); break;
            case 6: case 8:
                handleSideBetWhenPointIs6Or8(rollResult); break;
        }
    }

    public void askForSideBet() {
        String sideBetInput = "";
        while (sideBetInput.length() <= 0 || !sideBetInput.equalsIgnoreCase("yes") && !sideBetInput.equalsIgnoreCase("no")) {

            sideBetInput = ConsoleService.consoleServiceInstance.promptForString("\nWould you like a side bet? ");
        }
        sideBetActive = sideBetInput.equalsIgnoreCase("yes");
    }

    private void handleSideBetWhenPointIs4Or10(int rollResult) {
        switch (rollResult) {
            case 4: case 10:
                inactivePlayerWin = false;
                activePlayerWin = true; break;
            case 7: case 11:
                activePlayerWin = false;
                inactivePlayerWin = true; break;
            case 2: case 3: case 5: case 6: case 8: case 9: case 12:
                activePlayerWin = false;
                inactivePlayerWin = false; break;
        }
    }

    private void handleSideBetWhenPointIs5Or9(int rollResult) {
        switch (rollResult) {
            case 5: case 9:
                inactivePlayerWin = false;
                activePlayerWin = true; break;
            case 7: case 11:
                activePlayerWin = false;
                inactivePlayerWin = true; break;
            case 2: case 3: case 4: case 6: case 8: case 10: case 12:
                activePlayerWin = false;
                inactivePlayerWin = false; break;
        }
    }

    private void handleSideBetWhenPointIs6Or8(int rollResult) {
        switch (rollResult) {
            case 6: case 8:
                inactivePlayerWin = false;
                activePlayerWin = true; break;
            case 2: case 3: case 7: case 11: case 12:
                activePlayerWin = false;
                inactivePlayerWin = true; break;
            case 4: case 5: case 9: case 10:
                activePlayerWin = false;
                inactivePlayerWin = false; break;
        }
    }
}
