package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        char[][] table = new char[n][];
        for (int i = 0; i < n; ++i) {
            table[i] = in.next().toCharArray();
        }
        int[] cost = new int[256];
        cost['f'] = 1;
        cost['a'] = 10;
        cost['c'] = 100;
        cost['e'] = 1000;
        int target = 1111;
        int answer = 0;
        for (int i = 0; i + 1 < n; ++i) {
            for (int j = 0; j + 1 < m; ++j) {
                int sum = 0;
                for (int ii = i; ii < i + 2; ++ii) {
                    for (int jj = j; jj < j + 2; ++jj) {
                        sum += cost[table[ii][jj]];
                    }
                }
                if (sum == target) ++answer;
            }
        }
        out.println(answer);
    }
}
