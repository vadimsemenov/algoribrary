package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskC {
    private int citiesQty, roadsQty, days;
    // init graph
    private int[] firstEdge, nextEdge, to, destroyTime;

    private int[] parent;
    private int[] link;
    private int[] size;
    private int[] time;

    private int[] topologicalOrder;
    private int topOrderPtr;

    private long[] fees;

    public void solve(int __, InputReader in, PrintWriter out) {
        citiesQty = in.nextInt();
        roadsQty = in.nextInt();
        days = in.nextInt();
        init();
        int[] count = new int[days];
        int[][] order = new int[days][];
        for (int i = 0; i < roadsQty; ++i) {
            int time;
            addEdge(i, in.nextInt() - 1, in.nextInt() - 1, time = in.nextInt());
            count[days - time]++;
        }
        for (int i = 0; i < count.length; ++i) {
            order[i] = new int[count[i]];
            count[i] = 0;
        }
        for (int i = 0; i < roadsQty; ++i) {
            int time = days - destroyTime[i];
            order[time][count[time]++] = i;
        }

        fees = new long[citiesQty];
        for (int[] roads : order) {
            for (int edge : roads) {
                unite(to[edge * 2], to[edge * 2 + 1], destroyTime[edge]);
            }
        }

        int root = topologicalOrder[topOrderPtr];
        fees[root] += (long) size[root] * time[root];
        for (int ptr = topOrderPtr; ptr-- > 0; ) {
            int vertex = topologicalOrder[ptr];
            fees[vertex] += fees[parent[vertex]];
        }

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
            for (int edge = firstEdge[current]; edge != -1; edge = nextEdge[edge]) {
                int next = to[edge];
                if (distance[next] != -1 || destroyTime[edge >>> 1] <= distance[current]) continue;
                distance[next] = distance[current] + 1;
                queue[tail++] = next;
            }
        }
        out.println(answer);
    }

    private void unite(int u, int v, int t) {
        u = get(u);
        v = get(v);
        if (u == v) return;
        int parent, child;
        if (size[u] > size[v]) {
            parent = u;
            child = v;
        } else {
            parent = v;
            child = u;
        }
        link[child] = this.parent[child] = parent;
        topologicalOrder[topOrderPtr++] = child;
        topologicalOrder[topOrderPtr] = parent;
        fees[parent] += (long) size[parent] * (time[parent] - t);
        fees[child] += (long) size[child] * (time[child] - t);
        fees[child] -= fees[parent];
        size[parent] += size[child];
        time[parent] = t;
    }

    private void init() {
        firstEdge = new int[citiesQty];
        Arrays.fill(firstEdge, -1);
        nextEdge = new int[roadsQty * 2];
        to = new int[roadsQty * 2];
        destroyTime = new int[roadsQty];
        topologicalOrder = new int[citiesQty];

        parent = new int[citiesQty];
        link = new int[citiesQty];
        size = new int[citiesQty];
        time = new int[citiesQty];
        for (int i = 0; i < citiesQty; ++i) {
            parent[i] = i;
            link[i] = i;
        }
        Arrays.fill(size, 1);
        Arrays.fill(time, days);
    }

    private int get(int v) {
        return link[v] == v ? v : (link[v] = get(link[v]));
    }

    private void addEdge(int i, int u, int v, int time) {
        int vi = i << 1;
        int ui = vi | 1;
        to[vi] = u;
        to[ui] = v;
        nextEdge[vi] = firstEdge[v];
        firstEdge[v] = vi;
        nextEdge[ui] = firstEdge[u];
        firstEdge[u] = ui;
        destroyTime[i] = time;
    }
}
