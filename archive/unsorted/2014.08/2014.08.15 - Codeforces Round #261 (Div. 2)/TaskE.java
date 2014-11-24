package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskE {
    static class Edge implements Comparable<Edge> {
        int from, to, weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return -Integer.compare(this.weight, other.weight);
        }
    }

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        Edge[] all = new Edge[edges + 1];
        for (int i = 0; i < edges; ++i) {
            all[i] = new Edge(in.nextInt(), in.nextInt(), in.nextInt());
        }
        all[edges] = new Edge(0, 0, -1);
        Arrays.sort(all);
        int[] max = new int[vertices + 1];
        int[][] toUpdate = new int[edges + 1][2];
        int size = 0;
        int previousWeight = 0;
        for (Edge edge : all) {
            if (edge.weight != previousWeight) {
                while (size > 0) {
                    size--;
                    max[toUpdate[size][0]] = Math.max(max[toUpdate[size][0]], toUpdate[size][1]);
                }
                previousWeight = edge.weight;
            }
            toUpdate[size][0] = edge.from;
            toUpdate[size][1] = max[edge.to] + 1;
            size++;
        }
        int answer = 0;
        for (int i : max) answer = Math.max(answer, i);
        out.println(answer);
    }
}
