package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public final class TaskA {
    private Map<Integer, Long> mem;

    public void solve(int __, InputReader in, PrintWriter out) {
        int w = in.nextInt();
        int sqrt = 2;
        while (sqrt * sqrt < w) {
            sqrt++;
        }
        mem = new HashMap<>();
        mem.put(1, 1L);
        out.println(rec(w));
    }

    private long rec(int w) {
        Long res = mem.get(w);
        if (res != null) {
            return res;
        }
        long cur = 0;
        int k;
        for (k = 2; k * k <= w; ++k) {
            cur += rec(w / k);
        }
        int l = w / k + 1;
        k--;
        while (l-- > 1) {
            int last = w / l;
            cur += rec(l) * (last - k);
            k = last;
        }
        mem.put(w, cur);
        return cur;
    }
}
