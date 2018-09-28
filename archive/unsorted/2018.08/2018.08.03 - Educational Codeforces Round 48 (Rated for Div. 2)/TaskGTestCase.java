package task;

import net.egork.chelper.task.Test;
import net.egork.chelper.tester.TestCase;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TaskGTestCase {
    @TestCase
    public Collection<Test> createTests() {
        List<Test> tests = new ArrayList<>();
        final int limit = 100;
        for (int count = 1; count < 30; ++count) {
            int[] rangs = generate(count, limit);
            for (int gcd = 1; gcd < 10; ++gcd) {
                for (int lcm = gcd; lcm < limit; lcm += gcd) {
                    int answer = bruteForce(rangs, gcd, lcm);
                    tests.add(new Test(buildInput(count, gcd, lcm, rangs), "" + answer));
                }
            }
        }
        return tests;
    }

    private String buildInput(int count, int gcd, int lcm, int[] rangs) {
        StringBuilder builder = new StringBuilder();
        builder.append(count)
                .append(' ')
                .append(gcd)
                .append(' ')
                .append(lcm)
                .append('\n');
        for (int rang : rangs) {
            builder.append(rang)
                    .append(' ');
        }
        builder.setCharAt(builder.length() - 1, '\n');
        return builder.toString();
    }

    private int bruteForce(int[] rangs, int gcd, int lcm) {
        int answer = 0;
        for (int i = 0; i < rangs.length; ++i) {
            for (int j = 0; j < rangs.length; ++j) {
                if (existsV(rangs[i], rangs[j], gcd, lcm)) {
                    answer++;
                }
            }
        }
        return answer;
    }

    private boolean existsV(int ai, int aj, int gcd, int lcm) {
        for (int v = gcd; v <= lcm; v += gcd) {
            if (gcd(v, ai) == gcd && lcm(v, aj) == lcm) {
                return true;
            }
        }
        return false;
    }

    private Random rng = new Random(58);
    private int[] generate(int count, int limit) {
        int[] array = new int[count];
        for (int i = 0; i < count; ++i) {
            array[i] = rng.nextInt(limit) + 1;
        }
        return array;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    private int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }
}
