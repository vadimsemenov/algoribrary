package tasks;

public class OddEvenTree {
    private static final int[] IMPOSSIBLE = new int[]{-1};
    public int[] getTree(String[] _x) {
        int n = _x.length;
        boolean[][] x = new boolean[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                x[i][j] = _x[i].charAt(j) != 'E';
            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (x[i][j] != x[j][i]) {
                    return IMPOSSIBLE;
                }
            }
            if (x[i][i]) {
                return IMPOSSIBLE;
            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                for (int k = j + 1; k < n; ++k) {
                    if (x[i][j] ^ x[j][k] != x[i][k]) {
                        return IMPOSSIBLE;
                    }
                }
            }
        }
        int[] result = new int[n * 2 - 2];
        int ptr = 0;
        DSU dsu = new DSU(n);
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (x[i][j] && dsu.unite(i, j)) {
                    result[ptr++] = i;
                    result[ptr++] = j;
                }
            }
        }
        if (ptr != result.length) {
            return IMPOSSIBLE;
        }
        return result;
    }

    static class DSU {
        int[] parent;

        DSU(int capacity) {
            parent = new int[capacity];
            for (int i = 0; i < capacity; ++i) {
                parent[i] = i;
            }
        }

        int get(int v) {
            return v == parent[v] ? v : (parent[v] = get(parent[v]));
        }

        boolean unite(int u, int v) {
            u = get(u); v = get(v);
            parent[u] = v;
            return u != v;
        }
    }
}
