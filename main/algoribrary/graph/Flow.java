package algoribrary.graph;

import java.util.Arrays;

/**
 * Created by vadim on 21/11/14.
 */
public class Flow {
    private Graph graph;
    private int source;
    private int sink;

    private int[] queue;
    private int[] nextEdge;
    private int[] level;

    private Flow(Graph graph, int source, int sink) {
        this.graph = graph;
        this.source = source;
        this.sink = sink;
        queue = new int[graph.verticesCounter];
        nextEdge = new int[graph.verticesCounter];
        level = new int[graph.verticesCounter];
    }

    public static long dinic(Graph graph, int source, int sink) {
        return new Flow(graph, source, sink).dinic();
    }

    public static boolean[] getMinCut(Graph graph, int source, int sink) {
        Flow flow = new Flow(graph, source, sink);
        flow.dinic();
        boolean[] cut = new boolean[graph.verticesCounter];
        for (int i = 0; i < graph.verticesCounter; ++i) {
            cut[i] = flow.level[i] != -1;
        }
        return cut;
    }

    private long dinic() {
        long totalPushed = 0;
        while (bfs()) {
            for (int v = 0; v < graph.verticesCounter; ++v) {
                nextEdge[v] = graph.firstOutgoing[v];
            }
            while (true) {
                long pushed = dfs(source, Long.MAX_VALUE);
                if (pushed == 0) {
                    break;
                }
                totalPushed += pushed;
            }
        }
        return totalPushed;
    }

    private long dfs(int vertex, long minCapacity) {
        if (vertex == sink) {
            return minCapacity;
        }
        if (level[vertex] >= level[sink]) {
            return 0;
        }
        int currentEdge = nextEdge[vertex];
        long pushed = 0;
        while (currentEdge != -1) {
            int next = graph.end[currentEdge];
            if (level[next] == level[vertex] + 1 && graph.capacities[currentEdge] > graph.flows[currentEdge]) {
                pushed = dfs(next, Math.min(minCapacity, graph.capacities[currentEdge] - graph.flows[currentEdge]));
                if (pushed != 0) {
                    graph.pushFlow(currentEdge, pushed);
                    break;
                }
            }
            currentEdge = graph.nextOutgoing[currentEdge];
        }
        nextEdge[vertex] = currentEdge;
        return pushed;
    }

    private boolean bfs() {
        Arrays.fill(level, -1);
        level[source] = 0;
        int head = 0;
        int tail = 0;
        queue[tail++] = source;
        while (head < tail) {
            int current = queue[head++];
            int currentEdge = graph.firstOutgoing[current];
            while (currentEdge != -1) {
                int next = graph.end[currentEdge];
                if (level[next] == -1 && graph.capacities[currentEdge] > graph.flows[currentEdge]) {
                    queue[tail++] = next;
                    level[next] = level[current] + 1;
                }
                currentEdge = graph.nextOutgoing[currentEdge];
            }
        }
        return level[sink] != -1;
    }
}
