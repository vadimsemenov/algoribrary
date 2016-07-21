package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TaskB {
    private static class CaseSolver implements Callable<CaseSolver> {
        int total, counter;
        double[] p;

        void solve() {
            double[] p = new double[counter];
            double best = 0;
            for (int _ = 0; _ < (1 << total); ++_) {
                if (Integer.bitCount(_) == counter) {
                    int ptr = 0;
                    for (int i = 0; i < total; ++i) {
                        if (((_ >>> i) & 1) != 0) {
                            p[ptr++] = this.p[i];
                        }
                    }
                    double res = 0;
                    for (int mask = 0; mask < (1 << counter); ++mask) {
                        if (Integer.bitCount(mask) != counter / 2) continue;
                        int sum = 0;
                        double prob = 1;
                        for (int i = 0; i < counter; ++i) {
                            if ((mask >>> i & 1) == 0) {
                                sum += 1;
                                prob *= p[i];
                            } else {
                                sum -= 1;
                                prob *= (1 - p[i]);
                            }
                        }
                        if (sum == 0) {
                            res += prob;
                        }
                    }
                    if (res > best) {
                        best = res;
                    }
                }
            }
            output = String.format("%.10f", best);
        }

        void readInput(InputReader in) {
            total = in.nextInt();
            counter = in.nextInt();
            p = new double[total];
            for (int i = 0; i < total; ++i) p[i] = in.nextDouble();
        }

        void printOutput(PrintWriter out) {
            out.println("Case #" + testNumber + ": " + output);
        }

        @Override
        public CaseSolver call() {
            System.err.println("In process testcase #" + testNumber);
            solve();
            System.err.println("Done testcase #" + testNumber + " (" + output + ")");
            return this;
        }

        CaseSolver(final int testNumber) {
            this.testNumber = testNumber;
        }

        private final int testNumber;
        private String output;
    }

    public void solve(int useless, InputReader in, PrintWriter out) {
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