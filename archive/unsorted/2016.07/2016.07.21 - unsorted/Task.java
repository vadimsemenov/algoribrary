package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.stream.IntStream;

public final class Task {
    public void solve(int __, InputReader in, PrintWriter out) {
        IntStream.rangeClosed(1, in.nextInt())
                .mapToObj(Solution::new)
                .peek(solver -> solver.prologue(in))
                .parallel()
                .peek(Solution::culmination)
                .sequential()
                .forEachOrdered(solver -> solver.epilogue(out));
    }

    static class Solution {
        private final int id;
        private StringBuilder answer = new StringBuilder();
        Solution(int id) { this.id = id; System.err.println("in process #" + id); }

        void prologue(InputReader in) { answer.append(in.next()); }
        void culmination() {
            answer.reverse();
            for (int i = 0; i < 10_000; ++i) answer.append(i);
            System.err.println("done #" + id);
        }

        void epilogue(PrintWriter out) {
            String output = "Case #" + id + ": " + answer;
                   out.println(output);
            System.err.println(output);
        }
    }
}
