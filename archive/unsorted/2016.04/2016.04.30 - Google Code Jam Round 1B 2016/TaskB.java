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
        private String first;
        private String second;
        long x = -1, y = -1;

        void solve() {
            solveLarge();
        }

        void solveLarge() {
            char[] fst = first.toCharArray();
            char[] snd = second.toCharArray();
            if (fst.length != snd.length) throw new AssertionError(fst.length + " " + snd.length);
            solve(fst, snd, 0, 0);
            output = String.format("%" + first.length() + "d", x).replace(' ', '0') + " " +
                     String.format("%" + first.length() + "d", y).replace(' ', '0');
        }

        private void solve(char[] fst, char[] snd, int ptr, int signum) {
            while (ptr < fst.length && fst[ptr] != '?' && snd[ptr] != '?') {
                if (signum == 0) {
                    signum = fst[ptr] < snd[ptr] ? -1 : fst[ptr] > snd[ptr] ? 1 : 0;
                }
                ++ptr;
            }
            if (ptr == fst.length) {
                estimate(fst, snd);
                return;
            }
            if (ptr + 1 < fst.length && signum == 0 && fst[ptr] == '?' && snd[ptr] == '?' && fst[ptr + 1] == '?' && snd[ptr + 1] == '?') {
                fst[ptr] = snd[ptr] = '0';
                solve(fst, snd, ptr + 1, signum);
                fst[ptr] = snd[ptr] = '?';
                return;
            }
            if (fst[ptr] == '?' && snd[ptr] == '?') {
                if (signum == 0) {
                    for (int x = 0; x < 10; ++x) {
                        for (int y = 0; y < 10; ++y) {
                            fst[ptr] = (char) (x + '0');
                            snd[ptr] = (char) (y + '0');
                            solve(fst, snd, ptr + 1, x < y ? -1 : x > y ? 1 : 0);
                        }
                    }
                    fst[ptr] = snd[ptr] = '?';
                    return;
                } else if (signum == -1) {
                    fst[ptr] = '9';
                    snd[ptr] = '0';
                } else if (signum == 1) {
                    fst[ptr] = '0';
                    snd[ptr] = '9';
                } else throw new AssertionError("unreachable");
                solve(fst, snd, ptr, signum);
                fst[ptr] = snd[ptr] = '?';
            } else if (fst[ptr] == '?') {
                if (signum == 0) {
                    for (int i = 0; i < 10; ++i) {
                        fst[ptr] = (char) (i + '0');
                        solve(fst, snd, ptr + 1, fst[ptr] < snd[ptr] ? -1 : fst[ptr] > snd[ptr] ? 1 : 0);
                    }
                    fst[ptr] = '?';
                    return;
                } else if (signum == -1) {
                    fst[ptr] = '9';
                } else if (signum == 1) {
                    fst[ptr] = '0';
                } else throw new AssertionError("unreachable");
                solve(fst, snd, ptr, signum);
                fst[ptr] = '?';
            } else if (snd[ptr] == '?') {
                if (signum == 0) {
                    for (int i = 0; i < 10; ++i) {
                        snd[ptr] = (char) (i + '0');
                        solve(fst, snd, ptr + 1, fst[ptr] < snd[ptr] ? -1 : fst[ptr] > snd[ptr] ? 1 : 0);
                    }
                    snd[ptr] = '?';
                    return;
                } else if (signum == 1) {
                    snd[ptr] = '9';
                } else if (signum == -1) {
                    snd[ptr] = '0';
                } else throw new AssertionError("unreachable");
                solve(fst, snd, ptr, signum);
                snd[ptr] = '?';
            } else throw new AssertionError("unreachable :)");
        }

        private void estimate(char[] fst, char[] snd) {
            long x = estimate(fst);
            long y = estimate(snd);
            if (better(x, y, this.x, this.y)) {
                this.x = x;
                this.y = y;
            }
        }

        private long estimate(char[] a) {
            long x = 0;
            for (char ch : a) {
                x *= 10;
                x += ch - '0';
            }
            return x;
        }

        void solveSmall() {
            if (first.length() != second.length()) throw new AssertionError(first + " " + second);
            int x = -1;
            int y = -1;
            int upTo = 1;
            for (int i = 0; i < first.length(); ++i) {
                upTo *= 10;
            }
            for (int i = 0; i < upTo; ++i) {
                for (int j = 0; j < upTo; j++) {
                    if (match(first, i) && match(second, j)) {
                        if (x == -1 || better(i, j, x, y)) {
                            x = i;
                            y = j;
                        }
                    }
                }
            }
            output = String.format("%" + first.length() + "d", x).replace(' ', '0') + " " +
                     String.format("%" + first.length() + "d", y).replace(' ', '0');
        }

        private boolean better(long i, long j, long x, long y) {
            if (x == -1) return true;
            if (Math.abs(i - j) < Math.abs(x - y)) return true;
            if (Math.abs(i - j) > Math.abs(x - y)) return false;
            if (i + j < x + y) return true;
            if (i + j > x + y) return false;
            if (j < y) return true;
            if (j > y) return false;
            throw new AssertionError(i + " " + j + " " + x + " " + y);
        }

        private static boolean match(String string, int number) {
            for (int i = 0; i < string.length(); ++i) {
                char ch = string.charAt(string.length() - 1 - i);
                int digit = number % 10;
                number /= 10;
                if (ch == '?') {
                    continue;
                }
                if (ch - '0' != digit) return false;
            }
            return true;
        }

        void readInput(InputReader in) {
            first = in.next();
            second = in.next();
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