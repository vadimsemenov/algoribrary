package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        int symbolsQty = in.nextInt();
        for (int i = 0; i < symbolsQty; ++i) {
            out.print((char) ('a' + i));
        } // last one is not 'a'
        for (int i = 0; i < length - symbolsQty; ++i) {
            out.print((char) ('a' + i % 2));
        }
    }
}
