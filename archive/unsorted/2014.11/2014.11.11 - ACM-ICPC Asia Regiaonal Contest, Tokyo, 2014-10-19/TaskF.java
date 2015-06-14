package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TaskF {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        List<Integer>[] graph = new List[vertices];
        for (int i = 0; i < vertices; ++i) {
            graph[i] = new ArrayList<>();
        }
        int edges = in.nextInt();
        int[] from = new int[edges];
        int[] to = new int[edges];
        boolean[] good = new boolean[edges];
        final int[] weight = new int[edges];
        for (int e = 0; e < edges; ++e) {
            from[e] = in.nextInt() - 1;
            to[e] = in.nextInt() - 1;
            weight[e] = in.nextInt();
            graph[from[e]].add(e);
            graph[to[e]].add(e);
        }
        Integer[] order = new Integer[edges];
        for (int i = 0; i < edges; ++i) {
            order[i] = i;
        }
        Arrays.sort(order, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(weight[o1], weight[o2]);
            }
        });
        DSU dsu = new DSU(vertices);
        int[] cnt = new int[vertices * vertices];
        int counter = 0;
        int sum = 0;
        CleaningArray degree = new CleaningArray(vertices);
        CleaningArray outcomming = new CleaningArray(vertices);
        int[] queue = new int[vertices];
        for (int i = 0; i < order.length; ++i) {
            int up = i;
            while (up < order.length && weight[order[up]] == weight[order[i]]) {
                up++;
            }
            for (int j = i; j < up; ++j) {
                int e = order[j];
                int pv = dsu.get(from[e]);
                int pu = dsu.get(to[e]);
                if (pv != pu) {
                    cnt[Math.min(pv, pu) * vertices + Math.max(pv, pu)]++;
                    good[e] = true;
                }
            }
            degree.clear();
            outcomming.clear();
            for (int j = i; j < up; ++j) {
                int e = order[j];
                int pv = dsu.get(from[e]);
                int pu = dsu.get(to[e]);
                if (good[e] && cnt[Math.min(pv, pu) * vertices + Math.max(pv, pu)] == 1) {
                    degree.set(pv, degree.get(pv) + 1);
                    degree.set(pu, degree.get(pu) + 1);
                    outcomming.set(pv, outcomming.get(pv) ^ e);
                    outcomming.set(pu, outcomming.get(pu) ^ e);
                }
            }
            int head = 0;
            int tail = 0;
            for (int j = i; j < up; ++j) {
                int e = order[j];
                int pv = dsu.get(from[e]);
                int pu = dsu.get(to[e]);
                if (good[e] && cnt[Math.min(pv, pu) * vertices + Math.max(pv, pu)] == 1) {
                    if (degree.get(pv) == 1) {
                        queue[tail++] = pv;
                    }
                    if (degree.get(pu) == 1) {
                        queue[tail++] = pu;
                    }
                }
            }

            while (head < tail) {
                int cur = queue[head++];
                if (degree.get(cur) > 1) throw new AssertionError("degree[cur] > 1");
                if (degree.get(cur) != 1) continue;
                int edgeId = outcomming.get(cur);
                counter++;
                sum += weight[edgeId];
                int next = cur ^ from[edgeId] ^ to[edgeId];
                degree.set(next, degree.get(next) - 1);
                outcomming.set(next, outcomming.get(next) ^ edgeId);
                if (degree.get(next) == 1) {
                    queue[tail++] = next;
                }
            }

            for (int j = i; j < up; ++j) {
                int e = order[j];
                if (dsu.get(from[e]) != dsu.get(to[e])) {
                    dsu.union(from[e], to[e]);
                }
            }
        }
        if (dsu.size[0] != vertices) throw new AssertionError("connected graph?!");
/*
        List<Integer>[] newGraph = new List[vertices];
        for (int i = 0; i < vertices; ++i) {
            newGraph[i] = new ArrayList<>();
        }
        for (int e = 0; e < next; ++e) if (good[e]) {
            newGraph[from[e]].add(e);
            newGraph[to[e]].add(e);
        }

        int[] timeIn = new int[vertices];
        int[] minAvailable = new int[vertices];
        boolean[] isBridge = new boolean[next];
        currentTime = 1;
        dfs(0, -1, newGraph, from, to, timeIn, minAvailable, isBridge);
        int cnt = 0;
        int sum = 0;
        for (int e = 0; e < next; ++e) if (isBridge[e]) {
            cnt++;
            sum += weight[e];
        }
        System.err.println(Arrays.toString(timeIn));
        System.err.println(Arrays.toString(minAvailable));
        System.err.println(Arrays.toString(isBridge));
        System.err.println(Arrays.toString(good));
        out.println(cnt + " " + sum);
*/
        out.println(counter + " " + sum);
    }

    private static final class CleaningArray {
        int[] array, time;
        int currentTime = 0;

        CleaningArray(int size) {
            array = new int[size];
            time = new int[size];
        }

        public void set(int idx, int val) {
            array[idx] = val;
            time[idx] = currentTime;
        }

        public int get(int idx) {
            return time[idx] == currentTime ? array[idx] : 0;
        }

        public void clear() {
            currentTime++;
        }
    }

    private int currentTime;
    private void dfs(int vertex, int inId, List<Integer>[] graph, int[] from, int[] to, int[] timeIn, int[] minAvailable, boolean[] isBridge) {
        minAvailable[vertex] = timeIn[vertex] = currentTime++;
        for (int e : graph[vertex]) if (inId != e) {
            int next = vertex ^ from[e] ^ to[e];
            if (timeIn[next] != 0) {
                minAvailable[vertex] = Math.min(minAvailable[vertex], timeIn[next]);
            } else {
                dfs(next, e, graph, from, to, timeIn, minAvailable, isBridge);
                minAvailable[vertex] = Math.min(minAvailable[vertex], minAvailable[next]);
                if (minAvailable[next] > timeIn[vertex]) {
                    isBridge[e] = true;
                }
            }
        }
    }

    static final class DSU {
        int[] parent, size;

        DSU(int n) {
            parent = new int[n];
            for (int i = 0; i < n; ++i) {
                parent[i] = i;
            }
            size = new int[n];
            Arrays.fill(size, 1);
        }

        public int get(int v) {
            return v == parent[v] ? v : (parent[v] = get(parent[v]));
        }

        public void union(int v, int u) {
            v = get(v);
            u = get(u);
            if (v == u) return;
            if (size[v] < size[u]) {
                int tmp = v; v = u; u = tmp;
            }
            parent[u] = v;
            size[v] += size[u];
        }
    }
}
