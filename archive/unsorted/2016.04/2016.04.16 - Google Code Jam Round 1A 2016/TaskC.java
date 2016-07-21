package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class TaskC {
    private static class CaseSolver implements Callable<CaseSolver> {
        int n;
        int[] bff;

        private void solve() {
            int longest = -1;
            int[] visited = new int[n];
            int[] cycle = new int[n];
            for (int start = 0; start < n; ++start) {
                Arrays.fill(visited, -1);
                Arrays.fill(cycle, -1);

                int ptr = 0;
                int current = start;
                do {
                    visited[current] = ptr;
                    cycle[ptr++] = current;
                    current = bff[current];
                } while (visited[current] == -1);
                int length = ptr - visited[current];
                if (length > longest) {
                    longest = length;
                }
            }
            int[] children = new int[n];
            int[] degree = new int[n];
            for (int i = 0; i < n; ++i) {
                ++degree[bff[i]];
                children[bff[i]] ^= i;
            }
            int[] queue = new int[n];
            int head = 0; int tail = 0;
            for (int i = 0; i < n; ++i) {
                if (degree[i] == 0) {
                    queue[tail++] = i;
                }
            }
            int[] chain = new int[n];
            while (head < tail) {
                int current = queue[head++];
                int parent = bff[current];
                if (--degree[parent] == 0) {
                    queue[tail++] = parent;
                }
                children[parent] ^= current;
                chain[parent] = Math.max(chain[parent], chain[current] + 1);
            }
            int cnt = 0;
            for (int i = 0; i < n; ++i) {
                if (bff[bff[i]] == i) {
                    ++cnt;
                    longest = Math.max(longest, chain[i] + chain[bff[i]] + 2);
                }
            }
            for (int i = 0; i < n; ++i) {
                if (bff[bff[i]] != i) continue;
                for (int j = i + 1; j < n; ++j) {
                    if (bff[bff[j]] != j || bff[j] == i) continue;
                    longest = Math.max(longest, 2 * cnt +
                            Math.max(chain[i], chain[bff[i]]) + Math.max(chain[j], chain[bff[j]]));
                }
            }
            output = longest + "";
        }

        public void readInput(final InputReader in) {
            n = in.nextInt();
            bff = in.nextIntArray(n);
            for (int i = 0; i < n; ++i) --bff[i];
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