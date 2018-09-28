package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        int[] multiset = in.nextIntArray(length);
        int[] qty = new int[101];
        for (int i : multiset) {
            qty[i]++;
        }
        int one = 0;
        int more = 0;
        for (int cnt : qty) {
            if (cnt == 1) {
                one++;
            } else if (cnt > 2) {
                more++;
            }
        }
        int needOne = one / 2;
        int needMore = one % 2 == 0 ? 0 : 1;
        if (needMore > more) {
            out.println("NO");
            return;
        }
        out.println("YES");
        for (int i : multiset) {
            char next = 'A';
            if (needOne > 0 && qty[i] == 1) {
                next = 'B';
                needOne--;
            }
            if (needMore > 0 && qty[i] > 2) {
                next = 'B';
                needMore--;
            }
            out.print(next);
        }
    }
}
