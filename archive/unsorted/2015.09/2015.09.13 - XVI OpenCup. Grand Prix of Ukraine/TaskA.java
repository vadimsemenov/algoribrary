package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TaskA {
    boolean[] visited;

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        List<Integer>[] graph = new List[vertices];
        List<Integer>[] revGraph = new List[vertices];
        for (int i = 0; i < vertices; ++i) {
            graph[i] = new ArrayList<>();
            revGraph[i] = new ArrayList<>();
        }
        for (int i = 0; i < edges; ++i) {
            int begin = in.nextInt() - 1;
            int end = in.nextInt() - 1;
            graph[begin].add(end);
            revGraph[end].add(begin);
        }
        long answer = 0;
        int[] queue = new int[vertices];
        int head, tail;
        int[] timer = new int[vertices];
        int currentTime = 1;
        for (int v = 0; v < vertices; ++v) {
            ++currentTime;
            head = tail = 0;
            queue[tail++] = v;
            timer[v] = currentTime;
            while (head < tail) {
                int current = queue[head++];
                for (int to : revGraph[current]) if (timer[to] < currentTime) {
                    timer[to] = currentTime;
                    queue[tail++] = to;
                }
            }
            head = 0;
            while (head < tail) {
                int current = queue[head++];
                for (int to : graph[current]) if (timer[to] < currentTime) {
                    timer[to] = currentTime;
                    queue[tail++] = to;
                }
            }
            answer += head;
        }
        out.println(answer);
    }
}
