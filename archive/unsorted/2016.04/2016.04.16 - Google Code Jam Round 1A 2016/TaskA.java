package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TaskA {
    private static class CaseSolver implements Callable<CaseSolver> {
        String input;
        private void solve() {
            char[] result = new char[input.length()];
            int head = 0; int tail = 0;
            for (char ch : input.toCharArray()) {
                if (head == tail) {
                    result[tail++] = ch;
                } else {
                    if (result[head] <= ch) {
                        --head;
                        if (head < 0) head += input.length();
                        result[head] = ch;
                    } else {
                        result[tail++] = ch;
                    }
                }
            }
            StringBuilder builder = new StringBuilder(input.length());
            for (int i = 0; i < input.length(); ++i) {
                builder.append(result[(head + i) % input.length()]);
            }
            output = builder.toString();
        }

        public void readInput(final InputReader in) {
            input = in.next();
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