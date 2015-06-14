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

public class TaskC {
    private static final int EXPECTED_TEST_NUMBER = 100;

    private static class TestSolver implements Callable<TestSolver> {
        private final int testNumber;
        private String output;

        private int length;
        private long repeat;
        private char[] sequence;

        private static final String YES = "YES";
        private static final String NO = "NO";

        private void solve() {
            int product = 1;
            for (int i = 0; i < length; ++i) {
                sequence[i] -= 'i' - 2;
                assert 1 <= sequence[i] && sequence[i] <= 4 : sequence[i];
                product = multiply(product, sequence[i]);
            }
            if (repeat > 12) {
                repeat = 12 + (repeat % 2);
            }
            {
                char[] tmp = new char[(int) (length * repeat)];
                for (int i = 0; i < tmp.length; ++i) {
                    tmp[i] = sequence[i % length];
                }
                product = multiply(product, product);
                length = tmp.length;
                sequence = tmp;
            }
            if (product != -1) {
                output = NO;
                return;
            }
            boolean wasI = false;
            int prefixProduct = 1;
            for (int i = 0; i < length; ++i) {
                prefixProduct = multiply(prefixProduct, sequence[i]);
                wasI |= prefixProduct == 2;
                if (prefixProduct == 4 && wasI) {
                    output = YES;
                    return;
                }
            }
            output = NO;
        }

        private static final int[][] MULTIPLICATION_TABLE = new int[][]{
                {0, 0,  0,  0,  0},
                {0, 1,  2,  3,  4},
                {0, 2, -1,  4, -3},
                {0, 3, -4, -1,  2},
                {0, 4,  3, -2, -1}
        };

        private int multiply(int left, int right) {
            int result = signum(left) * signum(right);
            result *= MULTIPLICATION_TABLE[Math.abs(left)][Math.abs(right)];
            assert 1 <= Math.abs(result) && Math.abs(result) <= 4 : result;
            return result;
        }

        private int signum(int number) {
            return number < 0 ? -1 : number > 0 ? 1 : 0;
        }

        public void readInput(final InputReader in) {
            length = in.nextInt();
            repeat = in.nextLong();
            sequence = in.next().toCharArray();
            assert length == sequence.length;
        }

        public void printOutput(final PrintWriter out) {
            out.println("Case #" + testNumber + ": " + output);
        }

        public TestSolver call() {
            System.err.println("In process testcase #" + testNumber);
            solve();
            System.err.println("Done testcase #" + testNumber + ": " + output);
            return this;
        }

        public TestSolver(final int testNumber) {
            this.testNumber = testNumber;
        }
    }

    public void solve(final int foo, final InputReader in, final PrintWriter out) {
        int tests = in.nextInt();
        Executor executor = new Executor(in, out);
        for (int test = 1; test <= tests; ++test) {
            TestSolver solver = new TestSolver(test);
            executor.submit(solver);
        }
        executor.shutdown();
    }

    private static class Executor {
        public static final int THREADS_NUMBER = 4;

        private final long START_TIME = System.currentTimeMillis();

        private final InputReader in;
        private final PrintWriter out;

        private final ExecutorService pool = Executors.newFixedThreadPool(THREADS_NUMBER);
        private final List<Future<TestSolver>> tasks;

        public Executor(final InputReader in, final PrintWriter out) {
            this.in = in;
            this.out = out;
            this.tasks = new ArrayList<>(EXPECTED_TEST_NUMBER);
        }

        public void submit(final TestSolver solver) {
            solver.readInput(in);
            tasks.add(pool.submit(solver));
        }

        public void shutdown() {
            pool.shutdown();
            System.err.println("Began writing outputs");
            for (Future<TestSolver> solution : tasks) {
                try {
                    TestSolver solver = solution.get();
                    solver.printOutput(out);
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
            System.err.println("Done in " + (System.currentTimeMillis() - START_TIME) + ".ms");
        }
    }
}

