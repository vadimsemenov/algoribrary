package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class AntStack {
    public void solve(int testCase, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] weights = in.nextIntArray(counter);
        long[] stack = new long[counter + 1];
        Arrays.fill(stack, Long.MAX_VALUE);
        stack[0] = 0;
        int answer = 0;
        for (int weight : weights) {
            for (int size = answer; size >= 0; --size) {
                if (stack[size] <= 6L * weight) {
                    stack[size + 1] = Math.min(stack[size + 1], stack[size] + weight);
                }
            }
            if (stack[answer + 1] < Long.MAX_VALUE) {
                ++answer;
            }
        }
        out.println("Case #" + testCase + ": " + answer);
    }
}
