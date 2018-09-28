package task;

import net.egork.chelper.task.Test;
import net.egork.chelper.tester.TestCase;

import java.util.*;

public class TaskETestCase {
    @TestCase
    public Collection<Test> createTests() {
        int testsQty = 10000;
        List<Test> tests = new ArrayList<>(testsQty);
        Random rng = new Random(58);
        for (int t = 0; t < testsQty; ++t) {
            long number;
            number = getNumber(rng);
            long max = rng.nextInt(Integer.MAX_VALUE);
            double prob = rng.nextDouble();
            long min = prob <= 0.1 ? 0 : prob < 0.2 ? max : rng.nextInt(Integer.MAX_VALUE);
            if (min > max) {
                long tmp = min;
                min = max;
                max = tmp;
            }
            int result = bruteForce("" + number, min, max);
            tests.add(new Test(number + " " + min + " " + max, "" + result));
        }
        return tests;
    }

    private long getNumber(Random rng) {
        long number;
        do {
            number = rng.nextLong();
        } while (number < 0);
        return number;
    }

    private static final int MODULO = 998244353;
    private int bruteForce(String number, long min, long max) {
        int[] dp = new int[number.length() + 1];
        dp[0] = 1;
        for (int i = 0; i < number.length(); ++i) {
            if (number.charAt(i) == '0') {
                if (min == 0) {
                    dp[i + 1] = (dp[i + 1] + dp[i]) % MODULO;
                }
                continue;
            }
            int j = i + 1;
            long num = number.charAt(i) - '0';
            while (j < number.length() && num < min) {
                num = 10 * num + number.charAt(j) - '0';
                j++;
            }
            while (j < number.length() && num <= max) {
                dp[j] = (dp[j] + dp[i]) % MODULO;
                num = 10 * num + number.charAt(j) - '0';
                j++;
            }
            if (j == number.length() && min <= num && num <= max) {
                dp[j] = (dp[j] + dp[i]) % MODULO;
            }
        }
        return dp[number.length()];
    }
}
