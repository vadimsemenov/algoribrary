package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TaskC {
    private static final int MODULO = 1_000_000_007;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int[] cost = new int[vertices];
        for (int i = 0; i < vertices; i++) cost[i] = in.nextInt();
        List<Integer>[] graph = new List[vertices];
        List<Integer>[] reverse = new List[vertices];
        for (int i = 0; i < vertices; i++) {
            graph[i] = new ArrayList<>();
            reverse[i] = new ArrayList<>();
        }
        int edges = in.nextInt();
        for (int e = 0; e < edges; e++) {
            int v = in.nextInt() - 1;
            int u = in.nextInt() - 1;
            graph[v].add(u);
            reverse[u].add(v);
        }
        List<Integer> order = new ArrayList<>();
        boolean[] used = new boolean[vertices];
        for (int v = 0; v < vertices; v++) if (!used[v]) dfs(v, graph, used, order);
        Collections.reverse(order);
        Arrays.fill(used, false);
        long summary = 0;
        long ways = 1;
        for (int v : order) if (!used[v]) {
            List<Integer> component = new ArrayList<>();
            dfs(v, reverse, used, component);
            int minCost = Integer.MAX_VALUE;
            int counter = 0;
            for (int vertex : component) {
                if (cost[vertex] < minCost) {
                    minCost = cost[vertex];
                    counter = 1;
                } else if (cost[vertex] == minCost) {
                    counter++;
                }
            }
            summary += minCost;
            ways *= counter;
            ways %= MODULO;
        }
        out.println(summary + " " + ways);
    }

    private void dfs(int vertex, List<Integer>[] graph, boolean[] used, List<Integer> buffer) {
        used[vertex] = true;
        for (int to : graph[vertex]) if (!used[to]) dfs(to, graph, used, buffer);
        buffer.add(vertex);
    }
}
