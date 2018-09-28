package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public final class TaskE {
    private static final int MODULO = 1000_000_000 + 7;
    public void solve(int __, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        int power = in.nextInt();
        long[] key = in.nextLongArray(vertices);
        int[][] edgeList = in.nextIntTable(edges, 2);
        long[] xor = new long[edges];
        Integer[] order = new Integer[edges];
        for (int e = 0; e < edges; ++e) {
            order[e] = e;
            for (int i = 0; i < 2; ++i) {
                xor[e] ^= key[--edgeList[e][i]];
            }
        }
        Arrays.sort(order, Comparator.comparingLong(e -> xor[e]));
        DSU dsu = new DSU(vertices);
        long answer = 0;
        long numbers = 1L << power;
        for (int i = 0; i < edges; ) {
            numbers--;
            int j = i + 1;
            while (j < edges && xor[order[i]] == xor[order[j]]) {
                ++j;
            }
            dsu.clear();
            int components = vertices;
            for (int k = i; k < j; ++k) {
                int e = order[k];
                if (dsu.unite(edgeList[e][0], edgeList[e][1])) {
                    --components;
                }
            }
            answer += power(2, components);
            answer %= MODULO;
            i = j;
        }
        answer += power(2, vertices) * (numbers % MODULO);
        answer %= MODULO;
        if (answer < 0) {
            answer += MODULO;
        }
        out.println(answer);
    }

    private long power(long base, long power) {
        long result = 1;
        while (power > 0) {
            if ((power & 1) == 1) {
                result = result * base % MODULO;
            }
            base = base * base % MODULO;
            power >>>= 1;
        }
        return result;
    }

    private static class DSU {
        final int[] parents;
        final int[] time;
        final int[] rang;
        int TIME = 1;

        DSU(int capacity) {
            parents = new int[capacity];
            time = new int[capacity];
            rang = new int[capacity];
        }

        boolean unite(int u, int v) {
            u = get(u);
            v = get(v);
            if (u == v) return false;
            if (rang[u] < rang[v]) {
                int tmp = u;
                u = v;
                v = tmp;
            }
            parents[v] = u;
            if (rang[u] == rang[v]) {
                rang[u]++;
            }
            return true;
        }

        int get(int v) {
            if (TIME != time[v]) {
                time[v] = TIME;
                parents[v] = v;
                rang[v] = 0;
            }
            return parents[v] == v ? v : get(parents[v]);
        }

        void clear() {
            TIME++;
        }
    }
}
