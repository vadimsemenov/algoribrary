package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class ShilAndBeautifulMatrix {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int rows = in.nextInt();
        int columns = in.nextInt();
        final int MODULO = 1_000_000_000 + 7;
        long[] factorial = new long[rows * columns + 1];
        factorial[0] = 1;
        for (int i = 1; i < factorial.length; ++i) {
            factorial[i] = i * factorial[i - 1] % MODULO;
        }
        int[] cnt = new int[Integer.highestOneBit(rows * columns) << 1];
        for (int i = 1; i <= rows; ++i) for (int j = 1; j <= columns; ++j) {
            ++cnt[i ^ j];
        }
        long answer = 1;
        for (int i = 0; i < cnt.length; ++i) {
            answer = answer * factorial[cnt[i]] % MODULO;
        }
        out.println(answer);
    }
}
