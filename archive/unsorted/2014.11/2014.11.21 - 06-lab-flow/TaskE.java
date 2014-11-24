package tasks;

import algoribrary.graph.Graph;
import algoribrary.graph.MaxFlow;
import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskE {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        Graph graph = Graph.createFlowGraph(vertices, edges);
        for (int e = 0; e < edges; ++e) {
            graph.addFlowEdge(in.nextInt() - 1, in.nextInt() - 1, in.nextInt());
        }
        int source = 0;
        int sink = vertices - 1;
        long maxFlow = MaxFlow.dinic(graph, source, sink);
        StringBuilder result = new StringBuilder();
        int ways = 0;
        int[] nextEdge = new int[vertices];
        for (int i = 0; i < vertices; ++i) {
            nextEdge[i] = graph.getFirstOutgoing(i);
        }
        while (nextEdge[source] != -1) {
            if (graph.getFlow(nextEdge[source]) > 0) {
                dfs(source, Long.MAX_VALUE)
            }
        }
    }
}
