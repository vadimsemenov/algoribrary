package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskE {
    private int[][] tree;
    private int[][] mem;

    public void solve(int __, InputReader in, PrintWriter out) {
        int verticesQty = in.nextInt();
        int[][] edges = in.nextIntTable(verticesQty - 1, 2);
        int[] degree = new int[verticesQty];
        for (int[] edge : edges) {
            for (int i = 0; i < edge.length; ++i) {
                edge[i]--;
                degree[edge[i]]++;
            }
        }
        tree = new int[verticesQty][];
        for (int i = 0; i < verticesQty; ++i) {
            tree[i] = new int[degree[i]];
            degree[i] = 0;
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            tree[u][degree[u]++] = v;
            tree[v][degree[v]++] = u;
        }
        mem = new int[verticesQty][4];
        for (int[] d : mem) Arrays.fill(d, -1);
        out.println(dfs(0, -1, 0));
    }

    private int dfs(int vertex, int parent, int distance) {
        if (mem[vertex][distance] != -1) {
            return mem[vertex][distance];
        }
        int result = dfs(vertex, parent, 1, 2); // connect root and vertex
        if (distance <= 2) { // good distance
            result = Math.min(result, dfs(vertex, parent, 0, distance + 1));
        }
        if (tree[vertex].length > 1) { // has child
            int sum = dfs(vertex, parent, 0, 3);
            int diff = Integer.MAX_VALUE;
            for (int child : tree[vertex]) {
                if (child != parent) {
                    // connect root and child, so distance to vertex is 2
                    diff = Math.min(diff, (1 + dfs(child, vertex, 1)) - dfs(child, vertex, 3));
                }
            }
            result = Math.min(result, sum + diff);
        }
        return mem[vertex][distance] = result;
    }

    private int dfs(int vertex, int parent, int result, int childDistance) {
        for (int child : tree[vertex]) {
            if (child != parent) {
                result += dfs(child, vertex, childDistance);
            }
        }
        return result;
    }
}
