package task;

import net.egork.chelper.task.Test;
import net.egork.chelper.tester.TestCase;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TaskHTestCase {
    private static final int MAX_VALUE = 5032107;
    @TestCase
    public Collection<Test> createTests() {
        final int testQty = 50;
        Random rng = new Random(58);
        List<Test> testList = new ArrayList<>(testQty);
        int maxLength = 10;
        for (int t = 0; t < testQty; ++t) {
            int length = rng.nextInt(maxLength) + 2;
            int[] array = IntStream.generate(() -> rng.nextInt(MAX_VALUE) + 1)
                    .limit(length)
                    .toArray();
            int queries = length * (length - 1) / 2;
            StringBuilder input = new StringBuilder();
            input.append(length).append(' ').append(queries).append('\n');
            for (int i : array) {
                input.append(i).append(' ');
            }
            input.setCharAt(input.length() - 1, '\n');
            StringBuilder output = new StringBuilder();
            for (int l = 1; l < length; ++l) {
                for (int r = l + 1; r <= length; ++r) {
                    input.append(l).append(' ').append(r).append('\n');
                    output.append(bruteForce(array, l, r)).append('\n');
                }
            }
            testList.add(new Test(input.toString(), output.toString()));
        }
        return testList;
    }

    private int bruteForce(int[] array, int l, int r) {
        int answer = 100500;
        for (int i = l - 1; i < r; ++i) {
            for (int j = i + 1; j < r; ++j) {
                long product = (long) array[i] * array[j];
                int count = 0;
                for (int prime = 2; (long) prime * prime <= product; ++prime) {
                    int power = 0;
                    while (product % prime == 0) {
                        power++;
                        product /= prime;
                    }
                    count += power % 2;
                }
                if (product > 1) count++;
                answer = Math.min(answer, count);
            }
        }
        return answer;
    }
}
