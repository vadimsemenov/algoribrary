package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskC {
    private static class CaseSolver implements Callable<CaseSolver> {
        List<Integer>[] graph;
        private int counter;
        private int lsize;
        private int rsize;

        int[] matching;
        boolean[] visited;

        void solve() {
            matching = new int[rsize];
            Arrays.fill(matching, -1);
            visited = new boolean[lsize];
            int matchingSize = 0;
            for (int i = 0; i < lsize; ++i) {
                if (dfs(i)) {
                    ++matchingSize;
                    Arrays.fill(visited, false);
                }
            }
            output = "" + (counter - (lsize + rsize - matchingSize));
        }

        private boolean dfs(int v) {
            if (visited[v]) return false;
            visited[v] = true;
            for (int to : graph[v]) {
                if (matching[to] == -1) {
                    matching[to] = v;
                    return true;
                }
            }
            for (int to : graph[v]) {
                if (matching[to] == -1 || dfs(matching[to])) {
                    matching[to] = v;
                    return true;
                }
            }
            return false;
        }

        void readInput(InputReader in) {
            counter = in.nextInt();
            graph = new List[counter];
            Map<String, Integer> left = new HashMap<>();
            lsize = 0;
            Map<String, Integer> right = new HashMap<>();
            rsize = 0;
            for (int i = 0; i < counter; ++i) {
                String l = in.next();
                String r = in.next();
                Integer ll = left.get(l);
                if (ll == null) left.put(l, (ll = lsize++));
                Integer rr = right.get(r);
                if (rr == null) right.put(r, (rr = rsize++));
                if (graph[ll] == null) graph[ll] = new ArrayList<>();
                graph[ll].add(rr);
            }

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