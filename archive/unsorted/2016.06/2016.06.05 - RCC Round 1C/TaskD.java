package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskD {
    final int LOG = 20;
    List<Integer>[] children;
    int[] level;
    int[][] ancestor;
    int[] order;
    int ptr;
    int[] position;
    int[] last;

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        children = new List[counter];
        for (int i = 0; i < counter; ++i) children[i] = new ArrayList<>();
        for (int i = 1; i < counter; ++i) {
            int parent = in.nextInt() - 1;
            children[parent].add(i);
        }
        buildLca();
        BIT box = new BIT(counter);
        int queries = in.nextInt();
        while (queries --> 0) {
            int type = in.nextInt();
            if (type == 1) {
                int u = in.nextInt() - 1;
                int v = in.nextInt() - 1;
                int w = lca(u, v);
                int uu = level[u] - box.get(position[u]);
                int vv = level[v] - box.get(position[v]);
                int ww = level[w] - box.get(position[w]);
                out.println(uu + vv - 2 * ww);
            } else if (type == 2) {
                int vertex = in.nextInt() - 1;
                box.update(position[vertex], last[vertex], 1);
            } else throw new AssertionError(type);
        }
    }

    private void buildLca() {
        level = new int[children.length];
        ancestor = new int[children.length][LOG + 1];
        order = new int[children.length];
        position = new int[children.length];
        last = new int[children.length];
        for (int[] p : ancestor) Arrays.fill(p, -1);
        dfs(0, -1, 0);
    }

    private int dfs(int vertex, int parent, int depth) {
        order[ptr] = vertex;
        last[vertex] = position[vertex] = ptr++;
        level[vertex] = depth;
        ancestor[vertex][0] = parent;
        for (int i = 1; i <= LOG; ++i) {
            if (ancestor[vertex][i - 1] < 0) break;
            ancestor[vertex][i] = ancestor[ancestor[vertex][i - 1]][i - 1];
        }
        for (int child : children[vertex]) {
            last[vertex] = dfs(child, vertex, depth + 1);
        }
        return last[vertex];
    }

    private int lca(int u, int v) {
        if (level[u] < level[v]) {
            u ^= v; v ^= u; u ^= v;
        }
        int diff = level[u] - level[v];
        for (int bit = LOG; bit >= 0; --bit) if ((diff >>> bit & 1) == 1){
            u = ancestor[u][bit];
        }
        if (u == v) return v;
        for (int bit = LOG; bit >= 0; --bit) {
            if (ancestor[u][bit] != ancestor[v][bit]) {
                u = ancestor[u][bit];
                v = ancestor[v][bit];
            }
        }
        return ancestor[u][0];
    }

    static class BIT {
        int[] data;

        BIT(int size) {
            data = new int[size];
        }

        // inclusive
        void update(int from, int to, int delta) {
            update(from, delta);
            update(to + 1, -delta);
        }

        void update(int from, int delta) {
            while (from < data.length) {
                data[from] += delta;
                from |= from + 1;
            }
        }

        int get(int at) {
            int result = 0;
            while (at >= 0) {
                result += data[at];
                at = (at & at + 1) - 1;
            }
            return result;
        }
    }
}
