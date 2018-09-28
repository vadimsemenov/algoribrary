package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class TaskD {
    private List<Integer>[] tree;
    private int[] time;

    private Comparator<Integer> timeComparator = Comparator.comparingInt(vertex -> time[vertex]);

    public void solve(int __, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        tree = new List[vertices];
        for (int i = 0; i < vertices; i++) {
            tree[i] = new ArrayList<>();
        }
        for (int e = 0; e < vertices - 1; ++e) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            tree[u].add(v);
            tree[v].add(u);
        }
        int[] order = new int[vertices];
        time = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            order[i] = in.nextInt() - 1;
            if (time[order[i]] != 0 || (i != 0 && order[i] == 0)) {
                out.println("No");
                return;
            }
            time[order[i]] = i;
        }
        if (order[0] != 0) {
            out.println("No");
            return;
        }
        int head = 0;
        int tail = 1;
        int[] from = new int[vertices];
        Arrays.fill(from, -1);
        from[0] = 0;
        while (head < tail) {
            int vertex = order[head++];
            int children = tree[vertex].size();
            if (vertex != 0) children--;
            for (int child : tree[vertex]) {
                if (child != from[vertex]) {
                    if (from[child] != -1 || tail > time[child] || time[child] >= tail + children) {
                        out.println("No");
                        return;
                    }
                    from[child] = vertex;
                }
            }
            tail += children;
        }
        out.println("Yes");
    }
}
