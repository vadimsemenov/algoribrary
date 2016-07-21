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

public class TaskB {
    private static class CaseSolver implements Callable<CaseSolver> {
        int[][] dataset;
        int n;
        private void solve() {
            for (int i = 0; i < dataset.length - 1; ) {
                int min = Integer.MAX_VALUE;
                for (int j = i; j < dataset.length; ++j) {
                    if (dataset[j] != null) {
                        min = Math.min(min, dataset[j][i / 2]);
                    }
                }
                int cnt = 0;
                for (int j = i; j < dataset.length; ++j) {
                    if (dataset[j] != null && dataset[j][i / 2] == min) {
                        int[] tmp = dataset[j];
                        dataset[j] = dataset[i];
                        dataset[i] = tmp;
                        ++i;
                        ++cnt;
                    }
                }
                if (cnt == 1) {
                    int[] tmp = dataset[i];
                    dataset[i] = new int[n];
                    dataset[dataset.length - 1] = tmp;
                    ++i;
                } else if (cnt != 2) throw new AssertionError(cnt + " " + i);

            }
            int[][] table = new int[n][n];
            int[] queue = new int[n];
            boolean[] inQueue = new boolean[n];
            int head = 0; int tail = 0;
            for (int i = 0; i < dataset.length; i += 2) {
                int j = i + 1;
                if (dataset[i] == null || dataset[j] == null) continue;
                boolean equals = true;
                for (int k = 0; k < n; ++k) {
                    equals &= dataset[i][k] == dataset[j][k];
                }
                if (equals) {
                    queue[tail++] = i / 2;
                    inQueue[i / 2] = true;
                    for (int k = i; k < table.length; ++k) {
                        table[i / 2][k] = table[k][i / 2] = dataset[i][k];
                    }
                }
            }
            int x = -1; int y = -1;
            while (head < tail) {
                int cur = queue[head++];
                if (dataset[2 * cur] == null || dataset[2 * cur + 1] == null) {
                    int[] d = dataset[2 * cur] == null ? dataset[2 * cur + 1] : dataset[2 * cur];
                    for (int i = 0; i < n; ++i) {
                        if (table[cur][i] != 0 && table[cur][i] != d[i]) {
                            x = cur;
                        }
                    }
                    if (x == -1) {
                        y = cur;
                        for (int i = 0; i < n; ++i) {
                            table[cur][i] = d[i];
                        }
                    } else {
                        for (int i = 0; i < n; ++i) {
                            table[i][cur] = d[i];
                        }
                    }
                    continue;
                }
                boolean horizontal = true;
                for (int i = 0; i < n; ++i) {
                    if (dataset[2 * cur][i] != dataset[2 * cur + 1][i]) {
                        if (table[cur][i] != 0) {
                            horizontal = dataset[2 * cur][i] == table[cur][i];
                            break;
                        }
                    }
                }
                if (!horizontal) {
                    int[] tmp = dataset[2 * cur];
                    dataset[2 * cur] = dataset[2 * cur + 1];
                    dataset[2 * cur + 1] = tmp;
                }
                for (int i = 0; i < n; ++i) {
                    if (table[cur][i] != 0 && table[i][cur] != 0) {
                        continue;
                    }
                    if (table[cur][i] == 0) {
                        table[cur][i] = dataset[2 * cur][i];
                    }
                    if (table[i][cur] == 0) {
                        table[i][cur] = dataset[2 * cur + 1][i];
                    }
                    if (table[cur][i] != table[i][cur] && !inQueue[i]) {
                        queue[tail++] = i;
                        inQueue[i] = true;
                    }
                }
            }

            assert head == tail && head >= n - 1;
            for (int i = 0; i < n; ++i) if (!inQueue[i]) {
                table[i][i] = dataset[2 * i] == null ? dataset[2 * i + 1][i] : dataset[2 * i][i];
                x = i;
                break;
            }
            StringBuilder builder = new StringBuilder(n * 2);
            for (int i = 0; i < n; ++i) {
                builder.append(x == -1 ? table[i][y] : table[x][i]).append(' ');
            }
            output = builder.toString();
        }

        public void readInput(final InputReader in) {
            n = in.nextInt();
            dataset = new int[2 * n][];
            for (int i = 0; i + 1 < dataset.length; ++i) dataset[i] = in.nextIntArray(n);
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