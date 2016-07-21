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

public class BoomerangDecoration {
    private static class CaseSolver implements Callable<CaseSolver> {
        int length;
        char[] text, target;

        private void solve() {
            int[] prefix = process();
            for (int i = 0; i + i < length; ++i) {
                char tmp = text[i];
                text[i] = text[length - i - 1];
                text[length - i - 1] = tmp;
                tmp = target[i];
                target[i] = target[length - i - 1];
                target[length - i - 1] = tmp;
            }
            int[] suffix = process();
//            System.err.println(Arrays.toString(prefix));
//            System.err.println(Arrays.toString(suffix));
            int answer = Math.min(prefix[length - 1], suffix[length - 1]);
            for (int i = 0; i < length - 1; ++i) {
                answer = Math.min(answer, Math.max(prefix[i], suffix[length - i - 2]));
            }
            output = "" + answer;
        }

        private int[] process() {
            int[] result = new int[length];
            for (int i = 1; i < length; ++i) {
                result[i] = result[i - 1];
                if (target[i] != target[i - 1]) {
                    ++result[i];
                }
            }
            if (target[0] != text[0]) {
                result[0] = 1;
            }
            for (int i = 1; i < length; ++i) {
                if (text[i] != target[i]) {
                    result[i]++;
                } else {
                    result[i] = result[i - 1];
                }
            }
            return result;
        }

        public void readInput(final InputReader in) {
            length = in.nextInt();
            text = in.next().toCharArray();
            target = in.next().toCharArray();
            assert length == text.length && length == target.length;
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

    private static final int THREADS_QTY = 1;
}