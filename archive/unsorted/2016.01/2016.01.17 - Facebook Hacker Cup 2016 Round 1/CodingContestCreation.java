package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CodingContestCreation {
    private static class CaseSolver implements Callable<CaseSolver> {
        private static final int MAX = 100;
        private static final int STEP = 10;
        private static final int NEED = 4;
        private int counter;
        private int[] list;
        private int answer;

        private void solve() {
            for (int ptr = 0; ptr < counter; ) {
                int previous = list[ptr++];
                for (int have = 1; have < NEED; ++have) {
                    if (ptr >= counter) {
                        answer += NEED - have;
                        return;
                    }
                    int current = list[ptr];
                    if (current <= previous) {
                        answer += NEED - have;
                        break;
                    }
                    int diff = (current - previous - 1) / STEP;
                    diff = Math.min(diff, NEED - have);
                    answer += diff;
                    have += diff;
                    if (have < NEED) {
                        ++ptr; // take current
                    }
                    previous = current;
                }
            }
        }

        public void readInput(final InputReader in) {
            counter = in.nextInt();
            list = in.nextIntArray(counter);
        }

        public void printOutput(final PrintWriter out) {
            out.println("Case #" + testNumber + ": " + answer);
        }

        @Override
        public CaseSolver call() {
            System.err.println("In process testcase #" + testNumber);
            solve();
            System.err.println("Done testcase #" + testNumber + " (" + answer + ")");
            return this;
        }

        public CaseSolver(final int testNumber) {
            this.testNumber = testNumber;
        }

        private final int testNumber;
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