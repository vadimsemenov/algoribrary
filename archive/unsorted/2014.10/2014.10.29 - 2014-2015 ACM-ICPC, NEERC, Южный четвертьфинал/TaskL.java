package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskL {
    private int[] begin, end;
    private boolean[] useful;
    private List<Integer>[] graph;
    private List<Integer>[] reversedGraph;
    private boolean[] used;
    private int[] timeIn, timeOut;
    private int currentTime;
    private int[][] parents;
    private int[] level;
    private int[] minReachableLevel;
    private boolean[] reachable;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        if (!in.hasNext()) {
            throw new UnknownError();
        }


        int vertices = in.nextInt();
        int edges = in.nextInt();
        graph = new List[vertices];
        reversedGraph = new List[vertices];
        for (int i = 0; i < vertices; ++i) {
            graph[i] = new ArrayList<>();
            reversedGraph[i] = new ArrayList<>();
        }
        begin = new int[edges];
        end = new int[edges];
        for (int e = 0; e < edges; ++e) {
            begin[e] = in.nextInt() - 1;
            end[e] = in.nextInt() - 1;
            graph[begin[e]].add(e);
            reversedGraph[end[e]].add(e);
        }

        // obnooolyai!
        currentTime = 0;
        timeIn = new int[vertices];
        timeOut = new int[vertices];
        int log = 0;
        while ((1 << log) <= vertices) ++log;
        parents = new int[vertices][log];
        for (int[] p : parents) Arrays.fill(p, -1);
        level = new int[vertices];
        Arrays.fill(level, Integer.MAX_VALUE);
        reachable = new boolean[vertices];
        dfs(0, -1, 0);
        minReachableLevel = new int[vertices];
        Arrays.fill(minReachableLevel, Integer.MAX_VALUE);
        fillMRL(0);
        used = new boolean[vertices];
        useful = new boolean[edges];
        go(0);

        System.err.println(Arrays.toString(level));
        System.err.println(Arrays.toString(minReachableLevel));
        int cnt = 0;
        for (int e = 0; e < edges; ++e) if (useful[e]) cnt++;
        out.println(cnt);
        for (int e = 0; e < edges; ++e) if (useful[e]) {
            out.print(e + 1);
            out.print(' ');
        }
        out.println();
    }

    private void go(int vertex) {
        used[vertex] = true;
        for (int idx : graph[vertex]) {
            int lca = lca(vertex, end[idx]);
            if (minReachableLevel[vertex] < level[end[idx]] ||
                    (lca != -1 && lca != end[idx])) {
                useful[idx] = true;
            }
            if (!used[end[idx]]) {
                go(end[idx]);
            }
        }
    }

    private void fillMRL(int vertex) {
        minReachableLevel[vertex] = level[vertex];
        for (int idx : reversedGraph[vertex]) {
            int lca = lca(vertex, begin[idx]);
            if (lca != -1) {
                minReachableLevel[vertex] = Math.min(minReachableLevel[vertex], level[lca]);
            }
        }
        for (int idx : graph[vertex]) {
            if (minReachableLevel[end[idx]] != Integer.MAX_VALUE) continue;
            fillMRL(end[idx]);
        }
    }

    private int lca(int v, int u) {
        if (!reachable[v] || !reachable[u]) {
            return -1;
        }
        if (level[v] > level[u]) {
            int tmp = v; v = u; u = tmp;
        }
        if (timeIn[v] <= timeIn[u] && timeOut[u] <= timeOut[v]) {
            return v;
        }
        int diff = level[u] - level[v];
        int log = parents[u].length;
        for (int i = 0; i < log; ++i) {
            if (((diff >>> i) & 1) == 1) {
                u = parents[u][i];
            }
        }
        for (int i = log - 1; i >= 0; --i) {
            if (parents[v][i] != parents[u][i]) {
                v = parents[v][i];
                u = parents[u][i];
            }
        }
        return parents[v][0];
    }

    private void dfs(int vertex, int parent, int currentLevel) {
        reachable[vertex] = true;
        timeIn[vertex] = currentTime++;
        level[vertex] = currentLevel;
        parents[vertex][0] = parent;
        for (int i = 1; i < parents[vertex].length; ++i) {
            if (parents[vertex][i - 1] == -1) break;
            parents[vertex][i] = parents[parents[vertex][i - 1]][i - 1];
        }
        for (int idx : graph[vertex]) if (!reachable[end[idx]]) {
            dfs(end[idx], vertex, currentLevel + 1);
        }
        timeOut[vertex] = currentTime++;
    }
}
