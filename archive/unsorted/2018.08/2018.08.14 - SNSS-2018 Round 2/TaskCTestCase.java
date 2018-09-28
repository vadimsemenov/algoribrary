package task;

import net.egork.chelper.task.Test;
import net.egork.chelper.tester.TestCase;

import java.util.*;
import java.util.stream.Collectors;

public class TaskCTestCase {
    private static final long[] pow = new long[256];
    static {
        for (int i = 1; i < 256; ++i) {
            pow[i] = pow(i);
        }
    }

    @TestCase
    public Collection<Test> createTests() {
        if (true) return Collections.emptyList();
        Random rng = new Random(58);
        Map<Long, List<String>> map = new HashMap<>();
        int testsQty = 200;
        for (int t = 0; t < testsQty; ++t) {
            long r = rng.nextInt(Integer.MAX_VALUE);
            while (r < 0 || Long.toString(r).length() > 12 || map.containsKey(r)) {
                r = rng.nextLong();
            }
            map.put(r, new ArrayList<>());
        }
        for (int a = 1; a < 256; ++a) {
            System.err.println(a);
            for (int b = 1; b < 256; ++b) {
                long bb = b + pow[b] * a;
                for (int c = 1; c < 256; ++c) {
                    long cc = c + pow[c] * bb;
                    for (int d = 1; d < 256; ++d) {
                        long string = d + pow[d] * cc;
//                        long string = Long.parseLong("" + a + b + c + d);
                        List<String> list = map.get(string);
                        if (list != null) {
                            list.add(a + "." + b + "." + c + "." + d);
                        }
                    }
                }
            }
        }
        List<Test> tests = new ArrayList<>();
        for (Map.Entry<Long, List<String>> entry : map.entrySet()) {
            tests.add(new Test("" + entry.getKey(), String.join("\n", entry.getValue())));
        }
        return tests;
    }

    private static long pow(int number) {
        int pow = 10;
        while (pow <= number) {
            pow *= 10;
        }
        return pow;
    }
}
