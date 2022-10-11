package com.benoakland.streetcrapscli;

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
                break;
            case 5: case 9:
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
                break;
            case 6: case 8:
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
                break;
        }
    }

    public void askForSideBet() {
        String sideBetInput = "";
        Scanner inputScanner = new Scanner(System.in);
        while (sideBetInput.length() <= 0 || !sideBetInput.equalsIgnoreCase("yes") && !sideBetInput.equalsIgnoreCase("no")) {

            System.out.println();
            System.out.print("Would you like a side bet? ");

            sideBetInput = inputScanner.nextLine().trim();
        }
        sideBetActive = sideBetInput.equalsIgnoreCase("yes");
    }
}
