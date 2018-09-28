package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TaskE {
    public void solve(int __, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        int threshold = in.nextInt();
        int[] u = new int[edges];
        int[] v = new int[edges];
        int[] degree = new int[vertices];
        for (int edge = 0; edge < edges; ++edge) {
            u[edge] = in.nextInt() - 1;
            v[edge] = in.nextInt() - 1;
            degree[u[edge]]++;
            degree[v[edge]]++;
        }
        int[][] graph = new int[vertices][];
        Arrays.setAll(graph, vertex -> new int[degree[vertex]]);
        Arrays.fill(degree, 0);
        for (int edge = 0; edge < edges; ++edge) {
            graph[u[edge]][degree[u[edge]]++] = edge;
            graph[v[edge]][degree[v[edge]]++] = edge;
        }
        int[] queue = new int[vertices];
        int head = 0;
        int tail = 0;
        for (int vertex = 0; vertex < vertices; ++vertex) {
            if (degree[vertex] < threshold) {
                queue[tail++] = vertex;
            }
        }
        int[] answer = new int[edges];
        boolean[] removed = new boolean[edges];
        for (int day = edges; day --> 0; ) {
            while (head < tail) {
                int vertex = queue[head++];
                for (int edge : graph[vertex]) {
                    if (removed[edge]) continue;
                    removed[edge] = true;
                    int next = vertex ^ u[edge] ^ v[edge];
                    if (degree[next] == threshold) {
                        queue[tail++] = next;
                    }
                    degree[next]--;
                }
            }
            answer[day] = vertices - tail;
            if (!removed[day]) {
                removed[day] = true;
                if (degree[v[day]] == threshold) {
                    queue[tail++] = v[day];
                }
                if (degree[u[day]] == threshold) {
                    queue[tail++] = u[day];
                }
                degree[u[day]]--;
                degree[v[day]]--;
            }
        }
        for (int ans : answer) {
            out.println(ans);
        }
    }
}
