package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CorporateGifting {
    public static int MAX_COLOR = 0;

    private List<Integer>[] graph;
    private long[] minCost;
    private long[] secondMinCost;
    private int[] bestColor;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        long startTime = System.currentTimeMillis();
        int vertices = in.nextInt();
        init(vertices);
        assert 0 == in.nextInt();
        for (int i = 1; i < vertices; ++i) {
            int parent = in.nextInt() - 1;
            graph[parent].add(i);
        }

        dfs(0);
        long finishTime = System.currentTimeMillis();
        print(out, testNumber, minCost[0] + vertices, finishTime - startTime);
    }

    private void dfs(int vertex) {
        int[] diff = new int[graph[vertex].size() + 2];
        long totalCost = 0;
        for (int child : graph[vertex]) {
            dfs(child);
            totalCost += minCost[child];
            if (bestColor[child] < diff.length) {
                diff[bestColor[child]] += secondMinCost[child] - minCost[child];
            }
        }
        int c1 = -1;
        int c2 = -1;
        for (int c = 0; c < diff.length; ++c) {
            long cost = totalCost + diff[c] + c;
            if (c1 == -1 || cost < totalCost + diff[c1] + c1) {
                c2 = c1;
                c1 = c;
            } else if (c2 == -1 || cost < totalCost + diff[c2] + c2) {
                c2 = c;
            }
        }
        minCost[vertex] = totalCost + diff[c1] + c1;
        secondMinCost[vertex] = totalCost + diff[c2] + c2;
        bestColor[vertex] = c1;
        MAX_COLOR = Math.max(MAX_COLOR, bestColor[vertex] + 1);
    }

    private void print(PrintWriter out, int testNumber, long answer, long totalTime) {
               out.println("Case #" + testNumber + ": " + answer);
        System.err.println("Case #" + testNumber + ": " + answer + ", ready in " + totalTime + ".ms");
    }

    private void init(int vertices) {
        graph = new List[vertices];
        for (int i = 0; i < vertices; ++i) {
            graph[i] = new ArrayList<>();
        }
        minCost = new long[vertices];
        secondMinCost = new long[vertices];
        bestColor = new int[vertices];
    }
}
