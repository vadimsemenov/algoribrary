package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int rows = in.nextInt();
        int cols = in.nextInt();
        int count = in.nextInt();
        int answer = rows + cols - 1;
        DSU dsu = new DSU(rows + cols);
        for (int i = 0; i < count; ++i) {
            int r = in.nextInt() - 1;
            int c = in.nextInt() - 1;
            if (dsu.unite(r, rows + c)) {
                answer--;
            }
        }
        out.println(answer);
    }

    static class DSU {
        final int[] parent;
        final int[] size;

        DSU(int capacity) {
            parent = new int[capacity];
            size = new int[capacity];
            for (int i = 0; i < capacity; ++i) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int get(int v) {
            if (parent[v] != v) {
                parent[v] = get(parent[v]);
            }
            return parent[v];
        }

        boolean unite(int v, int u) {
            u = get(u);
            v = get(v);
            if (u == v) return false;
            if (size[u] < size[v]) {
                int t = u;
                u = v;
                v = t;
            }
            parent[v] = u;
            size[u] += size[v];
            return true;
        }
    }
}
