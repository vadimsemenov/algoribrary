package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskE {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        int[] degrees = new int[vertices];
        DSU dsu = new DSU(vertices);
        int need = vertices;
        for (int e = 0; e < edges; ++e) {
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1;
            degrees[x]++;
            degrees[y]++;
            if ((need > 1 && dsu.get(x) == dsu.get(y)) || degrees[x] > 2 || degrees[y] > 2) {
                out.println("NO");
                return;
            }
            need--;
            dsu.union(x, y);
        }
        out.println("YES");
        out.println(need);
        for (int i = 0; i < vertices; ++i) {
            if (degrees[i] == 2) {
                continue;
            }
            degrees[i]++;
            for (int j = 0; j < vertices; ++j) {
                if (degrees[j] == 2 || (need > 1 && dsu.get(i) == dsu.get(j))) {
                    continue;
                }
                out.println((Math.min(i, j) + 1) + " " + (Math.max(i, j) + 1));
                degrees[j]++;
                dsu.union(i, j);
                need--;
                break;
            }
            i--;
        }
        if (need != 0) {
            throw new AssertionError(need);
        }
    }

    private static class DSU {
        int[] parent;
        int[] size;

        DSU(int elements) {
            parent = new int[elements];
            for (int i = 0; i < elements; ++i) {
                parent[i] = i;
            }
            size = new int[elements];
            Arrays.fill(size, 1);
        }

        public int get(int v) {
            return v == parent[v] ? v : (parent[v] = get(parent[v]));
        }

        public void union(int v, int u) {
            v = get(v);
            u = get(u);
            if (v == u) {
                return;
            }
            if (size[v] < size[u]) {
                int tmp = v; v = u; u = tmp;
            }
            parent[u] = v;
            size[v] += size[u];
        }
    }
}
