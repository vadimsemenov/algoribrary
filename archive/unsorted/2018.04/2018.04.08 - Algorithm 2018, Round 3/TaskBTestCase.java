package tasks;

import net.egork.chelper.task.Test;
import net.egork.chelper.tester.TestCase;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TaskBTestCase {
    private static final int TESTS = 10;
    private static final int MAX_LENGTH = 42;
    private static final int MAX_VALUE = 1_000_000;
    private Random rng = new Random(58);

    @TestCase
    public Collection<Test> createTests() {
        int length = rng.nextInt(MAX_LENGTH) + 1;
        List<Test> tests = new ArrayList<>(TESTS);
        for (int test = 0; test < TESTS; ++test) {
            List<Integer> input = generate(length);
            int answer = bruteForce(input);

            StringBuilder inputString = new StringBuilder(input.size());
            inputString.append('\n');
            for (int i : input) {
                inputString.append(i);
                inputString.append(' ');
            }
            inputString.setCharAt(inputString.length() - 1, '\n');
            tests.add(new Test(inputString.toString(), "" + answer));
        }
        return Collections.emptyList();
    }

    private int bruteForce(List<Integer> input) {
        BigInteger last = BigInteger.ZERO;
        int answer = 0;
        for (int number : input) {
            BigInteger n = BigInteger.valueOf(number);
            if (n.compareTo(last) < 0) {
                if (number == 0) {
                    return -1;
                }
                while (n.compareTo(last) < 0) {
                    answer++;
                    n = n.shiftLeft(1);
                }
            }
        }
        return answer;
    }

    private List<Integer> generate(int length) {
        return IntStream
                .generate(() -> rng.nextInt(MAX_VALUE))
                .boxed()
                .limit(length)
                .collect(Collectors.toList());
    }
}
