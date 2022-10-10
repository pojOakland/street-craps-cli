package com.benoakland.streetcrapscli;

public class SideBet {

    // Properties
    private boolean sideBetActive;

    // Constructors
    public SideBet() {
        sideBetActive = false;
    }

    // Getters/Setters
    public boolean isSideBetActive() {
        return sideBetActive;
    }

    public void setSideBetActive(boolean sideBetActive) {
        this.sideBetActive = sideBetActive;
    }

    // Methods
    public boolean[] checkSideBet(int point, int rollResult) {
        boolean activePlayerWins = false;
        boolean inactivePlayerWins = false;

        switch (point) {
            case 4:
                switch (rollResult) {
                    case 4: case 10:
                        activePlayerWins = true;
                    case 7: case 11:
                        inactivePlayerWins = true;
                }
            case 5:
                switch (rollResult) {
                    case 5: case 9:
                        activePlayerWins = true;
                    case 7: case 11:
                        inactivePlayerWins = true;
                }
            case 6:
                switch (rollResult) {
                    case 6: case 8:
                        activePlayerWins = true;
                    case 2: case 3: case 7: case 11: case 12:
                        inactivePlayerWins = true;
                }
            case 8:
                switch (rollResult) {
                    case 6: case 8:
                        activePlayerWins = true;
                    case 2: case 3: case 7: case 11: case 12:
                        inactivePlayerWins = true;
                }
            case 9:
                switch (rollResult) {
                    case 5: case 9:
                        activePlayerWins = true;
                    case 7: case 11:
                        inactivePlayerWins = true;
                }
            case 10:
                switch (rollResult) {
                    case 4: case 10:
                        activePlayerWins = true;
                    case 7: case 11:
                        inactivePlayerWins = true;
                }
        }

        return new boolean[] {activePlayerWins,inactivePlayerWins};
    }
}
