package tasks;

import algoribrary.collections.intervaltree.fenwicktree.LongFenwickTree;
import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskF {
    static class Edge {
        int to, weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int lostTime = in.nextInt();
        int vertices = in.nextInt();
        int lines = in.nextInt();
        List<Integer>[] codes = new List[vertices + 1];
        for (int i = 0; i <= vertices; ++i) codes[i] = new ArrayList<>();
        int nextVertex = 0;
        int start = in.nextInt();
        int finish = in.nextInt();
        int[][] numbers = new int[lines][];
        int[][] lengths = new int[lines][];
        for (int line = 0; line < lines; ++line) {
            int counter = in.nextInt();
            numbers[line] = new int[counter];
            lengths[line] = new int[counter];
            for (int i = 0; i < counter; ++i) {
                numbers[line][i] = in.nextInt();
                lengths[line][i] = in.nextInt();
                codes[numbers[line][i]].add(nextVertex);
                nextVertex++;
            }
        }
        long[][] graph = new long[nextVertex][nextVertex];
        for (long[] arr : graph) Arrays.fill(arr, Long.MAX_VALUE / 3);
        for (int v = 1; v <= vertices; ++v) {
            for (int i : codes[v]) for (int j : codes[v]) {
                if (i != j) {
                    graph[i][j] = lostTime;
                } else {
                    graph[i][j] = 0;
                }
            }
        }
        nextVertex = 0;
        for (int line = 0; line < lines; ++line) {
            nextVertex++;
            for (int i = 1; i < numbers[line].length; ++i) {
                graph[nextVertex - 1][nextVertex] =
                        graph[nextVertex][nextVertex - 1] =
                                lengths[line][i] - lengths[line][i - 1];
                nextVertex++;
            }
        }
        for (int k = 0; k < graph.length; ++k) {
            for (int i = 0; i < graph.length; ++i) {
                if (graph[i][k] < Long.MAX_VALUE / 3) {
                    for (int j = 0; j < graph.length; ++j) {
                        if (graph[k][j] < Long.MAX_VALUE / 3) {
                            graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                        }
                    }
                }
            }
        }
        long answer = Long.MAX_VALUE;
        for (int i : codes[start]) for (int j : codes[finish]) answer = Math.min(answer, graph[i][j]);
        out.println(answer);
    }
}
