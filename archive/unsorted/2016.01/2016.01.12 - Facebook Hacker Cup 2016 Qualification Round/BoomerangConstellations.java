package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BoomerangConstellations {
    private static class CaseSolver implements Callable<CaseSolver> {
        int counter;
        int[] xs, ys;

        private void solve() {
            long answer = 0;
            for (int i = 0; i < counter; ++i) {
                Map<Long, Integer> cnt = new HashMap<>();
                for (int j = 0; j < counter; ++j) if (i != j) {
                    long dist = distance(i, j);
                    Integer qty = cnt.get(dist);
                    if (qty == null) {
                        qty = 0;
                    }
                    cnt.put(dist, qty + 1);
                }
                for (Map.Entry<Long, Integer> entry : cnt.entrySet()) {
                    int qty = entry.getValue();
                    answer += (long) qty * (qty - 1) / 2;
                }
            }
            output = "" + answer;
        }

        private long distance(int i, int j) {
            long dx = xs[i] - xs[j];
            long dy = ys[i] - ys[j];
            return dx * dx + dy * dy;
        }

        public void readInput(final InputReader in) {
            counter = in.nextInt();
            xs = new int[counter];
            ys = new int[counter];
            for (int i = 0; i < counter; ++i) {
                xs[i] = in.nextInt();
                ys[i] = in.nextInt();
            }
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
    }

    private static final int THREADS_QTY = 4;
}