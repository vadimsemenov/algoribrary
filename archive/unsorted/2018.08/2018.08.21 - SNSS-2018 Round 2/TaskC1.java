package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskC1 {
    public void solve(int __, InputReader in, PrintWriter out) {
        int citiesQty = in.nextInt();
        int roadsQty = in.nextInt();
        int days = in.nextInt();
        int[] degree = new int[citiesQty];
        int[] u = new int[roadsQty];
        int[] v = new int[roadsQty];
        int[] destroyTime = new int[roadsQty];
        int[] count = new int[days];
        int[][] order = new int[days][];
        for (int i = 0; i < roadsQty; ++i) {
            u[i] = in.nextInt() - 1;
            v[i] = in.nextInt() - 1;
            degree[u[i]]++;
            degree[v[i]]++;
            destroyTime[i] = in.nextInt();
            count[days - destroyTime[i]]++;
        }
        for (int i = 0; i < days; ++i) {
            order[i] = new int[count[i]];
            count[i] = 0;
        }
        for (int i = 0; i < roadsQty; ++i) {
            int time = days - destroyTime[i];
            order[time][count[time]++] = i;
        }
        DSU dsu = new DSU(citiesQty + roadsQty);
        for (int i = 0; i < citiesQty; ++i) {
            dsu.allocate(days, 1);
        }
        for (int[] roads : order) {
            for (int road : roads) {
                int vp = dsu.get(v[road]);
                int up = dsu.get(u[road]);
                if (vp == up) continue;
                int p = dsu.allocate(destroyTime[road], 0);
                dsu.hang(vp, p);
                dsu.hang(up, p);
            }
        }

        long[] fees = new long[dsu.size()];
        int root = fees.length - 1;
        fees[root] = (long) dsu.size[root] * dsu.time[root];
        for (int vertex = root; vertex --> 0; ) {
            int parent = dsu.parent[vertex];
            fees[vertex] = fees[parent] + (long) dsu.size[vertex] * (dsu.time[vertex] - dsu.time[parent]);
        }

        int[][] graph = buildGraph(citiesQty, roadsQty, degree, u, v);
        int[] queue = new int[citiesQty];
        int[] distance = new int[citiesQty];
        Arrays.fill(distance, -1);
        int head = 0;
        int tail = 0;
        queue[tail++] = 0;
        distance[0] = 0;
        long answer = 0;
        while (head < tail) {
            int current = queue[head++];
            answer = Math.max(answer, fees[current]);
            for (int edge : graph[current]) {
                int next = current ^ v[edge] ^ u[edge];
                if (distance[next] != -1 || destroyTime[edge] <= distance[current]) continue;
                distance[next] = distance[current] + 1;
                queue[tail++] = next;
            }
        }
        out.println(answer);
    }

    private int[][] buildGraph(int citiesQty, int roadsQty, int[] degree, int[] u, int[] v) {
        int[][] graph = new int[citiesQty][];
        for (int i = 0; i < citiesQty; ++i) {
            graph[i] = new int[degree[i]];
            degree[i] = 0;
        }
        for (int i = 0; i < roadsQty; ++i) {
            graph[v[i]][degree[v[i]]++] = i;
            graph[u[i]][degree[u[i]]++] = i;
        }
        return graph;
    }

    static class DSU {
        final int[] parent;
        final int[] compressed;
        final int[] size;
        final int[] time;
        int payload = 0;

        DSU(int capacity) {
            parent = new int[capacity];
            compressed = new int[capacity];
            size = new int[capacity];
            time = new int[capacity];
        }

        int allocate(int t, int s) {
            int id = payload;
            parent[payload] = id;
            compressed[payload] = id;
            size[payload] = s;
            time[payload] = t;
            payload++;
            return id;
        }

        int size() {
            return payload;
        }

        int get(int v) {
            int p = compressed[v];
            if (parent[p] == p) {
                return p;
            }
            p = get(parent[p]);
            compressed[v] = p;
            return p;
        }

        void hang(int v, int p) {
            if (parent[p] != p) {
                throw new IllegalArgumentException();
            }
            v = get(v);
            assert v != p;
            parent[v] = p;
            size[p] += size[v];
        }
    }
}
