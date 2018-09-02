package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class GameOfTwoStacks {
    public void solve(int __, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int limit = in.nextInt();
        long[] a = new long[n + 1];
        for (int i = 1; i <= n; ++i) a[i] = in.nextInt() + a[i - 1];
        int ptr = n;
        while (ptr > 0 && a[ptr] > limit) --ptr;
        int best = ptr;
        long sum = 0;
        for (int i = 1; i <= m; ++i) {
            int current = in.nextInt();
            sum += current;
            while (ptr > 0 && sum + a[ptr] > limit) --ptr;
            if (sum + a[ptr] <= limit) best = Math.max(best, i + ptr);
        }
        out.println(best);
    }
}
