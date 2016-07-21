package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.math.BigInteger;

public class TaskG {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();
        out.println(BigInteger.valueOf(n).gcd(BigInteger.valueOf(k)).longValue());
//        for (int n = 1; n < 10000; ++n) {
//            for (int k = 1; k < n; ++k) {
//                int bf = bf(n, k);
//                if (bf != BigInteger.valueOf(n).gcd(BigInteger.valueOf(k)).longValue()) {
//                    out.println(n + " " + k);
//                }
//            }
//        }
    }

    private int bf(int n, int k) {
        int result = 0;
        boolean[] visited = new boolean[n];
        int[] next = new int[n];
        for (int i = 0; i < n - k; ++i) {
            next[i] = i + k;
        }
        for (int i = n - k; i < n; ++i) {
            next[i] = i - (n - k);
        }
        for (int start = 0; start < n; ++start) {
            if (visited[start]) continue;
            ++result;
            int current = start;
            while (!visited[current]) {
                visited[current] = true;
                current = next[current];
            }
        }
        return result;
    }
}
