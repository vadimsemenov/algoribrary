package tasks;



import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

public final class TaskF {
    private static final long INF = Long.MAX_VALUE / 3;

    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int[] powers = in.nextIntArray(count);
        int[] procQty = in.nextIntArray(count);
        Task[] tasks = new Task[count];
        for (int i = 0; i < count; ++i) {
            tasks[i] = new Task(powers[i], procQty[i]);
        }
        Arrays.sort(tasks, Task.COMPARATOR);
        long[][][] dp = new long[2][count + 1][Arrays.stream(procQty).sum() + 1];
        int cur = 0;
        for (long[] d : dp[cur]) {
            Arrays.fill(d, INF);
        }
        dp[cur][0][0] = 0;
        int maxProcs = 0;

        for (int i = 0; i < count; ) {
            int next = cur ^ 1;
            for (long[] d : dp[next]) {
                Arrays.fill(d, INF);
            }
            int j = i + 1;
            int procsSum = tasks[i].procs;
            long powerSum = tasks[i].power;
            while (j < count && tasks[j].power == tasks[i].power) {
                procsSum += tasks[j].procs;
                powerSum += tasks[j].power;
                j++;
            }
            int nextMaxProcs = maxProcs;

            for (int greater = 0; greater <= i; ++greater) {
                for (int procs = 0; procs <= maxProcs; ++procs) {
                    int adProcs = procsSum;
                    long adPower = powerSum;
                    int maxSecondPart = Math.min(greater, j - i);
                    for (int secondPart = 0; secondPart <= maxSecondPart; ++secondPart) {
                        int firstPart = j - i - secondPart;
                        assert firstPart >= 0;
                        int nextGreater = greater - secondPart + firstPart;
                        assert nextGreater >= 0;
                        int nextProcs = procs + adProcs;
                        assert nextProcs >= 0;
                        dp[next][nextGreater][nextProcs] = Math.min(dp[next][nextGreater][nextProcs], dp[cur][greater][procs] + adPower);
                        if (dp[next][nextGreater][nextProcs] < INF) {
                            nextMaxProcs = Math.max(nextMaxProcs, procs + adProcs);
                        }
                        adProcs -= tasks[j - 1 - secondPart].procs;
                        adPower -= tasks[j - 1 - secondPart].power;
                    }
                }
            }

            maxProcs = nextMaxProcs;
            i = j;
            cur = next;
        }

        long answer = INF;
        for (int greater = 0; greater <= count; ++greater) {
            for (int procs = 1; procs <= maxProcs; ++procs) {
                long totalPower = dp[cur][greater][procs];
                if (totalPower < INF) {
                    answer = Math.min(answer, (1000 * totalPower + procs - 1) / procs);
                }
            }
        }
        out.println(answer);
    }

    static class Task {
        static final Comparator<Task> COMPARATOR = Comparator
                .comparingInt(Task::getPower)
                .thenComparingInt(Task::getProcs)
                .reversed();

        final int power;
        final int procs;

        Task(int power, int procs) {
            this.power = power;
            this.procs = procs;
        }

        public int getPower() {
            return power;
        }

        public int getProcs() {
            return procs;
        }
    }
}
