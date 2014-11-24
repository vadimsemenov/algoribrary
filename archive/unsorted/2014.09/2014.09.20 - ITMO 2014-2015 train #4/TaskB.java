package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] table = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                table[i][j] = in.nextInt();
            }
        }
        int b1 = -1;
        int b2 = -1;
        int ans = Integer.MAX_VALUE;
        for (int t1 = 0; t1 < m; ++t1) {
            for (int t2 = t1 + 1; t2 < m; ++t2) {
                int[] cnt = new int[4];
                for (int i = 0; i < n; ++i) {
                    if (table[i][t1] == 1 && table[i][t2] == 1)
                        cnt[0]++;
                    if (table[i][t1] == 1 && table[i][t2] == 2)
                        cnt[1]++;
                    if (table[i][t1] == 2 && table[i][t2] == 1)
                        cnt[2]++;
                    if (table[i][t1] == 2 && table[i][t2] == 2)
                        cnt[3]++;
                }
                int max = -1;
                for (int i = 0; i < 4; ++i) max = Math.max(max, cnt[i]);
                if (ans > max) {
                    ans = max;
                    b1 = t1;
                    b2 = t2;
                }
            }
        }
        out.println(ans);
        out.println((b1 + 1) + " " + (b2 + 1));
    }
}
