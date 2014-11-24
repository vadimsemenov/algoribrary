package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.HashMap;

public class TaskD {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] colors = new int[counter];
        int all = 0;
        for (int i = 0; i < counter; ++i) {
            colors[i] = in.nextInt();
            Integer cnt = map.get(colors[i]);
            if (cnt == null) {
                all++;
                cnt = 0;
            }
            map.put(colors[i], cnt + 1);
        }
        if (all == 1) {
            out.println(0);
        } else {
            int queries = in.nextInt();
            for (int q = 0; q < queries; ++q) {
                int pos = in.nextInt() - 1;
                int col = in.nextInt();
                int cnt = map.get(colors[pos]);
                if (cnt == 1) {
                    map.remove(colors[pos]);
                    all--;
                } else {
                    map.put(colors[pos], cnt - 1);
                }
                Integer oldCnt = map.get(col);
                if (oldCnt == null) {
                    oldCnt = 0;
                    all++;
                }
                map.put(col, oldCnt + 1);
                colors[pos] = col;
                if (all == 1) {
                    out.println(q + 1);
                    return;
                }
            }
            out.println(-1);
        }
    }
}
