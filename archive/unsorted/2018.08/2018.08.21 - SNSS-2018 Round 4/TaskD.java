package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        int[][] edgeList = in.nextIntTable(edges, 2);
        int[] degree = new int[vertices];
        for (int[] edge : edgeList) {
            for (int i = 0; i < edge.length; ++i) {
                edge[i]--;
            }
            degree[edge[0]]++;
        }
        int[][][] graph = new int[vertices][][];
        for (int i = 0; i < vertices; ++i) {
            graph[i] = new int[degree[i]][2];
            degree[i] = 0;
        }
        for (int i = 0; i < edgeList.length; i++) {
            int[] edge = edgeList[i];
            int from = edge[0];
            int to = edge[1];
            graph[from][degree[from]][0] = to;
            graph[from][degree[from]][1] = i;
            degree[from]++;
        }
        boolean[] visited = new boolean[vertices];
        List<Integer> topOrder = new ArrayList<>(vertices);
        for (int v = 0; v < vertices; ++v) {
            if (!visited[v]) {
                topSort(v, graph, visited, topOrder);
            }
        }
        int[] distance = new int[vertices];
        for (int vertex : topOrder) {
            for (int[] edge : graph[vertex]) {
                int next = edge[0];
                distance[vertex] = Math.max(distance[vertex], distance[next] + 1);
            }
        }
        int max = 0;
        for (int v = 0; v < vertices; ++v) {
            max = Math.max(max, distance[v]);
        }
        int[] count = new int[edges];
        int maxQty = 0;
        for (int v = 0; v < vertices; ++v) {
            if (distance[v] < max) continue;
            maxQty++;
            dfs(v, graph, distance, count);
        }
        boolean exists = false;
        for (int e = 0; e < edges; ++e) {
            if (count[e] == maxQty) {
                out.println(e + 1);
                exists = true;
            }
        }
        if (!exists) {
            out.println(-1);
        }
    }

    private void dfs(int vertex, int[][][] graph, int[] distance, int[] count) {
        int child = -1;
        int id = -1;
        for (int[] edge : graph[vertex]) {
            int next = edge[0];
            if (distance[vertex] == distance[next] + 1) {
                if (child != -1) return;
                child = next;
                id = edge[1];
            }
        }
        if (child != -1) {
            count[id]++;
            dfs(child, graph, distance, count);
        }
    }

    private void topSort(int vertex, int[][][] graph, boolean[] visited, List<Integer> topOrder) {
        visited[vertex] = true;
        for (int[] edge : graph[vertex]) {
            int next = edge[0];
            if (!visited[next]) {
                topSort(next, graph, visited, topOrder);
            }
        }
        topOrder.add(vertex);
    }
}
