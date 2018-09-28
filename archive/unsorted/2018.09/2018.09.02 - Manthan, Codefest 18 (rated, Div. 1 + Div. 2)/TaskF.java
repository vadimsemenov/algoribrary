package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskF {
    private static final int MODULO = 1000_000_000 + 7;

    public void solve(int __, InputReader in, PrintWriter out) {
        int totalLength = in.nextInt();
        int blockLength = in.nextInt();
        int[] a = in.nextIntArray(totalLength);
        int[] max = new int[totalLength - blockLength + 1]; // max[i] = max {a[j], j <- i..i+blockLength}
        int[] stack = new int[totalLength];
        int bottom = 0;
        int top = 0;
        for (int i = 0; i < totalLength; ++i) {
            while (top > bottom && a[stack[top - 1]] <= a[i]) {
                top--;
            }
            stack[top++] = i;
            if (i >= blockLength - 1) {
                max[i - blockLength + 1] = a[stack[bottom]];
                if (stack[bottom] + blockLength - 1 == i) {
                    bottom++;
                }
            }
        }
        long answer = 0;
        for (int group = 0; group < blockLength - 1; ++group) {
            long stackSum = 0;
            top = 0;
            for (int i = max.length - group - 1; i >= 0; i -= blockLength - 1) {
                while (top > 0 && max[stack[top - 1]] <= max[i]) {
                    int prev = top == 1 ? max.length : stack[top - 2];
                    long multiplier = 1 + (prev - stack[top - 1] - 1) / (blockLength - 1);
                    stackSum -= max[stack[top - 1]] * multiplier;
                    stackSum %= MODULO;
                    top--;
                }
                stack[top++] = i;
                int prev = top == 1 ? max.length : stack[top - 2];
                long multiplier = 1 + (prev - stack[top - 1] - 1) / (blockLength - 1);
                stackSum += max[stack[top - 1]] * multiplier;
                stackSum %= MODULO;
                answer += stackSum;
                answer %= MODULO;
            }
        }
        if (answer < 0) {
            answer += MODULO;
        }
        out.println(answer);
    }
}
