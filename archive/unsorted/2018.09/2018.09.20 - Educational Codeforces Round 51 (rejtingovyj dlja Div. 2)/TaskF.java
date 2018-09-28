package task;

import algoribrary.collections.Heap;
import algoribrary.io.InputReader;
import algoribrary.misc.ArrayUtils;

import java.io.PrintWriter;
import java.util.*;
import java.util.function.IntUnaryOperator;

public final class TaskF {
    public void solve(int __, InputReader in, PrintWriter out) {
        int verticesQty = in.nextInt();
        int edgesQty = in.nextInt();
        Edge[] edges = new Edge[edgesQty];
        List<Edge>[] graph = new List[verticesQty];
        Arrays.setAll(graph, i -> new ArrayList<>());
        for (int i = 0; i < edgesQty; ++i) {
            Edge edge = Edge.readEdge(in);
            edges[i] = edge;
            graph[edge.u].add(edge);
            graph[edge.v].add(edge);
        }
        Arrays.sort(edges, Comparator.comparingInt(Edge::getDistance));
        DSU dsu = new DSU(verticesQty);
        List<Edge> others = new ArrayList<>(edgesQty - verticesQty + 1);
        Map<Integer, long[]> dijkstra = new HashMap<>();
        List<Edge>[] tree = new List[verticesQty];
        Arrays.setAll(tree, i -> new ArrayList<>());
        for (Edge edge : edges) {
            if (dsu.unite(edge.u, edge.v)) {
                tree[edge.u].add(edge);
                tree[edge.v].add(edge);
            } else {
                others.add(edge);
                if (!dijkstra.containsKey(edge.u)) {
                    dijkstra.put(edge.u, computeDistances(edge.u, graph));
                }
                if (!dijkstra.containsKey(edge.v)) {
                    dijkstra.put(edge.v, computeDistances(edge.v, graph));
                }
            }
        }
        long[] distanceFromRoot = new long[verticesQty];
        int[] layer = new int[verticesQty];
        int[][] parents = new int[verticesQty][];
        dfs(0, null, tree, parents, layer, distanceFromRoot);

        int queriesQty = in.nextInt();
        for (int q = 0; q < queriesQty; ++q) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            int lca = lca(u, v, parents, layer);
            long answer = distanceFromRoot[u] + distanceFromRoot[v] - 2 * distanceFromRoot[lca];
            for (Edge edge : others) {
                long[] uDist = dijkstra.get(edge.u);
                long[] vDist = dijkstra.get(edge.v);
                answer = Math.min(answer, edge.distance +
                        Math.min(uDist[u] + vDist[v], uDist[v] + vDist[u]));
            }
            out.println(answer);
        }
    }

    private void dfs(int vertex, Edge incoming, List<Edge>[] tree, int[][] parents, int[] layer, long[] distanceFromRoot) {
        if (incoming != null) {
            int parent = incoming.other(vertex);
            layer[vertex] = 1 + layer[parent];
            int parentsQty = parents[parent].length;
            if ((layer[vertex] & layer[vertex] - 1) == 0) {
                ++parentsQty;
            }
            parents[vertex] = new int[parentsQty];
            parents[vertex][0] = parent;
            for (int pow = 1; pow < parentsQty; ++pow) {
                parents[vertex][pow] = parents[parents[vertex][pow - 1]][pow - 1];
            }
            distanceFromRoot[vertex] = distanceFromRoot[parent] + incoming.distance;
        } else {
            parents[vertex] = new int[0];
        }
        for (Edge edge : tree[vertex]) {
            if (edge != incoming) {
                dfs(edge.other(vertex), edge, tree, parents, layer, distanceFromRoot);
            }
        }
    }

    private int lca(int u, int v, int[][] parents, int[] layer) {
        if (layer[u] != layer[v]) {
            if (layer[u] < layer[v]) {
                int t = u;
                u = v;
                v = t;
            }
            int diff = layer[u] - layer[v];
            for (int bit = parents[u].length; bit --> 0; ) {
                if (((diff >>> bit) & 1) == 1) {
                    u = parents[u][bit];
                }
            }
            assert layer[u] == layer[v];
        }
        if (u == v) {
            return v;
        }
        for (int bit = parents[u].length; bit --> 0; ) {
            if (bit < parents[u].length && parents[u][bit] != parents[v][bit]) {
                u = parents[u][bit];
                v = parents[v][bit];
            }
        }
        return parents[u][0];
    }

    private long[] computeDistances(int start, List<Edge>[] graph) {
        long[] distanceTo = new long[graph.length];
        Arrays.fill(distanceTo, Long.MAX_VALUE / 3);
        Heap heap = new Heap(graph.length, graph.length, (u, v) -> Long.compare(distanceTo[u], distanceTo[v]));
        distanceTo[start] = 0;
        heap.add(start);
        while (!heap.isEmpty()) {
            int vertex = heap.pop();
            for (Edge edge : graph[vertex]) {
                int next = edge.other(vertex);
                long relaxed = distanceTo[vertex] + edge.distance;
                if (relaxed < distanceTo[next]) {
                    distanceTo[next] = relaxed;
                    if (heap.contains(next)) {
                        heap.siftUp(next);
                    } else {
                        heap.add(next);
                    }
                }
            }
        }
        return distanceTo;
    }

    private static class DSU {
        final int[] parent;
        final int[] rang;

        DSU(int capacity) {
            parent = new int[capacity];
            rang = new int[capacity];
            Arrays.setAll(parent, IntUnaryOperator.identity());
        }

        boolean unite(int u, int v) {
            u = get(u);
            v = get(v);
            if (v == u) {
                return false;
            }
            if (rang[v] < rang[u]) {
                int t = v;
                v = u;
                u = t;
            }
            parent[u] = v;
            if (rang[u] == rang[v]) {
                rang[v]++;
            }
            return true;
        }

        int get(int v) {
            return v == parent[v] ? v : (parent[v] = get(parent[v]));
        }
    }

    private static class Edge {
        final int u;
        final int v;
        final int distance;

        Edge(int u, int v, int distance) {
            this.u = u;
            this.v = v;
            this.distance = distance;
        }

        static Edge readEdge(InputReader in) {
            return new Edge(in.nextInt() - 1, in.nextInt() - 1, in.nextInt());
        }

        int other(int vertex) {
            assert vertex == v || vertex == u;
            return vertex ^ v ^ u;
        }

        int getDistance() {
            return distance;
        }
    }
}
