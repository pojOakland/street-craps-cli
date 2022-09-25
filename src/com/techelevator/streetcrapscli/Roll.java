package com.techelevator.streetcrapscli;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Roll {

    // Properties
    private int die1;
    private int die2;
    private int[] rollArray = new int[2];
    private int result;
    private String name;

    // Constants
    private static final int[] two11 = new int[] {1,1};
    private static final int[] three12 = new int[] {1,2};
    private static final int[] three21 = new int[] {2,1};
    private static final int[] four13 = new int[] {1,3};
    private static final int[] four31 = new int[] {3,1};
    private static final int[] four22 = new int[] {2,2};
    private static final int[] five14 = new int[] {1,4};
    private static final int[] five41 = new int[] {4,1};
    private static final int[] five32 = new int[] {3,2};
    private static final int[] five23 = new int[] {2,3};
    private static final int[] six15 = new int[] {1,5};
    private static final int[] six51 = new int[] {5,1};
    private static final int[] six42 = new int[] {4,2};
    private static final int[] six24 = new int[] {2,4};
    private static final int[] six33 = new int[] {3,3};
    private static final int[] seven16 = new int[] {1,6};
    private static final int[] seven61 = new int[] {6,1};
    private static final int[] seven25 = new int[] {2,5};
    private static final int[] seven52 = new int[] {5,2};
    private static final int[] seven34 = new int[] {3,4};
    private static final int[] seven43 = new int[] {4,3};
    private static final int[] eight26 = new int[] {2,6};
    private static final int[] eight62 = new int[] {6,2};
    private static final int[] eight53 = new int[] {5,3};
    private static final int[] eight35 = new int[] {3,5};
    private static final int[] eight44 = new int[] {4,4};
    private static final int[] nine36 = new int[] {3,6};
    private static final int[] nine63 = new int[] {6,3};
    private static final int[] nine54 = new int[] {5,4};
    private static final int[] nine45 = new int[] {4,5};
    private static final int[] ten46 = new int[] {4,6};
    private static final int[] ten64 = new int[] {6,4};
    private static final int[] ten55 = new int[] {5,5};
    private static final int[] eleven56 = new int[] {5,6};
    private static final int[] eleven65 = new int[] {6,5};
    private static final int[] twelve66 = new int[] {6,6};
    private static final Map<int[], String> rollMap = new HashMap<>(){
        {
            put(two11,"Crap Two, Snake Eyes");
            put(three12, "Crap Three, Australian Yo");
            put(three21, "Crap Three, Australian Yo");
            put(four13, "Easy Four");
            put(four31, "Easy Four");
            put(four22, "Hard Four, Little Joe");
            put(five14, "Easy Five");
            put(five41, "Easy Five");
            put(five23, "Easy Five");
            put(five32, "Easy Five");
            put(six15, "Easy Six");
            put(six51, "Easy Six");
            put(six24, "Easy Six");
            put(six42, "Easy Six");
            put(six33, "Hard Six, Jimmie Hicks");
            put(seven16, "Seven Out");
            put(seven61, "Seven Out");
            put(seven25, "Seven Out");
            put(seven52, "Seven Out");
            put(seven34, "Seven Out");
            put(seven43, "Seven Out");
            put(eight26, "Easy Eight");
            put(eight62, "Easy Eight");
            put(eight35, "Easy Eight");
            put(eight53, "Easy Eight");
            put(eight44, "Hard Eight");
            put(nine36, "Centerfield Nine");
            put(nine63, "Centerfield Nine");
            put(nine45, "Centerfield Nine");
            put(nine54, "Centerfield Nine");
            put(ten46, "Easy Ten");
            put(ten64, "Easy Ten");
            put(ten55, "Hard Ten, Puppy Paws");
            put(eleven56, "YO");
            put(eleven65, "YO");
            put(twelve66, "Hard Twelve, Boxcars");

        }
    };

    // Getters/Setters
    public int getResult() {
        return result;
    }

    public String getName() {
        return name;
    }

    // Methods
    public void newRoll() {
        this.die1 = ThreadLocalRandom.current().nextInt(1, 7);
        this.die2 = ThreadLocalRandom.current().nextInt(1, 7);
        rollArray[0] = die1;
        rollArray[1] = die2;
        result = die1 + die2;
        name = rollMap.get(rollArray);
    }
}
