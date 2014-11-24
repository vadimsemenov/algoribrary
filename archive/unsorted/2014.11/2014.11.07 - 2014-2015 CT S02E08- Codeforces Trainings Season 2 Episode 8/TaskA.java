package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskA {
    private static final class Edge {
        int from, to;
        int capacity, flow;
        Edge reversed;

        public Edge(int from, int to, int capacity, int flow) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = flow;
        }
    }

    private int vertices;
    private int haveTime;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        vertices = in.nextInt();
        int source = in.nextInt() - 1;
        int people = in.nextInt();
        haveTime = in.nextInt() + 1;
        Flow flow = new Flow(haveTime * vertices + 1);
        int sink = haveTime * vertices;
        for (int t = 0; t + 1 < haveTime; ++t) {
            flow.addEdge(hash(source, t), hash(source, t + 1), Integer.MAX_VALUE / 10);
        }
        int medicalFacilitiesCounter = in.nextInt();
        for (int i = 0; i < medicalFacilitiesCounter; ++i) {
            int medical = in.nextInt() - 1;
            for (int t = 0; t < haveTime; ++t) {
                flow.addEdge(hash(medical, t), sink, Integer.MAX_VALUE / 10);
            }
        }
        int edges = in.nextInt();
        for (int e = 0; e < edges; ++e) {
            int from = in.nextInt() - 1;
            int to = in.nextInt() - 1;
            int capacity = in.nextInt();
            int length = in.nextInt();
            for (int t = 0; t + length < haveTime; ++t) {
                flow.addEdge(hash(from, t), hash(to, t + length), capacity);
            }
        }
        out.println(Math.min(people, flow.dinic(hash(source, 0), sink)));
    }

    private int hash(int vertex, int time) {
        return vertex * haveTime + time;
    }

    static final class Flow {
        private List<Edge>[] graph;
        private int[] distance;
        private int[] queue;
        private int[] nextEdge;

        public Flow(int vertices) {
            graph = new List[vertices];
            for (int i = 0; i < vertices; ++i) {
                graph[i] = new ArrayList<>();
            }
            distance = new int[vertices];
            queue = new int[vertices];
            nextEdge = new int[vertices];
        }

        public void addEdge(int from, int to, int capacity) {
            Edge e1 = new Edge(from, to, capacity, 0);
            Edge e2 = new Edge(to, from, 0, 0);
            e1.reversed = e2;
            e2.reversed = e1;
            graph[from].add(e1);
            graph[to].add(e2);
        }

        public int dinic(int source, int sink) {
            int flow = 0;
            while (bfs(source, sink)) {
                Arrays.fill(nextEdge, 0);
                while (true) {
                    int pushed = dfs(source, sink, Integer.MAX_VALUE);
                    if (pushed == 0) break;
                    flow += pushed;
                }
            }
            return flow;
        }

        private int dfs(int vertex, int sink, int minCapacity) {
            if (vertex == sink) {
                return minCapacity;
            }
            for (; nextEdge[vertex] < graph[vertex].size(); ++nextEdge[vertex]) {
                Edge edge = graph[vertex].get(nextEdge[vertex]);
                if (distance[vertex] + 1 != distance[edge.to] || edge.capacity == edge.flow) continue;
                int pushed = dfs(edge.to, sink, Math.min(minCapacity, edge.capacity - edge.flow));
                if (pushed == 0) continue;
                edge.flow += pushed;
                edge.reversed.flow -= pushed;
                return pushed;
            }
            return 0;
        }

        private boolean bfs(int source, int sink) {
            Arrays.fill(distance, -1);
            distance[source] = 0;
            int head = 0;
            int tail = 0;
            queue[tail] = source;
            tail++;
            while (head < tail) {
                int current = queue[head];
                head++;
                for (Edge edge : graph[current]) {
                    if (distance[edge.to] == -1 && edge.capacity > edge.flow) {
                        distance[edge.to] = distance[current] + 1;
                        queue[tail] = edge.to;
                        tail++;
                    }
                }
            }
            return distance[sink] != -1;
        }
    }
}
