package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TaskE {
    public void solve(int __, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int[] count = new int[vertices];
        for (int e = 0; e < vertices - 1; ++e) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            if (u == v || Math.max(u, v) != vertices - 1) {
                out.println("NO");
                return;
            }
            count[Math.min(u, v)]++;
        }
        if (count[vertices - 2] == 0) {
            out.println("NO");
            return;
        }
        List<Integer>[] tree = new List[vertices];
        Arrays.setAll(tree, i -> new ArrayList<>());
        int j = 0;
        for (int i = 0; i < vertices; ++i) {
            int prev = i;
            for (int k = 1; k < count[i]; ++k) {
                while (j < i && count[j] > 0) {
                    j++;
                }
                if (j == i) {
                    out.println("NO");
                    return;
                }
                tree[prev].add(j);
                prev = j++;
            }
            if (count[i] > 0) {
                tree[vertices - 1].add(prev);
            }
        }
        out.println("YES");
        for (int v = 0; v < vertices; ++v) {
            if (!tree[v].isEmpty()) {
                for (int u : tree[v]) {
                    out.println((u + 1) + " " + (v + 1));
                }
            }
        }
    }
}
