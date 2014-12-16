package tasks;

import java.util.Arrays;

public class ChristmasTreeDecoration {
    public int solve(int[] col, int[] x, int[] y) {
        int answer = 0;
        DSU dsu = new DSU(col.length);
        for (int i = 0; i < x.length; ++i) {
            if (col[x[i] - 1] != col[y[i] - 1]) {
                if (dsu.union(x[i] - 1, y[i] - 1)) {
                    answer++;
                }
            }
        }
        return col.length -  1 - answer;
    }

    static class DSU {
        int[] parent, size;

        public DSU(int counter) {
            parent = new int[counter];
            for (int i = 0; i < counter; ++i) parent[i] = i;
            size = new int[counter];
            Arrays.fill(size, 1);
        }

        public int get(int v) {
            return v == parent[v] ? v : (parent[v] = get(parent[v]));
        }

        public boolean union(int u, int v) {
            u = get(u);
            v = get(v);
            if (v == u) return false;
            if (size[v] < size[u]) {
                int tmp = v; v = u; u = tmp;
            }
            parent[u] = v;
            size[v] += size[u];
            return true;
        }
    }
}
