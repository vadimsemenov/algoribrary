package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        long[] seq = new long[]{1L, 1L};
        for (int step = 1; step < 15; ++step) {
            int cnt = 0;
            for (long i : seq) if (i == step) ++cnt;
            out.print(cnt + ", ");
            seq = modify(seq);
        }
    }

    private long[] modify(long[] seq) {
        long[] next = new long[seq.length + seq.length - 1];
        int ptr = 0;
        next[ptr++] = seq[0];
        for (int i = 1; i < seq.length; ++i) {
            next[ptr++] = seq[i] + seq[i - 1];
            next[ptr++] = seq[i];
        }
        return next;
    }


}
