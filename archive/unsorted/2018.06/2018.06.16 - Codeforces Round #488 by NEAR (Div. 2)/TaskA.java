package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int[] sequence = in.nextIntArray(n);
        boolean[] withFingerprint = new boolean[10];
        for (int i = 0; i < m; ++i) {
            withFingerprint[in.nextInt()] = true;
        }
        for (int i : sequence) {
            if (withFingerprint[i]) {
                out.print(i + " ");
            }
        }
    }
}
