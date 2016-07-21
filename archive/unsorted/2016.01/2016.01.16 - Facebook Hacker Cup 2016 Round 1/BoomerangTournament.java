package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class BoomerangTournament {
    private static class CaseSolver implements Callable<CaseSolver> {
        private int counter;
        private boolean[][] table;
        private int[] min, max;

        private void solve() {
            solve(0, (1 << counter) - 1);
        }

        private int solve(int level, int mask) {
            int count = Integer.bitCount(mask);
            int winners = 0;
            if (count == 1) {
                int id = Integer.bitCount(mask - 1);
                min[id] = Math.min(min[id], ((1 << level) >>> 1) + 1);
                winners = 1 << id;
            } else {
                count >>>= 1;
                final int lowestBit = Integer.lowestOneBit(mask);
                for (int subMask = mask; subMask != 0; subMask = (subMask - 1 & mask)) {
                    if (Integer.bitCount(subMask) != count || (lowestBit & subMask) == 0) {
                        continue;
                    }
                    final int lhs = solve(level + 1, subMask);
                    final int rhs = solve(level + 1, mask ^ subMask);
                    for (int i = lhs; i > 0; i &= i - 1) {
                        for (int j = rhs; j > 0; j &= j - 1) {
                            int fst = Integer.bitCount(Integer.lowestOneBit(i) - 1);
                            int snd = Integer.bitCount(Integer.lowestOneBit(j) - 1);
                            if (!table[fst][snd]) {
                                int tmp = fst;
                                fst = snd;
                                snd = tmp;
                            }
                            winners |= 1 << fst;
                            min[fst] = Math.min(min[fst], ((1 << level) >>> 1) + 1);
                            max[snd] = Math.max(max[snd], (1 << level) + 1);
                        }
                    }
                }
            }
            return winners;
        }

        public void readInput(final InputReader in) {
            counter = in.nextInt();
            assert (counter & counter - 1) == 0;
            table = new boolean[counter][counter];
            for (int i = 0; i < counter; ++i) {
                for (int j = 0; j < counter; ++j) {
                    int foo = in.nextInt();
                    assert foo == 0 || foo == 1;
                    table[i][j] = (foo == 1);
                    if (i == j) {
                        assert !table[i][j];
                    } else if (j < i) {
                        assert table[i][j] != table[j][i];
                    }
                }
            }
            min = new int[counter];
            Arrays.fill(min, counter);
            max = new int[counter];
            Arrays.fill(max, 1);
        }

        public void printOutput(final PrintWriter out) {
            out.println("Case #" + testNumber + ":");
            for (int i = 0; i < counter; ++i) {
                out.println(min[i] + " " + max[i]);
            }
        }

        @Override
        public CaseSolver call() {
            System.err.println("In process testcase #" + testNumber);
            solve();
            System.err.println("Done testcase #" + testNumber);
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