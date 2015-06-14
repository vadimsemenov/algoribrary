package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TaskD {
    private List<Edge>[] tree;
    private long[] max;
    private int[] where;
    private long[] secondMax;
    private Integer[] order;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        tree = new List[counter];
        for (int i = 0; i < counter; ++i) {
            tree[i] = new ArrayList<>();
        }
        for (int e = 0; e < counter - 1; ++e) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            int length = in.nextInt();
            tree[u].add(new Edge(v, length));
            tree[v].add(new Edge(u, length));
        }
        prepare();
//        System.err.println(Arrays.toString(max));
        int queries = in.nextInt();
        DSU dsu = new DSU(counter);
        boolean[] interesting = new boolean[counter];
        for (int query = 0; query < queries; ++query) {
            Arrays.fill(interesting, false);
            dsu.cleanUp();
            long limit = in.nextLong();
            int ptr = 0;
            int answer = -1;
            for (int i = 0; i < order.length; ++i) {
                int current = order[i];
                for (Edge edge : tree[current]) {
                    if (interesting[edge.to]) {
                        dsu.unite(current, edge.to);
                    }
                }
                while (max[order[ptr]] - max[current] > limit) {
                    dsu.delete(order[ptr]);
                    interesting[order[ptr]] = false;
                    ptr++;
                }
                interesting[current] = true;
                answer = Math.max(answer, dsu.size(current));
            }
            out.println(answer);
        }
    }

    private void prepare() {
        max = new long[tree.length];
        where = new int[tree.length];
        secondMax = new long[tree.length];
        Arrays.fill(max, -1);
        Arrays.fill(where, -1);
        Arrays.fill(secondMax, -1);
        atFirst(0, -1);
        atSecond(0, -1, 0);
        order = new Integer[tree.length];
        for (int i = 0; i < order.length; ++i) {
            order[i] = i;
        }
        Arrays.sort(order, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -Long.compare(max[o1], max[o2]);
            }
        });
    }

    private void atFirst(int vertex, int parent) {
        if (tree[vertex].size() == 1 && tree[vertex].get(0).to == parent) {
            max[vertex] = 0;
            return;
        }
        for (int i = 0; i < tree[vertex].size(); ++i) {
            Edge edge = tree[vertex].get(i);
            if (edge.to == parent) continue;
            atFirst(edge.to, vertex);
            long distance = max[edge.to] + edge.length;
            if (distance > max[vertex]) {
                secondMax[vertex] = max[vertex];
                max[vertex] = distance;
                where[vertex] = i;
            } else if (distance > secondMax[vertex]) {
                secondMax[vertex] = distance;
            }
        }
    }

    private void atSecond(int vertex, int parent, long distanceUp) {
        for (int i = 0; i < tree[vertex].size(); ++i) {
            Edge edge = tree[vertex].get(i);
            if (edge.to == parent) continue;
            atSecond(edge.to, vertex, Math.max(distanceUp, i == where[vertex] ?
                    secondMax[vertex] : max[vertex]) + edge.length);
        }
        max[vertex] = Math.max(max[vertex], distanceUp);
    }

    static class Edge {
        int to;
        int length;

        public Edge(int to, int length) {
            this.to = to;
            this.length = length;
        }
    }

    static class DSU {
        int[] parent;
        int[] size;

        public DSU(int capacity) {
            parent = new int[capacity];
            size = new int[capacity];
            cleanUp();
        }

        public void cleanUp() {
            for (int i = 0; i < parent.length; ++i) {
                parent[i] = i;
            }
            Arrays.fill(size, 1);
        }

        public int get(int v) {
            return v == parent[v] ? v : (parent[v] = get(parent[v]));
        }

        public boolean unite(int parent, int child) {
            parent = get(parent);
            child = get(child);
            if (parent == child) return false;
            this.parent[child] = parent;
            size[parent] += size[child];
            return true;
        }

        public int size(int v) {
            return size[get(v)];
        }

        public void delete(int v) { // ad hoc delete
            int p = get(v);
            if (p != v) {
                size[p]--;
            }
            parent[v] = v;
        }
    }
}
