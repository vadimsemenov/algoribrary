package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

import static algoribrary.misc.ArrayUtils.nextPermutation;

public class TaskA {
    private static class CaseSolver implements Callable<CaseSolver> {
        static int[][] cnt = new int[13][3];
        static int[][] map = new int[13][];
        static {
            cnt[0][0] = 1;
            map[0] = new int[1];
            map[0][0] = 0;
            for (int p = 1; p < cnt.length; ++p) {
                map[p] = new int[1 << p];
                for (int j = 0; j < map[p].length; j += 2) {
                    map[p][j] = map[p - 1][j / 2];
                    map[p][j + 1] = (map[p][j] + 1) % 3;
                }
                for (int x : map[p]) cnt[p][x]++;
            }
        }

        int power;
        int p, r, s;
        int[] seq;

        void solve() {
            int[] mapping = new int[3];
            {
                int[] x = {p, r, s};
                for (int i = 0; i < 3; ++i) mapping[i] = i;
                boolean good = false;
                for (int shift = 0; shift < 3; ++shift) {
                    if (cnt[power][0] == x[mapping[0]] &&
                            cnt[power][1] == x[mapping[1]] &&
                            cnt[power][2] == x[mapping[2]]) {
                        good = true;
                        break;
                    }
                    int fst = mapping[2];
                    for (int j = 2; j > 0; --j) mapping[j] = mapping[j - 1];
                    mapping[0] = fst;
                }
                if (!good) {
                    output = "IMPOSSIBLE";
                    return;
                }
            }
            System.err.println(Arrays.toString(cnt[power]));
            System.err.println(p + " " + r + " " + s + " " + Arrays.toString(mapping));
            int[] answer = new int[1 << power];
            for (int i = 0; i < answer.length; ++i) {
                answer[i] = mapping[map[power][i]];
            }
            System.err.println(Arrays.toString(answer));
            System.err.println(Arrays.toString(map[power]));
            for (int blockLength = 1; blockLength < (1 << power); blockLength <<= 1) {
                if (answer.length % (2 * blockLength) != 0) throw new AssertionError(answer.length + " " + blockLength);
                for (int i = 0; i < answer.length; i += 2 * blockLength) {
                    boolean swap = false;
                    int fst = i;
                    int snd = fst + blockLength;
                    for (int j = 0; j < blockLength; ++j) {
                        if (answer[fst + j] < answer[snd + j]) {
                            break;
                        } else if (answer[fst + j] > answer[snd + j]) {
                            swap = true;
                            break;
                        }
                    }
                    if (swap) {
                        for (int j = 0; j < blockLength; ++j) {
                            int tmp = answer[fst + j];
                            answer[fst + j] = answer[snd + j];
                            answer[snd + j] = tmp;
                        }
                    }
                }
            }
            String smpl = "PRS";
            StringBuilder out = new StringBuilder(answer.length);
            for (int i : answer) out.append(smpl.charAt(i));
            output = out.toString();
        }

        private int vs(int a, int b) {
            if (a == b) return -1;
            if (a == 'r') return b == 's' ? 0 : 1;
            if (a == 's') return b == 'p' ? 0 : 1;
            if (a == 'p') return b == 'r' ? 0 : 1;
            throw new AssertionError("unreachable");
        }

        void readInput(InputReader in) {
            power = in.nextInt();
            r = in.nextInt();
            p = in.nextInt();
            s = in.nextInt();
            if (r + p + s != (1 << power)) throw new AssertionError(r + " " + p + " " + s + " " + power);
            seq = new int[1 << power];
            for (int i = 0; i < p; ++i) seq[i] = 'p';
            for (int i = 0; i < r; ++i) seq[p + i] = 'r';
            for (int i = 0; i < s; ++i) seq[p + r + i] = 's';
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

    private static final int THREADS_QTY = 1;
}