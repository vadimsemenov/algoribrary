package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskC {
    List<Integer>[] tree;
    DSU dsu;
    boolean[] used;
    int[] length;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        int queries = in.nextInt();
        tree = new List[vertices];
        for (int i = 0; i < vertices; ++i) tree[i] = new ArrayList<>();
        dsu = new DSU(vertices);
        for (int i = 0; i < edges; ++i) {
            int v = in.nextInt() - 1;
            int u = in.nextInt() - 1;
            tree[v].add(u);
            tree[u].add(v);
            dsu.union(v, u);
        }
        used = new boolean[vertices];
        length = new int[vertices];
        for (int v = 0; v < vertices; ++v) if (!used[v]) {
            flag = false;
            dfs(v);
            flag = true;
            dfs(end);
            length[dsu.get(v)] = max;
        }
        for (int q = 0; q < queries; ++q) {
            int type = in.nextInt();
            if (type == 1) {
                out.println(length[dsu.get(in.nextInt() - 1)]);
            } else if (type == 2) {
                int x = in.nextInt() - 1;
                int y = in.nextInt() - 1;
                x = dsu.get(x);
                y = dsu.get(y);
                if (x == y) continue;
                int p = dsu.union(x, y);
                length[p] = Math.max((length[x] + 1) / 2 + (length[y] + 1) / 2 + 1,
                        Math.max(length[x], length[y]));
            } else throw new AssertionError(type);
        }
    }

    int max, end;
    boolean flag;
    private void dfs(int vertex) {
        used[vertex] = true;
        int max = -1;
        int end = vertex;
        for (int to : tree[vertex]) if (!used[to]) {
            dfs(to);
            if (max < this.max) {
                max = this.max;
                end = this.end;
            }
        }
        used[vertex] = flag;
        this.max = max + 1;
        this.end = end;
    }
}

class DSU {
    int[] parent;
    int[] rang;

    DSU(int size) {
        parent = new int[size];
        for (int i = 0; i < size; ++i) parent[i] = i;
        rang = new int[size];
        Arrays.fill(rang, 1);
    }

    int get(int x) {
        return x == parent[x] ? x : (parent[x] = get(parent[x]));
    }

    int union(int x, int y) {
        int px = get(x);
        int py = get(y);
        if (px == py) return px;
        if (rang[px] < rang[py]) {
            int t = py;
            py = px;
            px = t;
        }
        if (rang[px] == rang[py]) rang[px]++;
        parent[py] = px;
        return px;
    }
}
