package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TaskD {
    static class Vertex {
        int id;
        List<Integer> outgoing;
        List<Integer> weight;

        Vertex(int id) {
            this.id = id;
            outgoing = new ArrayList<>();
            weight = new ArrayList<>();
        }
    }
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[][] distance = new int[counter][counter];
        for (int i = 0; i < counter; ++i) {
            for (int j = 0; j < counter; ++j) {
                distance[i][j] = in.nextInt();
            }
        }
        boolean tree = true;
        int[][] weight = new int[counter * counter][2];
        int ptr = 0;
        for (int i = 0; i < counter && tree; ++i) {
            if (distance[i][i] != 0) {
                tree = false;
            }
            for (int j = 0; j < i; ++j) {
                if (distance[i][j] != distance[j][i] || distance[i][j] == 0) {
                    tree = false;
                }
                weight[ptr][0] = distance[i][j];
                weight[ptr][1] = i * counter + j;
                ptr++;
            }
        }
        if (!tree) {
            out.println("NO");
            return;
        }
        Arrays.sort(weight, 0, ptr, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        });

        DSU dsu = new DSU(counter);
        long[][] real = new long[counter][counter];
        for (long[] r : real)
            Arrays.fill(r, -1);
        for (int i = 0; i < counter; ++i) {
            real[i][i] = 0;
        }
        Vertex[] graph = new Vertex[counter];
        for (int i = 0; i < counter; ++i) {
            graph[i] = new Vertex(i);
        }
        for (int i = 0; i < ptr; ++i) {
            int w = weight[i][0];
            int u = weight[i][1] / counter;
            int v = weight[i][1] % counter;
            int pv = dsu.get(v);
            int pu = dsu.get(u);
            if (pv != pu) {
                real[u][v] = w;
                graph[v].outgoing.add(u);
                graph[v].weight.add(w);
                graph[u].outgoing.add(v);
                graph[u].weight.add(w);
                dsu.union(pv, pu);
            }
        }
        for (int v = 0; v < counter; ++v) {
            dfs(v, -1, v, 0, graph, real);
        }
        for (int i = 0; i < counter; ++i) {
            for (int j = 0; j < counter; ++j) {
                if (distance[i][j] != real[i][j]) {
                    out.println("NO");
                    return;
                }
            }
        }
        out.println("YES");
    }

    private void dfs(int vertex, int parent, int target, long distance, Vertex[] graph, long[][] real) {
        real[target][vertex] = real[vertex][target] = distance;
        for (int i = 0; i < graph[vertex].outgoing.size(); ++i) {
            int to = graph[vertex].outgoing.get(i);
            int w = graph[vertex].weight.get(i);
            if (to != parent) {
                dfs(to, vertex, target, distance + w, graph, real);
            }
        }
    }
}

class DSU {
    int[] parent;
    List<Integer>[] component;

    DSU(int size) {
        this.parent = new int[size];
        for (int i = 0; i < parent.length; ++i) {
            parent[i] = i;
        }
        component = new List[size];
        for (int i = 0; i < component.length; ++i) {
            component[i] = new ArrayList<>();
            component[i].add(i);
        }
    }

    int get(int v) {
        return v == parent[v] ? v : (parent[v] = get(parent[v]));
    }

    void union(int v, int u) {
        v = get(v);
        u = get(u);
        if (v == u)
            return;
        if (component[v].size() < component[u].size()) {
            int tmp = v;
            v = u;
            u = tmp;
        }
        parent[u] = v;
        for (int t : component[u]) {
            component[v].add(t);
        }
    }
}
