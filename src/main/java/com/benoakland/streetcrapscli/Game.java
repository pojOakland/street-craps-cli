package com.benoakland.streetcrapscli;

import com.benoakland.streetcrapscli.services.ConsoleService;

public class Game {

    // Properties
    private Player[] players;
    private int stakes;
    private int point;
    private Player activePlayer;
    private Player inactivePlayer;
    private Player winningPlayer;
    private Player losingPlayer;
    private Roll roll;
    private SideBet sideBet;

    // Constants
    private static final Integer[] IWAGD_ROLL_ARRAY = new Integer[] {7,7,11,7,11,7,4,4};
    private static final String[] IWAGD_ROLL_NAME_ARRAY = new String[] {" Seven Out.", " Seven Out.", " YO.",
            " Seven Out.", " YO.", " Seven Out.", " Hard Four, Little Joe.", " Hard Four, Backdoor Little Joe!"};


    // Constructors
    public Game(Player[] players, int stakes, int startingPlayerIndex) {
        this.players = players;
        this.stakes = stakes;
        if (startingPlayerIndex == 0) {
            activePlayer = players[0];
            inactivePlayer = players[1];
        }
        else {
            activePlayer = players[1];
            inactivePlayer = players[0];
        }
        roll = new Roll();
        sideBet = new SideBet();
    }

    // Methods
    public Player run() {

        ConsoleService.consoleServiceInstance.printBlankLine();
        ConsoleService.consoleServiceInstance.printString(activePlayer.getDisplayName() + " is coming out...");
        roll.newRoll();
        ConsoleService.consoleServiceInstance.printString(roll.getResult() + " " + roll.getName() + ".");

        if (roll.getResult() == 7 || roll.getResult() == 11) {
            winningPlayer = activePlayer;
            losingPlayer = inactivePlayer;
            winningPlayer.setBankroll(winningPlayer.getBankroll() + stakes);
            losingPlayer.setBankroll(losingPlayer.getBankroll() - stakes);

            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString(winningPlayer.getDisplayName() + " wins!");
            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString("Players current bankrolls:");

            for (Player player : players) {
                ConsoleService.consoleServiceInstance.printString(player.getDisplayName() + ": $" + player.getBankroll());
            }

            return activePlayer;
        }
        else if (roll.getResult() == 2 || roll.getResult() == 3 ||
                roll.getResult() == 12) {
            winningPlayer = inactivePlayer;
            losingPlayer = activePlayer;
            winningPlayer.setBankroll(winningPlayer.getBankroll() + stakes);
            losingPlayer.setBankroll(losingPlayer.getBankroll() - stakes);

            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString(winningPlayer.getDisplayName() + " wins!");
            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString("Players current bankrolls:");

            for (Player player : players) {
                ConsoleService.consoleServiceInstance.printString(player.getDisplayName() + ": $" + player.getBankroll());
            }

            return activePlayer;
        }
        else {
            point = roll.getResult();

            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString("The point is " + point + ".");

            sideBet.askForSideBet();

        }

        do {

            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString(activePlayer.getDisplayName() + " is rolling...");
            roll.newRoll();
            ConsoleService.consoleServiceInstance.printString(roll.getResult() + " " + roll.getName() + ".");
            if (sideBet.isSideBetActive()) {
                sideBet.checkSideBet(point,roll.getResult());
                if (sideBet.didActivePlayerWin()) {
                    activePlayer.setBankroll(activePlayer.getBankroll() + stakes);
                    inactivePlayer.setBankroll(inactivePlayer.getBankroll() - stakes);
                    sideBet.setSideBetActive(false);

                    ConsoleService.consoleServiceInstance.printBlankLine();
                    ConsoleService.consoleServiceInstance.printString(activePlayer.getDisplayName() + " wins the side bet!");
                    ConsoleService.consoleServiceInstance.printBlankLine();
                    ConsoleService.consoleServiceInstance.printString("Players current bankrolls:");

                    for (Player player : players) {
                        ConsoleService.consoleServiceInstance.printString(player.getDisplayName() + ": $" + player.getBankroll());
                    }
                    if (roll.getResult() != point && roll.getResult() != 7) {
                        sideBet.askForSideBet();
                    }

                }
                else if (sideBet.didInactivePlayerWin()) {
                    inactivePlayer.setBankroll(inactivePlayer.getBankroll() + stakes);
                    activePlayer.setBankroll(activePlayer.getBankroll() - stakes);
                    sideBet.setSideBetActive(false);

                    ConsoleService.consoleServiceInstance.printBlankLine();
                    ConsoleService.consoleServiceInstance.printString(inactivePlayer.getDisplayName() + " wins the side bet!");
                    ConsoleService.consoleServiceInstance.printBlankLine();
                    ConsoleService.consoleServiceInstance.printString("Players current bankrolls:");

                    for (Player player : players) {
                        ConsoleService.consoleServiceInstance.printString(player.getDisplayName() + ": $" + player.getBankroll());
                    }
                    if (roll.getResult() != point && roll.getResult() != 7) {
                        sideBet.askForSideBet();
                    }
                }
            }

        } while (roll.getResult() != point && roll.getResult() != 7);

        if (roll.getResult() == point) {
            winningPlayer = activePlayer;
            losingPlayer = inactivePlayer;
            winningPlayer.setBankroll(winningPlayer.getBankroll() + stakes);
            losingPlayer.setBankroll(losingPlayer.getBankroll() - stakes);

            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString(winningPlayer.getDisplayName() + " wins the point!");
            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString("Players current bankrolls:");

            for (Player player : players) {
                ConsoleService.consoleServiceInstance.printString(player.getDisplayName() + ": $" + player.getBankroll());
            }

        }
        else {
            winningPlayer = inactivePlayer;
            losingPlayer = activePlayer;
            activePlayer = winningPlayer;
            inactivePlayer = losingPlayer;
            winningPlayer.setBankroll(winningPlayer.getBankroll() + stakes);
            losingPlayer.setBankroll(losingPlayer.getBankroll() - stakes);

            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString(winningPlayer.getDisplayName() + " wins the point!");
            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString("Players current bankrolls:");

            for (Player player : players) {
                ConsoleService.consoleServiceInstance.printString(player.getDisplayName() + ": $" + player.getBankroll());
            }

        }
        return activePlayer;
    }

    public Player itWasAGoodDay() {
        winningPlayer = activePlayer;
        losingPlayer = inactivePlayer;

        for (int i = 0; i < 6; i++) {
            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString(activePlayer.getDisplayName() + " is coming out...");
            ConsoleService.consoleServiceInstance.printString(IWAGD_ROLL_ARRAY[i] + IWAGD_ROLL_NAME_ARRAY[i]);

            winningPlayer.setBankroll(winningPlayer.getBankroll() + stakes);
            losingPlayer.setBankroll(losingPlayer.getBankroll() - stakes);

            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString(winningPlayer.getDisplayName() + " wins!");
            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString("Players current bankrolls:");

            for (Player player : players) {
                ConsoleService.consoleServiceInstance.printString(player.getDisplayName() + ": $" + player.getBankroll());
            }

            ConsoleService.consoleServiceInstance.printBlankLine();
            ConsoleService.consoleServiceInstance.printString(activePlayer.getDisplayName() + " is the shooter, do you want to keep playing?");
            ConsoleService.consoleServiceInstance.printString("yes");

        }

        ConsoleService.consoleServiceInstance.printBlankLine();
        ConsoleService.consoleServiceInstance.printString(activePlayer.getDisplayName() + " is coming out...");
        ConsoleService.consoleServiceInstance.printString(IWAGD_ROLL_ARRAY[6] + IWAGD_ROLL_NAME_ARRAY[6]);

        point = IWAGD_ROLL_ARRAY[6];

        ConsoleService.consoleServiceInstance.printBlankLine();
        ConsoleService.consoleServiceInstance.printString("The point is " + point + ".");
        ConsoleService.consoleServiceInstance.printBlankLine();

        ConsoleService.consoleServiceInstance.printString(activePlayer.getDisplayName() + " is rolling...");
        ConsoleService.consoleServiceInstance.printString(IWAGD_ROLL_ARRAY[7] + IWAGD_ROLL_NAME_ARRAY[7]);
        ConsoleService.consoleServiceInstance.printBlankLine();

        winningPlayer.setBankroll(winningPlayer.getBankroll() + stakes);
        losingPlayer.setBankroll(losingPlayer.getBankroll() - stakes);

        ConsoleService.consoleServiceInstance.printString(winningPlayer.getDisplayName() + " wins the point!");
        ConsoleService.consoleServiceInstance.printBlankLine();
        ConsoleService.consoleServiceInstance.printString("Players current bankrolls:");

        for (Player player : players) {
            ConsoleService.consoleServiceInstance.printString(player.getDisplayName() + ": $" + player.getBankroll());
        }

        ConsoleService.consoleServiceInstance.printBlankLine();

        return activePlayer;
    }
}
