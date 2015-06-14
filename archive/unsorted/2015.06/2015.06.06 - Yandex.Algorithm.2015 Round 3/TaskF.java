package tasks;

import algoribrary.graph.Edge;
import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class TaskF {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int vertices = in.nextInt();
        int edges = in.nextInt();
        int s = in.nextInt() - 1;
        int t = in.nextInt() - 1;
        int initial = in.nextInt();
        int maximum = in.nextInt();
        List<Edge>[] graph = new List[vertices];
        for (int i = 0; i < vertices; ++i) graph[i] = new ArrayList<>();
        Edge[] edgeList = new Edge[edges];
        long answer = Long.MAX_VALUE;
        for (int i = 0; i < edges; ++i) {
            Edge edge = new Edge(in.nextInt() - 1, in.nextInt() - 1, in.nextInt(), in.nextInt());
            if (edge.delta < 0) {
                out.println(0);
                return;
            }
            graph[edge.u].add(edge);
            graph[edge.v].add(edge);
            edgeList[i] = edge;
            answer = Math.min(answer, (long) edge.cost * (edge.delta + 1));
        }
        long[] distanceS = dijkstra(graph, s);
        long[] distanceT = dijkstra(graph, t);

        for (Edge edge : edgeList) {
            long sumDelta = edge.delta +
                    Math.min((distanceS[edge.u] + distanceT[edge.v]),
                            (distanceS[edge.v] + distanceT[edge.u]));
            long redundant = Math.max((initial + sumDelta) - maximum, 0);
            // current = redundant * edge.cost
            if ((answer + edge.cost - 1) / edge.cost >= redundant) {
                answer = Math.min(answer, redundant * edge.cost);
            }
        }
        out.println(answer);
    }

    private long[] dijkstra(List<Edge>[] graph, int source) {
        long[] distance = new long[graph.length];
        Arrays.fill(distance, Long.MAX_VALUE / 2);
        distance[source] = 0;
        PriorityQueue<Node> queue = new PriorityQueue<>(graph.length);
        queue.add(new Node(source, 0));
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.distance != distance[current.vertex]) continue;
            for (Edge edge : graph[current.vertex]) {
                int next = edge.next(current.vertex);
                long newDistance = current.distance + edge.delta;
                if (distance[next] > newDistance) {
                    distance[next] = newDistance;
                    queue.add(new Node(next, newDistance));
                }
            }
        }
        return distance;
    }

    static class Edge {
        final int u, v, delta, cost;

        Edge(int u, int v, int delta, int cost) {
            this.u = u;
            this.v = v;
            this.delta = delta;
            this.cost = cost;
        }

        int next(int vertex) {
            return vertex ^ v ^ u;
        }
    }

    static class Node implements Comparable<Node> {
        final int vertex;
        final long distance;

        public Node(int vertex, long distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node other) {
            return Long.compare(this.distance, other.distance);
        }
    }
}
