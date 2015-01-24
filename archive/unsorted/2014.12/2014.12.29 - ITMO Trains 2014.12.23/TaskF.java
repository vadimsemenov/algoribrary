package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TaskF {
    private static final int INF = 3_000_000;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int courses = in.nextInt();
        int lessons = in.nextInt();
        int[] need = new int[courses];
        int root = 0;
        int vertices = 1;
        for (int i = 0; i < courses; ++i) {
            need[i] = in.nextInt();
            vertices += need[i] + 1;
        }
        int[][] code = new int[vertices][];
        Edge[] list = new Edge[vertices - 1 + lessons];
        int ptr = 0;
        for (int i = 0; i < courses; ++i) {
            code[i] = new int[need[i] + 1];
            if (i == 0) {
                code[i][0] = 1;
            } else {
                code[i][0] = code[i - 1][code[i - 1].length - 1] + 1;
            }
            list[ptr++] = new Edge(root, code[i][0], 0);
            for (int j = 1; j <= need[i]; ++j) {
                code[i][j] = code[i][j - 1] + 1;
                list[ptr++] = new Edge(code[i][j], code[i][j - 1], 0);
            }
        }
        for (int i = 0; i < lessons; ++i) {
            int c1 = in.nextInt() - 1;
            int l1 = in.nextInt();
            int c2 = in.nextInt() - 1;
            int l2 = in.nextInt();
            int cost = in.nextInt();
            list[ptr++] = new Edge(code[c1][l1], code[c2][l2], cost);
        }
        boolean[] reachable = new boolean[vertices];
        reachable[root] = true;
        for (int it = 1; it < vertices; ++it) {
            for (Edge edge : list) {
                reachable[edge.end] |= reachable[edge.begin];
            }
        }
        boolean good = true;
        for (int i = 0; i < courses; ++i) {
            good &= reachable[code[i][need[i]]];
        }
        out.println(good ? mdst(root, vertices, list) : -1);
    }

    private int mdst(int root, int vertices, Edge[] list) {
        Edge[] minIncoming = new Edge[vertices];
        for (Edge edge : list) {
            if (minIncoming[edge.end] == null || minIncoming[edge.end].cost > edge.cost) {
                minIncoming[edge.end] = edge;
            }
        }
        List<Integer>[] graph = new List[vertices];
        List<Integer>[] reversedGraph = new List[vertices];
        for (int i = 0; i < vertices; ++i) {
            graph[i] = new ArrayList<>();
            reversedGraph[i] = new ArrayList<>();
        }
        for (int i = 0; i < vertices; ++i) if (i != root) {
            graph[minIncoming[i].begin].add(minIncoming[i].end);
            reversedGraph[minIncoming[i].end].add(minIncoming[i].begin);
        }
        List<Integer> order = new ArrayList<>();
        boolean[] foo = new boolean[vertices];
        for (int v = 0; v < vertices; ++v) if (!foo[v]) {
            dfs(v, graph, foo, order);
        }
        Collections.reverse(order);
        Arrays.fill(foo, false);
        int[] color = new int[vertices];
        Arrays.fill(color, -1);
        int newVertices = 0;
        for (int vertex : order) if (color[vertex] == -1) {
            List<Integer> component = new ArrayList<>();
            dfs(vertex, reversedGraph, foo, component);
            for (int v : component) {
                color[v] = newVertices;
            }
            newVertices++;
        }
        int cost = 0;
        for (int i = 0; i < vertices; ++i) {
            if (i != root) {
                cost += minIncoming[i].cost;
            }
        }
        int newEdges = 0;
        for (Edge edge : list) {
            if (color[edge.begin] != color[edge.end]) {
                newEdges++;
            }
        }
        Edge[] newList = new Edge[newEdges];
        int ptr = 0;
        for (Edge edge : list) {
            if (color[edge.begin] != color[edge.end]) {
                newList[ptr++] = new Edge(color[edge.begin], color[edge.end], edge.cost - minIncoming[edge.end].cost);
            }
        }
        return newVertices == vertices ? cost : cost + mdst(color[root], newVertices, newList);
    }

    private void dfs(int vertex, List<Integer>[] graph, boolean[] used, List<Integer> buffer) {
        used[vertex] = true;
        for (int to : graph[vertex]) if (!used[to]) {
            dfs(to, graph, used, buffer);
        }
        buffer.add(vertex);
    }

    private void nothing(int testNumber, InputReader in, PrintWriter out) {
        int courses = in.nextInt();
        int lessons = in.nextInt();
        int[] need = new int[courses];
        int root = 0;
        int vertices = 1;
        for (int i = 0; i < courses; ++i) {
            need[i] = in.nextInt();
            vertices += need[i] + 1;
        }
        int[][] code = new int[vertices][];
        cost = new int[2 * vertices];
        List<Heap> incoming = new ArrayList<>();
        incoming.add(new Heap());
        for (int i = 0; i < courses; ++i) {
            code[i] = new int[need[i] + 1];
            code[i][0] = incoming.size();
            incoming.add(new Heap());
            incoming.get(code[i][0]).add(new Edge(root, code[i][0], 0));
            for (int j = 1; j <= need[i]; ++j) {
                code[i][j] = incoming.size();
                incoming.add(new Heap());
                Edge edge = new Edge(code[i][j], code[i][j - 1], 0);
                incoming.get(edge.end).add(edge);
            }
        }
        for (int i = 0; i < lessons; ++i) {
            int c1 = in.nextInt() - 1;
            int l1 = in.nextInt();
            int c2 = in.nextInt() - 1;
            int l2 = in.nextInt();
            int cost = in.nextInt();
            Edge edge = new Edge(code[c1][l1], code[c2][l2], cost);
            incoming.get(edge.end).add(edge);
        }
        for (int i = 0; i < courses; ++i) {
            incoming.get(root).add(new Edge(code[i][need[i]], root, INF * (i + 1)));
        }
        boolean[] reachable = new boolean[vertices];
        reachable[root] = true;
        for (int it = 0; it < vertices - 1; ++it) {
            for (Heap heap : incoming) {
                for (Edge edge : heap.data) {
                    reachable[edge.end] |= reachable[edge.begin];
                }
            }
        }
        for (int i = 0; i < courses; ++i) {
            if (!reachable[code[i][need[i]]]) {
                out.println(-1);
                return;
            }
        }
        contract(incoming);
        int answer = 0;
        for (int i = 1; i < cost.length; ++i) {
            answer += cost[i];
        }
        out.println(answer);
    }

    private void contract(List<Heap> incoming) {
        int current = 0;
        DSU dsu = new DSU(incoming.size());
        List<Edge> taken = new ArrayList<>(incoming.size());
        for (int i = 0; i < incoming.size(); ++i) {
            taken.add(null);
        }
        while (!incoming.get(current).isEmpty()) {
            Edge edge = incoming.get(current).pollFirst();
            System.err.println(edge.begin + " " + edge.end + " " + edge.cost + " " + current);
            int next = dsu.component(edge.begin);
            if (next == current) {
                System.err.println(current + " " + next);
                continue;
            }
            taken.set(current, edge);
            if (taken.get(next) == null) {
                System.err.println(current + " yoy");
                current = next;
            } else {
                int superVertex = dsu.add();
                incoming.add(new Heap());
                taken.add(null);
                while (dsu.component(current) != dsu.component(next)) {
                    edge = taken.get(next);
                    cost[edge.end] = edge.cost;
                    dsu.union(superVertex, next);
                    incoming.get(superVertex).merge(incoming.get(next));
                    next = dsu.component(taken.get(next).begin);
                }
                current = superVertex;
            }
        }
    }

    private int[] cost;

    static class DSU {
        private List<Integer> parent;

        public DSU(int size) {
            parent = new ArrayList<>(size);
            for (int i = 0; i < size; ++i) {
                parent.add(i);
            }
        }

        public int add() {
            int component = parent.size();
            parent.add(component);
            return component;
        }

        public int component(int v) {
            return v == parent.get(v) ? v : (parent.set(v, component(parent.get(v))));
        }

        public boolean union(int v, int u) {
            v = component(v);
            u = component(u);
            if (v == u) {
                return false;
            }
            if (v % 7 > 3) {
                int tmp = v;
                v = u;
                u = tmp;
            }
            parent.set(u, v);
            return true;
        }
    }

    static class Edge {
        public final int begin, end;
        public final int cost;

        public Edge(int begin, int end, int cost) {
            this.begin = begin;
            this.end = end;
            this.cost = cost;
        }
    }

    class Heap {
        private List<Edge> data;
        private int size;

        public Heap() {
            data = new ArrayList<>();
            size = 0;
        }

        public void add(Edge edge) {
            data.add(edge);
            size++;
            siftUp(size - 1);
        }

        public Edge pollFirst() {
            Edge result = data.get(0);
            size--;
            Collections.swap(data, 0, size);
            // data.remove(size);
            siftDown(0);
            return result;
        }

        public void heapify() {
            for (int i = data.size() - 1; i > 0; --i) {
                siftDown(i);
            }
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void merge(Heap other) {
            List<Edge> list = other.data;
            int size = other.size;
            if (this.size < other.size) {
                list = this.data;
                size = this.size;
                this.data = other.data;
            }
            for (int i = 0; i < size; ++i) {
                add(list.get(i));
            }
        }

        private void siftUp(int idx) {
            while (idx > 0) {
                int parent = (idx - 1) >> 1;
                Edge us = data.get(idx);
                Edge his = data.get(parent);
                if (us.cost - cost[us.end] < his.cost - cost[his.end]) {
                    Collections.swap(data, idx, parent);
                    idx = parent;
                } else {
                    break;
                }
            }
        }

        private void siftDown(int idx) {
            while (idx < data.size()) {
                int child = (idx << 1) + 1;
                if (child >= data.size()) {
                    break;
                }
                if (child + 1 < data.size()) {
                    Edge us = data.get(child);
                    Edge his = data.get(child + 1);
                    if (us.cost - cost[us.end] > his.cost - cost[his.end]) {
                        child = child + 1;
                    }
                }
                Edge us = data.get(idx);
                Edge his = data.get(child);
                if (us.cost - cost[us.end] > his.cost - cost[his.end]) {
                    Collections.swap(data, idx, child);
                    idx = child;
                } else {
                    break;
                }
            }
        }
    }
}
