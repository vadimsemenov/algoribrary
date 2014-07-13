package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bicycle {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        List<Integer>[] graph = new List[vertices];
        for (int i = 0; i < vertices; i++) graph[i] = new ArrayList<>();
        for (int i = 0; i < edges; i++) {
            int from = in.nextInt() - 1;
            int to = in.nextInt() - 1;
            graph[from].add(to);
        }
        for (List<Integer> list : graph) Collections.sort(list);
        toDegree = new int[vertices];
        available = new boolean[vertices];
        dfs(graph, 0, new boolean[vertices]);
        if (checkCycles(graph, 0, new int[vertices])) {
            out.println("inf");
            return;
        }
        boolean overflow = false;
        int[] queue = new int[vertices];
        int tail = 0;
        int head = 0;
        queue[tail++] = 0;
        int[] count = new int[vertices];
        count[0] = 1;
        while (head < tail) {
            int current = queue[head++];
            for (int to : graph[current]) if (available[to]) {
                count[to] += count[current];
                if (count[to] > 1_000_000_000) {
                    overflow = true;
                    count[to] -= 1_000_000_000;
                }
                toDegree[to]--;
                if (toDegree[to] == 0 && to != 1) queue[tail++] = to;
            }
        }
        if (overflow) {
            for (int i = 0; i < 9 - Integer.toString(count[1]).length(); i++)
                out.print("0");
        }
        out.println(count[1]);
    }

    private boolean[] available;
    private boolean dfs(List<Integer>[] graph, int vertex, boolean[] visited) {
        visited[vertex] = true;
        if (vertex == 1) {
            return available[vertex] = true;
        }
        for (int to : graph[vertex]) {
            available[vertex] |= visited[to] ? available[to] : dfs(graph, to, visited);
        }
        return available[vertex];
    }

    private int[] toDegree;
    private boolean checkCycles(List<Integer>[] graph, int vertex, int[] visited) {
        visited[vertex] = 1;
        if (vertex == 1) {
            for (int to : graph[vertex]) {
                if (visited[to] == 1) return true;
            }
            visited[vertex] = 2;
            return false;
        }
        for (int to : graph[vertex]) if (available[to]) {
            toDegree[to]++;
            if (visited[to] == 1) return true;
            if (visited[to] == 2) continue;
            if (checkCycles(graph, to, visited)) return true;
        }
        visited[vertex] = 2;
        return false;
    }
}
