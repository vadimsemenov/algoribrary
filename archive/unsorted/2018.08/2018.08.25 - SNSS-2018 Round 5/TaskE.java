package task;

import algoribrary.io.InputReader;
import algoribrary.misc.ArrayUtils;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.ToIntFunction;

public final class TaskE {
    public void solve(int __, InputReader in, PrintWriter out) {
        int cities = in.nextInt();
        int banks = in.nextInt();
        int innerQty = in.nextInt();
        int outerQty = in.nextInt();
        int[][] edges = in.nextIntTable(innerQty + outerQty, 3);
        for (int i = innerQty; i < edges.length; ++i) {
            for (int j = 0; j < 2; ++j) {
                edges[i][j] += banks;
            }
        }
        long totalCost = 0;
        for (int i = 0; i < innerQty; ++i) totalCost += (long) edges[i][2] * cities;
        for (int i = innerQty; i < edges.length; ++i) totalCost += (long) edges[i][2] * banks;
        ArrayUtils.sort(edges, new Comparator<int[]>() {
            @Override
            public int compare(int[] c1, int[] c2) {
                return Integer.compare(c1[2], c2[2]);
            }
        });
        DSU citiesDsu = new DSU(cities);
        DSU banksDsu = new DSU(banks);
        for (int[] edge : edges) {
            if (edge[0] <= banks) {
                int u = edge[0] - 1;
                int v = edge[1] - 1;
                if (banksDsu.unite(u, v)) {
                    totalCost -= (long) edge[2] * citiesDsu.components;
                }
            } else {
                int u = edge[0] - banks - 1;
                int v = edge[1] - banks - 1;
                if (citiesDsu.unite(u, v)) {
                    totalCost -= (long) edge[2] * banksDsu.components;
                }
            }
        }
        assert citiesDsu.components == 1 && banksDsu.components == 1;
        out.println(totalCost);
    }

    private class DSU {
        private final int[] parent;
        private final int[] rang;
        private int components;

        DSU(int capacity) {
            parent = new int[capacity];
            rang = new int[capacity];
            for (int i = 0; i < capacity; ++i) {
                parent[i] = i;
                rang[i] = 1;
            }
            components = capacity;
        }

        int get(int v) {
            return parent[v] == v ? v : (parent[v] = get(parent[v]));
        }

        boolean unite(int u, int v) {
            u = get(u);
            v = get(v);
            if (u == v) return false;
            components--;
            if (rang[u] < rang[v]) {
                int tmp = u;
                u = v;
                v = tmp;
            }
            parent[v] = u;
            if (rang[u] == rang[v]) rang[u]++;
            return true;
        }
    }
}
