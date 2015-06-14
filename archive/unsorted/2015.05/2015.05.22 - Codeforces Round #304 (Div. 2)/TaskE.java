package tasks;

import algoribrary.graph.Flow;
import algoribrary.graph.Graph;
import algoribrary.io.InputReader;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TaskE {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        Graph graph = Graph.createFlowGraph(2 * vertices + 2, 2 * (2 * vertices + edges));
        int source = 0;
        int sink = 2 * vertices + 1;
        long sumA = 0;
        long sumB = 0;
        int inf = -1;
        for (int i = 0; i < vertices; ++i) {
            int capacity = in.nextInt();
            graph.addFlowEdge(source, i + 1, capacity);
            sumA += capacity;
            inf = Math.max(inf, capacity);
        }
        for (int i = 0; i < vertices; ++i) {
            int capacity = in.nextInt();
            graph.addFlowEdge(vertices + 1 + i, sink, capacity);
            sumB += capacity;
            inf = Math.max(inf, capacity);
        }
        for (int v = 0; v < vertices; ++v) {
            graph.addFlowEdge(v + 1, v + vertices + 1, inf);
        }
        for (int e = 0; e < edges; ++e) {
            int u = in.nextInt();
            int v = in.nextInt();
            graph.addFlowEdge(u, vertices + v, inf);
            graph.addFlowEdge(v, vertices + u, inf);
        }
        if (sumA != sumB) {
            out.println("NO");
            return;
        }
        long totalFlow = Flow.dinic(graph, 0, 2 * vertices + 1);
        if (totalFlow != sumA) {
            out.println("NO");
            return;
        }
        out.println("YES");
        int[][] output = new int[vertices][vertices];
        for (int v = 1; v <= vertices; ++v) {
            for (int e : graph.getOutgoingEdges(v)) {
                if (graph.flow(e) > 0) {
                    output[v - 1][graph.end(e) - vertices - 1] = (int) graph.flow(e);
                }
            }
        }
        for (int i = 0; i < vertices; ++i) {
            for (int j = 0; j < vertices; ++j) {
                if (j > 0) out.print(' ');
                out.print(output[i][j]);
            }
            out.println();
        }
    }
}
