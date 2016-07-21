package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskA {
    private static class CaseSolver implements Callable<CaseSolver> {
        String[] digits = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"};
        //                  z       o       w                u     f         x      v        G       i
        //                  1       9       2                3     4         5      7        6       8
        String input;

        void solve() {
            int[] cnt = new int[256];
            for (char ch : input.toCharArray()) {
                cnt[ch]++;
            }
            int[] times = new int[10];
            times[0] += update(cnt, 0, 'Z');
            times[2] += update(cnt, 2, 'W');
            times[4] += update(cnt, 4, 'U');
            times[5] += update(cnt, 5, 'F');
            times[6] += update(cnt, 6, 'X');
            times[8] += update(cnt, 8, 'G');
            times[7] += update(cnt, 7, 'V');
            times[9] += update(cnt, 9, 'I');
            times[1] += update(cnt, 1, 'O');
            times[3] += update(cnt, 3, 'T');
            for (int i : cnt) if (i != 0) throw new AssertionError(i);
            StringBuilder result = new StringBuilder("");
            for (int i = 0; i < 10; ++i) {
                for (int j = 0; j < times[i]; ++j) {
                    result.append(i);
                }
            }
            output = result.toString();
        }

        private int update(int[] cnt, int n, char ch) {
            int count = cnt[ch];
            for (int cha : digits[n].toCharArray()) {
                cnt[cha] -= count;
                if (cnt[cha] < 0) throw new AssertionError(cnt[cha]);
            }
            return count;
        }

        void readInput(InputReader in) {
            input = in.next();
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