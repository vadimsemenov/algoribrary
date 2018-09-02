package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        List<Integer>[] graph = new List[vertices];
        for (int i = 0; i < vertices; ++i) graph[i] = new ArrayList<>();
        for (int e = 0; e < edges; ++e) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            graph[u].add(v);
            graph[v].add(u);
        }
        boolean[] visited = new boolean[vertices];
        List<Integer> component = new ArrayList<>();
        for (int i = 0; i < vertices; ++i) if (!visited[i]) {
            component.clear();
            dfs(i, graph, visited, component);
            for (int v : component) if (component.size() != graph[v].size() + 1) {
                out.println("NO");
                return;
            }
        }
        out.println("YES");
    }

    private void dfs(int vertex, List<Integer>[] graph, boolean[] visited, List<Integer> component) {
        visited[vertex] = true;
        component.add(vertex);
        for (int child : graph[vertex]) if (!visited[child]) {
            dfs(child, graph, visited, component);
        }
    }
}
