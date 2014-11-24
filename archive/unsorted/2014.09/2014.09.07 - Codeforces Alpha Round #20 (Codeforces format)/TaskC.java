package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.*;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        long[][] result = dijkstra(readGraph(vertices, edges, in), 0);
        if (result[0][vertices - 1] == Long.MAX_VALUE) {
            out.println("-1");
            return;
        }
        long[] from = result[1];
        List<Integer> path = new ArrayList<>();
        int current = vertices - 1;
        while (current >= 0) {
            path.add(current + 1);
            current = (int) from[current];
        }
        Collections.reverse(path);
        for (int vertex : path) {
            out.print(vertex);
            out.print(" ");
        }
        out.println();
    }

    static class Edge {
        int to, weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    private List<Edge>[] readGraph(int vertices, int edges, InputReader in) {
        List<Edge>[] graph = new List[vertices];
        for (int i = 0; i < vertices; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < edges; i++) {
            int v = in.nextInt() - 1;
            int u = in.nextInt() - 1;
            int w = in.nextInt();
            graph[v].add(new Edge(u, w));
            graph[u].add(new Edge(v, w));
        }
        return graph;
    }

    private long[][] dijkstra(List<Edge>[] graph, int start) {
        final long[] distance = new long[graph.length];
        Arrays.fill(distance, Long.MAX_VALUE);
        distance[start] = 0;
        long[] from = new long[graph.length];
        Arrays.fill(from, -1);
        MinIntervalTree tree = new MinIntervalTree(graph.length);
        tree.update(start, 0);
        while (tree.getMin() != Long.MAX_VALUE) {
            int current = tree.getMinIdx();
            tree.update(current, Long.MAX_VALUE);
            for (Edge edge : graph[current]) {
                int to = edge.to;
                long relaxed = distance[current] + edge.weight;
                if (relaxed < distance[to]) {
                    from[to] = current;
                    distance[to] = relaxed;
                    tree.update(to, distance[to]);
                }
            }
        }
        return new long[][]{distance, from};
    }
}

class MinIntervalTree {
    private long[] min;
    private int[] idx;
    private int size;

    public MinIntervalTree(int elems) {
        this.size = Integer.highestOneBit(elems) << 1;
        if ((elems & elems - 1) == 0) size >>= 1;
        min = new long[size << 1];
        Arrays.fill(min, Long.MAX_VALUE);
        idx = new int[size << 1];
        Arrays.fill(idx, -1);
        for (int i = 0; i < size; ++i) idx[size + i] = i;
    }

    public void update(int at, long value) {
        at += size;
        min[at] = value;
        at >>= 1;
        while (at > 0) {
            int lower = at << 1;
            if (min[lower] > min[lower + 1]) lower++;
            min[at] = min[lower];
            idx[at] = idx[lower];
            at >>= 1;
        }
    }

    public long getMin() {
        return min[1];
    }

    public int getMinIdx() {
        return idx[1];
    }
}
