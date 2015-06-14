package tasks;

import net.egork.chelper.task.Test;
import net.egork.chelper.tester.TestCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class TaskCTestCase {
    @TestCase
    public Collection<Test> createTests() {
        List<Test> list = new ArrayList<>();
        final int tests = 40;
        final Random rng = new Random(58);
        final int counter = 100;
        final int queries = 1000;
        final int maxd = 1000_000_000;
        final int maxh = 1000_000_000;
        for (int test = 0; test < tests; ++test) {
            int[] distance = new int[counter];
            int[] height = new int[counter];
            for (int i = 0; i < counter; ++i) {
                distance[i] = 1 + rng.nextInt(maxd);
                height[i] = 1 + rng.nextInt(maxh);
            }
            int[] from = new int[queries];
            int[] to = new int[queries];
            for (int i = 0; i < queries / 2; ++i) {
                to[i] = rng.nextInt(counter - 2) + 2;
                from[i] = rng.nextInt(to[i] - 1) + 2;
//                while (from[i] <= to[i] && counter - (to[i] - from[i] + 1) < 2) {
//                    to[i]--;
//                }
//                while (from[i] > to[i] && (from[i] - to[i] - 1) < 2) {
//                    if (to[i] > 1) to[i]--;
//                    else from[i]++;
//                }
            }
            for (int i = queries / 2; i < queries; ++i) {
                from[i] = rng.nextInt(counter - 3) + 4;
                to[i] = rng.nextInt(from[i] - 3) + 1;
            }
            StringBuilder input = new StringBuilder();
            input.append(counter);
            input.append(' ');
            input.append(queries);
            input.append('\n');
            for (int i = 0; i < counter; ++i) {
                if (i > 0) input.append(' ');
                input.append(distance[i]);
            }
            input.append('\n');
            for (int i = 0; i < counter; ++i) {
                if (i > 0) input.append(' ');
                input.append(height[i]);
            }
            input.append('\n');
            for (int i = 0; i < queries; ++i) {
                input.append(from[i]);
                input.append(' ');
                input.append(to[i]);
                input.append('\n');
            }
            list.add(new Test(input.toString(), bruteForce(counter, queries, distance, height, from, to)));
        }
        System.err.println("end of creating tests");
        return list;
    }

    String bruteForce(int counter, int queries, int[] distance, int[] height, int[] from, int[] to) {
        StringBuilder result = new StringBuilder();
        for (int q = 0; q < queries; ++q) {
            if (q > 0) result.append(' ');
            long answer = -1;
            for (int i = to[q] % counter; i != from[q] - 1; i = (i + 1) % counter) {
                long sum = 0;
                for (int j = (i + 1) % counter; j != from[q] - 1; j = (j + 1) % counter) {
                    sum += distance[(j + counter - 1) % counter];
                    answer = Math.max(answer, sum + 2L * (height[i] + height[j]));
                }
            }
            if (answer < 0) throw new AssertionError();
            result.append(answer);
        }
        return result.toString();
    }
}
