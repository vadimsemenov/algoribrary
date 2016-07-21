package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskD {
    private static final String GAME_CHEATED = "Game cheated!";
    private static final String DATA_NOT_SUFFICIENT = "Data not sufficient!";

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int height = in.nextInt();
        int queries = in.nextInt();
        long[][] borders = new long[queries][2];
        int goodPtr = queries - 1;
        int badQty = 0;
        long min = 1L << (height - 1);
        long max = (1L << height) - 1;
        for (int query = 0; query < queries; ++query) {
            int level = in.nextInt();
            long left = (in.nextLong() << (height - level));
            long right = (in.nextLong() << (height - level)) | ((1L << (height - level)) - 1);
            int answer = in.nextInt();
            if (answer == 1) {
                min = Math.max(min, left);
                max = Math.min(max, right);
                borders[goodPtr][0] = left;
                borders[goodPtr][1] = right;
                --goodPtr;
            } else {
                borders[badQty][0] = left;
                borders[badQty][1] = right;
                ++badQty;
            }
        }
        ++goodPtr;
        {
            Arrays.sort(borders, 0, badQty, (first, second) -> {
                int cmp = Long.compare(first[0], second[0]);
                if (cmp == 0) {
                    cmp = -Long.compare(first[1], second[1]);
                }
                return cmp;
            });
            int _badQty = badQty;
            badQty = Math.min(badQty, 1);
            for (int i = 1; i < _badQty; ++i) {
                if (borders[i][0] <= borders[badQty - 1][1] + 1) {
                    borders[badQty - 1][1] = Math.max(borders[badQty - 1][1], borders[i][1]);
                } else {
                    borders[badQty++] = borders[i];
                }
            }
        }
        if (min > max) {
            out.println(GAME_CHEATED);
            return;
        }
        for (int i = goodPtr; i < borders.length; ++i) {
            int ll = -1;
            int rr = badQty;
            while (rr - ll > 1) {
                int mm = (ll + rr) >>> 1;
                if (borders[mm][0] <= borders[i][0]) {
                    ll = mm;
                } else {
                    rr = mm;
                }
            }
            if (ll >= 0 && borders[ll][0] <= borders[i][0] && borders[i][1] <= borders[ll][1]) {
                out.println(GAME_CHEATED);
                return;
            }
        }
        for (int i = 0; i < badQty; ++i) {
            if (Math.max(min, borders[i][0]) <= Math.min(max, borders[i][1])) {
                if (borders[i][0] <= min) {
                    if (max <= borders[i][1]) {
                        out.println(GAME_CHEATED);
                        return;
                    }
                    min = borders[i][1] + 1;
                } else if (borders[i][1] < max) {
                    break;
                } else {
                    max = borders[i][0] - 1;
                }
            }
        }
        if (min == max) {
            out.println(min);
        } else {
            out.println(DATA_NOT_SUFFICIENT);
        }
    }
}
