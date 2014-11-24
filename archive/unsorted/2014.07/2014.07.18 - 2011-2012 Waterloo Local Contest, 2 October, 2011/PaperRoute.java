package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class PaperRoute {
    static class Edge {
        int to;
        long weight;

        Edge(int to, long weight) {
            this.to = to;
            this.weight = weight;
        }
    }
    
    private long[] distanceToCampus;
    private List<Edge>[] tree;
    
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        distanceToCampus = new long[counter + 1];
        for (int i = 0; i <= counter; i++) distanceToCampus[i] = in.nextInt();
        tree = new List[counter + 1];
        for (int i = 0; i <= counter; i++) tree[i] = new ArrayList<>();
        for (int i = 0; i < counter; i++) {
            int v = in.nextInt();
            int u = in.nextInt();
            int w = in.nextInt();
            tree[v].add(new Edge(u, w));
            tree[u].add(new Edge(v, w));
        }
        
        updateDistance(0, -1, 0);
        long time = dfs(0, -1);
        long best = Long.MAX_VALUE;
        for (long p : distanceToCampus) best = Math.min(best, p);
        out.println(time + best);
    }

    private long dfs(int vertex, int parent) {
        long current = 0;
        for (Edge e : tree[vertex]) if (e.to != parent) {
            current += 2 * e.weight + dfs(e.to, vertex);
        }
        return current;
    }

    private void updateDistance(int vertex, int parent, long distanceToRoot) {
        distanceToCampus[vertex] -= distanceToRoot;
        for (Edge e : tree[vertex]) if (e.to != parent) {
            updateDistance(e.to, vertex, distanceToRoot + e.weight);
        }
    }
}
