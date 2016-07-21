package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CarnivalCoins {
    private static class CaseSolver implements Callable<CaseSolver> {
        int counter;
        int threshold;
        double p;

        private void solve() {
            double q = 1 - p;
            double[][] prob = new double[counter + 1][];
            prob[0] = new double[]{1.0};
            for (int i = 1; i <= counter; ++i) {
                prob[i] = new double[i + 1];
                prob[i][0] = prob[i - 1][0] * q;
                prob[i][i] = prob[i - 1][i - 1] * p;
                for (int j = 1; j < i; ++j) {
                    prob[i][j] = prob[i - 1][j - 1] * p + prob[i - 1][j] * q;
                }
            }
            double[] f = new double[counter + 1];
            for (int m = threshold; m <= counter; ++m) {
                for (int i = threshold; i <= m; ++i) {
                    f[m] += prob[m][i];
                }
            }
            double[] dp = new double[counter + 1];
            for (int i = threshold; i <= counter; ++i) {
                for (int k = threshold; k <= i; ++k) {
                    dp[i] = Math.max(dp[i], dp[i - k] + f[k]);
                }
            }
            output = "" + dp[counter];
        }

        public void readInput(final InputReader in) {
            counter = in.nextInt();
            threshold = in.nextInt();
            p = in.nextDouble();
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