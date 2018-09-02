package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TaskC {
    private List<Integer>[] tree;
    private long[][] ans;
    private long[][] qty;
    private int length;
    private long total;

    public void solve(int __, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        length = in.nextInt();
        tree = new List[vertices];
        for (int i = 0; i < vertices; ++i) tree[i] = new ArrayList<>();
        for (int e = 0; e < vertices - 1; ++e) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            tree[u].add(v);
            tree[v].add(u);
        }
        ans = new long[length + 1][vertices];
        qty = new long[length + 1][vertices];
        dfs(0, -1);
        out.println(total);
    }

    private void dfs(int vertex, int parent) {
        qty[0][vertex] = 1;
        for (int child : tree[vertex]) if (child != parent) {
            dfs(child, vertex);
            // update total
            for (int i = 0; i < length; ++i) {
                for (int j = 1; j + i + 1 <= length; ++j) {
                    total += qty[i][child] * (qty[j][vertex] + ans[j][vertex]) + ans[i][child];
                }
            }
            // update arrays
            ans[0][vertex] += qty[length - 1][child] + ans[length - 1][child];
            qty[0][vertex] += qty[length - 1][child];
            for (int i = 1; i <= length; ++i) {
                ans[i][vertex] += ans[i - 1][child];
                qty[i][vertex] += qty[i - 1][child];
            }
        }
        for (int i = 1; i < length; ++i) {
            total += qty[i][vertex] + ans[i][vertex];
        }
        total += ans[0][vertex];
    }
}
