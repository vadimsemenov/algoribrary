package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        int[] weight = new int[vertices];
        for (int i = 0; i < vertices; i++) weight[i] = in.nextInt();
        Edge[] list = new Edge[edges];
        for (int e = 0; e < edges; e++) {
            int v = in.nextInt() - 1;
            int u = in.nextInt() - 1;
            list[e] = new Edge(v, u, Math.min(weight[v], weight[u]));
        }
        Arrays.sort(list);
        DSU dsu = new DSU(vertices);
        double answer = 0;
        double div = vertices * 1.0 * (vertices - 1);
        for (Edge edge : list) {
            int pv = dsu.get(edge.v);
            int pu = dsu.get(edge.u);
            if (pv == pu) continue;
            answer += edge.w * 2.0 * dsu.getSize(pv) * (dsu.getSize(pu) / div);
            dsu.union(pv, pu);
        }
        out.println(answer);
    }

    static class Edge implements Comparable<Edge> {
        int v, u, w;

        Edge(int v, int u, int w) {
            this.v = v;
            this.u = u;
            this.w = w;
        }

        @Override
        public int compareTo(Edge other) {
            return -Integer.compare(this.w, other.w);
        }
    }

    static class DSU {
        private int[] parent, size;

        DSU(int size) {
            this.parent = new int[size];
            this.size = new int[size];
            for (int i = 0; i < size; i++) parent[i] = i;
            Arrays.fill(this.size, 1);
        }

        public int get(int v) {
            return parent[v] == v ? v : (parent[v] = get(parent[v]));
        }

        public int getSize(int v) {
            return size[get(v)];
        }

        public void union(int v, int u) {
            int pv = get(v);
            int pu = get(u);
            if (size[pv] > size[pu]) {
                size[pv] += size[pu];
                parent[pu] = pv;
            } else {
                size[pu] += size[pv];
                parent[pv] = pu;
            }
        }
    }
}
