package tasks;

import java.util.Arrays;

public class SplitStoneGame {
    public String winOrLose(int[] number) {
        Arrays.sort(number);
        if (number[number.length - 1] == 1) return "LOSE";
        if (number.length < 3) return "LOSE";
        return number.length % 2 == 1 ? "WIN" : "LOSE";
    }
}
