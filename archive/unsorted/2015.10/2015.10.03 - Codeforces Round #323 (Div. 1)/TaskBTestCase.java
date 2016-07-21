package tasks;

import net.egork.chelper.task.Test;
import net.egork.chelper.tester.TestCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class TaskBTestCase {
    @TestCase
    public Collection<Test> createTests() {
        final int testno = 100;
        final int maxmul = 5000;
        final int max = 300;
        final Random rng = new Random(58);
        List<Test> tests = new ArrayList<>(testno);
        for (int i = 0; i < testno; ++i) {
            int n = rng.nextInt(100) + 1;
            int t = rng.nextInt(maxmul / n) + 1;
            StringBuilder sb = new StringBuilder();
            sb.append(n);
            sb.append(' ');
            sb.append(t);
            sb.append('\n');
            int[] a = new int[n];
            for (int j = 0; j < n; ++j) {
                a[j] = rng.nextInt(max) + 1;
                sb.append(a[j]);
                sb.append(' ');
            }
            tests.add(new Test(sb.toString(), bf(n, t, a)));
        }
        return tests;
    }

    private String bf(int n, int t, int[] a) {
        int answer = 0;
        int[] dp = new int[n * t];
        for (int i = 0; i < dp.length; ++i) {
            for (int j = 0; j <= i; ++j) {
                if (a[j % n] <= a[i % n]) {
                    dp[i] = Math.max(dp[i], dp[j]);
                }
            }
            ++dp[i];
            answer = Math.max(answer, dp[i]);
        }
        return Integer.toString(answer);
    }
}
