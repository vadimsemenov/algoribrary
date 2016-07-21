package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

public class SnakesAndLadders {
    private static class CaseSolver implements Callable<CaseSolver> {
        static final int MODULO = 1_000_000_000 + 7;

        int counter;
        int[] xs, hs;

        private void solve() {
            Integer[] order = new Integer[counter + 1];
            for (int i = 0; i <= counter; ++i) order[i] = i;
            Arrays.sort(order, new Comparator<Integer>() {
                @Override
                public int compare(Integer first, Integer second) {
                    int cmp = Integer.compare(xs[first], xs[second]);
                    if (cmp == 0) cmp = Integer.compare(hs[first], hs[second]);
                    return cmp;
                }
            });
            int[][] stack = new int[counter + 1][2];
            int top = 0;
            long answer = 0;
            for (int i : order) {
                while (top > 0 && stack[top - 1][1] < hs[i]) {
                    long s1 = 0;
                    long s2 = 0;
                    int n = 0;
                    while (n < top && stack[top - 1][1] == stack[top - n - 1][1]) {
                        ++n;
                        s1 += stack[top - n][0];
                        s2 += (long) stack[top - n][0] * stack[top - n][0] % MODULO;
                    }
                    s1 %= MODULO;
                    s2 %= MODULO;
                    answer += (s2 * n % MODULO + MODULO - s1 * s1 % MODULO) % MODULO;
                    top -= n;
                }
                stack[top][0] = xs[i];
                stack[top][1] = hs[i];
                ++top;
            }
            output = "" + (answer % MODULO);
        }

        public void readInput(final InputReader in) {
            counter = in.nextInt();
            xs = new int[counter + 1];
            hs = new int[counter + 1];
            for (int i = 0; i < counter; i++) {
                xs[i] = in.nextInt();
                hs[i] = in.nextInt();
            }
            xs[counter] = hs[counter] = Integer.MAX_VALUE;
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