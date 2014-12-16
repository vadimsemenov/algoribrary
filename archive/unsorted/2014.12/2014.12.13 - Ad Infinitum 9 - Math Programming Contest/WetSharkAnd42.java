package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class WetSharkAnd42 {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        long strength = in.nextLong();
        out.println(solve(strength - 1, 2) % 1_000_000_007);
    }

    private long solve(long strength, long start) {
        if (strength == 0) return start;
        long finish = start + 2 * strength;
        long last = finish / 42 - start / 42;
        return solve(last, finish);
    }
}
