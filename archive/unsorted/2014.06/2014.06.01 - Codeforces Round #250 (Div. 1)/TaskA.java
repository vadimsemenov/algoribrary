package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        final int[] cost = new int[vertices];
        for (int i = 0; i < vertices; i++) cost[i] = in.nextInt();
        Integer[] indices = new Integer[vertices];
        for (int i = 0; i < vertices; i++) indices[i] = i;
        Arrays.sort(indices, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -Integer.compare(cost[o1], cost[o2]);
            }
        });
        boolean[][] graph = new boolean[vertices][vertices];
        for (int i = 0; i < edges; i++) {
            int v = in.nextInt() - 1;
            int u = in.nextInt() - 1;
            graph[v][u] = graph[u][v] = true;
        }
        boolean[] used = new boolean[vertices];
        int answer = 0;
        for (int i = 0; i < vertices; i++) {
            int v = indices[i];
            used[v] = true;
            for (int j = 0; j < vertices; j++) if (graph[v][j] && !used[j]) {
                answer += cost[j];
            }
        }
        out.println(answer);
    }
}
