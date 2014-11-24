package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestDijkstra {
    private static class Pair<T1, T2> {
        T1 first;
        T2 second;

        Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }
    }

    private PrintWriter out;
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        this.out = out;
        final int vertices = 30;
        List<Pair<Integer, BigInteger>>[] graph = generate(vertices);
        long start = System.currentTimeMillis();
        dijkstra(graph, 0);
        long finish = System.currentTimeMillis();
        out.println("dijkstra on " + vertices + " vertices ready in " + (finish - start) + ".ms");
    }

    private void dijkstra(List<Pair<Integer, BigInteger>>[] graph, int source) {
        out.println("dijkstra starts");
        List<Integer> queue = new ArrayList<>();
        int head = 0;
        queue.add(source);
        int tot = 0;  // iterations counter
        BigInteger[] distanceTo = new BigInteger[graph.length];
        BigInteger INF = BigInteger.ONE.shiftLeft(maxPow);
        for (int i = 0; i < graph.length; ++i) {
            distanceTo[i] = INF;
        }
        distanceTo[source] = BigInteger.ZERO;
        int frameBegin = 0;
        int frameEnd = 1;
        int maxFrameSize = frameEnd - frameBegin;
        while (head < queue.size()) {
            int current = queue.get(head);
            head++;

            for (Pair<Integer, BigInteger> p : graph[current]) {
                tot++;
                int to = p.first;
                BigInteger length = p.second;
                BigInteger newDistance = distanceTo[current].add(length);
                if (newDistance.compareTo(distanceTo[to]) < 0) {
                    distanceTo[to] = newDistance;
                    queue.add(to);
                }
            }

            if (head == frameEnd) {
                frameBegin = head;
                frameEnd = queue.size();
                maxFrameSize = Math.max(maxFrameSize, frameEnd - frameBegin);
            }
        }
        out.println("dijkstra ends with maxFrameSize = " + maxFrameSize + " and " + tot + " iterations");
    }

    private int maxPow;
    private List<Pair<Integer, BigInteger>>[] generate(int vertices) {
        out.println("generating graph...");
        List<Pair<Integer, BigInteger>>[] graph = new List[vertices];
        for (int i = 0; i < vertices; ++i) {
            graph[i] = new ArrayList<>();
        }
        for (int i = vertices - 1; i >= 0; --i) {
            for (int j = i + 1; j < vertices; ++j) {
                graph[i].add(new Pair(j, BigInteger.ONE.shiftLeft(maxPow++)));
            }
            Collections.reverse(graph[i]);
        }

// output
        /*
        long[][] distance = new long[vertices][vertices];
        int maxLen = -1;
        for (int i = 0; i < vertices; ++i) {
            for (Pair<Integer, BigInteger> edge : graph[i]) {
                distance[i][edge.first] = edge.second.longValue();
                maxLen = Math.max(maxLen, edge.second.toString().length() + 1);
            }
        }
        for (int i = 0; i < vertices; ++i) {
            for (int j = 0; j < vertices; ++j) {
                out.printf("%" + maxLen + "d", distance[i][j]);
            }
            out.println();
        }
        */

        return graph;
    }
}
