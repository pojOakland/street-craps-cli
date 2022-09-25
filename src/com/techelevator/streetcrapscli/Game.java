package com.techelevator.streetcrapscli;

public class Game {

    // Properties
    private Player[] players;
    private int stakes;
    private int point;
    private int startingPlayerIndex;
    private Player activePlayer;
    private Player inactivePlayer;
    private Player winningPlayer;
    private Player losingPlayer;
    private Roll roll;

    // Constants
    private static final Integer[] IWAGD_ROLL_ARRAY = new Integer[] {7,7,11,7,11,7,4,4};
    private static final String[] IWAGD_ROLL_NAME_ARRAY = new String[] {" Seven Out.", " Seven Out.", " YO.",
            " Seven Out.", " YO.", " Seven Out.", " Hard Four, Little Joe.", " Hard Four, Backdoor Little Joe!"};



    // Constructors
    public Game(Player[] players, int stakes, int startingPlayerIndex) {
        this.players = players;
        this.stakes = stakes;
        this.startingPlayerIndex = startingPlayerIndex;
        if (startingPlayerIndex == 0) {
            activePlayer = players[0];
            inactivePlayer = players[1];
        }
        else {
            activePlayer = players[1];
            inactivePlayer = players[0];
        }
        roll = new Roll();
    }

    // Methods
    public Player run() {

        System.out.println("");
        System.out.println(activePlayer.getName() + " is coming out...");
        roll.newRoll();
        System.out.println(roll.getResult() + " " + roll.getName() + ".");

        if (roll.getResult() == 7 || roll.getResult() == 11) {
            winningPlayer = activePlayer;
            losingPlayer = inactivePlayer;
            winningPlayer.setBankroll(winningPlayer.getBankroll() + stakes);
            losingPlayer.setBankroll(losingPlayer.getBankroll() - stakes);

            System.out.println(winningPlayer.getName() + " wins!");
            System.out.println("");
            System.out.println("Players current bankrolls:");

            for (Player player : players) {
                System.out.println(player.getName() + ": $" + player.getBankroll());
            }

            return activePlayer;
        }
        else if (roll.getResult() == 2 || roll.getResult() == 3 ||
                roll.getResult() == 12) {
            winningPlayer = inactivePlayer;
            losingPlayer = activePlayer;
            winningPlayer.setBankroll(winningPlayer.getBankroll() + stakes);
            losingPlayer.setBankroll(losingPlayer.getBankroll() - stakes);

            System.out.println(winningPlayer.getName() + " wins!");
            System.out.println("");
            System.out.println("Players current bankrolls:");

            for (Player player : players) {
                System.out.println(player.getName() + ": $" + player.getBankroll());
            }

            return activePlayer;
        }
        else {
            point = roll.getResult();

            System.out.println("");
            System.out.println("The point is " + point + ".");
            System.out.println("");

        }

        do {

            System.out.println(activePlayer.getName() + " is rolling...");
            roll.newRoll();
            System.out.println(roll.getResult() + " " + roll.getName() + ".");
            System.out.println("");

        } while (roll.getResult() != point && roll.getResult() != 7);

        if (roll.getResult() == point) {
            winningPlayer = activePlayer;
            losingPlayer = inactivePlayer;
            winningPlayer.setBankroll(winningPlayer.getBankroll() + stakes);
            losingPlayer.setBankroll(losingPlayer.getBankroll() - stakes);

            System.out.println(winningPlayer.getName() + " wins!");
            System.out.println("");
            System.out.println("Players current bankrolls:");
            for (Player player : players) {
                System.out.println(player.getName() + ": $" + player.getBankroll());
            }

            return activePlayer;
        }
        else {
            winningPlayer = inactivePlayer;
            losingPlayer = activePlayer;
            activePlayer = winningPlayer;
            inactivePlayer = losingPlayer;
            winningPlayer.setBankroll(winningPlayer.getBankroll() + stakes);
            losingPlayer.setBankroll(losingPlayer.getBankroll() - stakes);

            System.out.println(winningPlayer.getName() + " wins!");
            System.out.println("");
            System.out.println("Players current bankrolls:");
            for (Player player : players) {
                System.out.println(player.getName() + ": $" + player.getBankroll());
            }

            return activePlayer;
        }
    }

    public Player itWasAGoodDay() {
        winningPlayer = activePlayer;
        losingPlayer = inactivePlayer;

        for (int i = 0; i < 6; i++) {
            System.out.println("");
            System.out.println(activePlayer.getName() + " is coming out...");
            System.out.println(IWAGD_ROLL_ARRAY[i].toString() + IWAGD_ROLL_NAME_ARRAY[i]);


            winningPlayer.setBankroll(winningPlayer.getBankroll() + stakes);
            losingPlayer.setBankroll(losingPlayer.getBankroll() - stakes);

            System.out.println(winningPlayer.getName() + " wins!");
            System.out.println("");
            System.out.println("Players current bankrolls:");

            for (Player player : players) {
                System.out.println(player.getName() + ": $" + player.getBankroll());
            }

            System.out.println("");
            System.out.println(activePlayer.getName() + " is the shooter, do you want to keep playing?");
            System.out.println("yes");

        }

        System.out.println("");
        System.out.println(activePlayer.getName() + " is coming out...");
        System.out.println(IWAGD_ROLL_ARRAY[6].toString() + IWAGD_ROLL_NAME_ARRAY[6]);

        point = IWAGD_ROLL_ARRAY[6];

        System.out.println("");
        System.out.println("The point is " + point + ".");
        System.out.println("");

        System.out.println(activePlayer.getName() + " is rolling...");
        System.out.println(IWAGD_ROLL_ARRAY[7].toString() + IWAGD_ROLL_NAME_ARRAY[7]);
        System.out.println("");

        winningPlayer.setBankroll(winningPlayer.getBankroll() + stakes);
        losingPlayer.setBankroll(losingPlayer.getBankroll() - stakes);

        System.out.println(winningPlayer.getName() + " wins!");
        System.out.println("");
        System.out.println("Players current bankrolls:");
        for (Player player : players) {
            System.out.println(player.getName() + ": $" + player.getBankroll());
        }

        System.out.println("");
        System.out.println(activePlayer.getName() + " is the shooter, do you want to keep playing?");

        return activePlayer;
    }
}