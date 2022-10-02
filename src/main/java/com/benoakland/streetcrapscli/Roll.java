package com.benoakland.streetcrapscli;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Roll {

    // Properties
    private int result;
    private String name;

    // Constants
    public static final Map<Integer, String> rollMap = new HashMap<>(){
        {
            put(11,"Crap Two, Snake Eyes");
            put(12, "Crap Three, Australian Yo");
            put(21, "Crap Three, Australian Yo");
            put(13, "Easy Four");
            put(31, "Easy Four");
            put(22, "Hard Four, Little Joe");
            put(14, "Easy Five");
            put(41, "Easy Five");
            put(23, "Easy Five");
            put(32, "Easy Five");
            put(15, "Easy Six");
            put(51, "Easy Six");
            put(24, "Easy Six");
            put(42, "Easy Six");
            put(33, "Hard Six, Jimmie Hicks");
            put(16, "Seven Out");
            put(61, "Seven Out");
            put(25, "Seven Out");
            put(52, "Seven Out");
            put(34, "Seven Out");
            put(43, "Seven Out");
            put(26, "Easy Eight");
            put(62, "Easy Eight");
            put(35, "Easy Eight");
            put(53, "Easy Eight");
            put(44, "Hard Eight");
            put(36, "Centerfield Nine");
            put(63, "Centerfield Nine");
            put(45, "Centerfield Nine");
            put(54, "Centerfield Nine");
            put(46, "Easy Ten");
            put(64, "Easy Ten");
            put(55, "Hard Ten, Puppy Paws");
            put(56, "YO");
            put(65, "YO");
            put(66, "Hard Twelve, Boxcars");

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
        Integer die1 = ThreadLocalRandom.current().nextInt(1, 7);
        Integer die2 = ThreadLocalRandom.current().nextInt(1, 7);
        String s = die1.toString() + die2.toString();
        Integer i = Integer.parseInt(s);
        result = die1 + die2;
        name = rollMap.get(i);
    }
}
