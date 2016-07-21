package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Yachtzee {
    private static class CaseSolver implements Callable<CaseSolver> {
        private int from, to;
        private int counter;
        private int[] cost;

        private void solve() {
            long sum = 0;
            for (int cc : cost) {
                sum += cc;
            }
            if (from > sum) {
                long sub = from / sum * sum;
                from -= sub;
                to -= sub;
            }
            double result = 0.0;
            int paid = 0;
            for (int i = 0; i < counter && paid < to; ++i) {
                if (paid + cost[i] < from) {
                    paid += cost[i];
                } else {
                    int l = Math.max(paid, from);
                    int r = Math.min(paid + cost[i], to);
                    double remainder = (r - l) / 2.0;
                    if (l != paid) remainder += l - paid;
                    result += remainder * (r - l) / (to - from);
                    paid += cost[i];
                }
            }
            if (paid < to) {
                assert paid == sum;
                if (paid + sum <= to) {
                    long mul = (to - paid) / sum;
                    for (int cc : cost) {
                        result += cc / 2.0 * mul * cc / (to - from);
                    }
                    paid += sum * mul;
                }
                assert paid >= from;
                for (int i = 0; i < counter && paid < to; ++i) {
                    int l = paid;
                    int r = Math.min(paid + cost[i], to);
                    result += (r - l) / 2.0 * (r - l) / (to - from);
                    paid += cost[i];
                }
            }
            output = String.format("%.9f", result);
        }

        public void readInput(final InputReader in) {
            final int MIN = 1;
            final int MAX = 1_000_000_000;
            counter = in.nextInt();
            from = in.nextInt();
            to = in.nextInt();
            assert 0 <= from && from < to && to <= MAX;
            cost = in.nextIntArray(counter);
            for (int cc : cost) assert MIN <= cc && cc <= MAX;
        }

        public void printOutput(final PrintWriter out) {
            out.println("Case #" + testNumber + ": " + output);
        }

        @Override
        public CaseSolver call() {
            System.err.println("In process testcase #" + testNumber);
            solve();
            System.err.println("Done testcase #" + testNumber + " (" + output + ")");
            return this;
        }

        public CaseSolver(final int testNumber) {
            this.testNumber = testNumber;
        }

        private final int testNumber;
        private String output;
    }

    public void solve(int _, final InputReader in, final PrintWriter out) {
        final long startTime = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(THREADS_QTY);
        int tests = in.nextInt();
        List<Future<CaseSolver>> futures = new ArrayList<>(tests);
        for (int testCase = 1; testCase <= tests; ++testCase) {
            CaseSolver solver = new CaseSolver(testCase);
            solver.readInput(in);
            futures.add(pool.submit(solver));
        }
        pool.shutdown();
        for (Future<CaseSolver> future : futures) {
            CaseSolver solver;
            try {
                solver = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                continue;
            }
            solver.printOutput(out);
        }
        final long finishTime = System.nanoTime();
        System.err.println("Complete in " + (finishTime - startTime) / 1_000_000_000. + "s");
    }

    private static final int THREADS_QTY = 4;
}