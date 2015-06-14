package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskF1 {
    private static final int INT_INF = Integer.MAX_VALUE / 3;
    private static final long LONG_INF = Long.MAX_VALUE / 3;
    
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int rows = in.nextInt();
        int columns = in.nextInt();
        int males = in.nextInt();
        int females = in.nextInt();
        if (Math.abs(males - females) != 1) {
            out.println(-1);
            return;
        }
        char[][] table = new char[rows][];
        for (int i = 0; i < rows; ++i) {
            table[i] = in.next().toCharArray();
        }
        int counter = 0;
        int[][] id = new int[rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                if (table[i][j] == '.') {
                    id[i][j] = ++counter;
                }
            }
        }
        int source = 0;
        int sink = 1 + 2 * counter + 1 + males + females;
        MinCostMaxFlow flow = new MinCostMaxFlow(sink + 1);
        int[][] distance = new int[counter + 1][counter + 1];
        for (int i = 0; i <= counter; ++i) {
            Arrays.fill(distance[i], INT_INF);
            distance[i][i] = 0;
        }
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                if (table[i][j] == '.') {
                    if (i > 0 && table[i - 1][j] == '.') {
                        distance[id[i][j]][id[i - 1][j]] = distance[id[i - 1][j]][id[i][j]] = 1;
                    }
                    if (j > 0 && table[i][j - 1] == '.') {
                        distance[id[i][j]][id[i][j - 1]] = distance[id[i][j - 1]][id[i][j]] = 1;
                    }
                }
            }
        }
        for (int k = 0; k <= counter; ++k) {
            for (int i = 0; i <= counter; ++i) {
                for (int j = 0; j <= counter; ++j) {
                    distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
                }
            }
        }
        for (int i = 0; i < counter; ++i) {
            flow.addEdge(i + 1, i + 1 + counter, 0, 1);
        }
        for (int it = 0; it < 3; ++it) {
            for (int i = 0; i < new int[]{1, males, females}[it]; ++i) {
                int ID = 1 + 2 * counter + i;
                if (it > 0) ID++;
                if (it > 1) ID += males;
                boolean likeMale = it == 1 || (it == 0 && males < females);
                int r = in.nextInt() - 1;
                int c = in.nextInt() - 1;
                int velocity = in.nextInt();
                if (likeMale) {
                    flow.addEdge(source, ID, 0, 1);
                } else {
                    flow.addEdge(ID, sink, 0, 1);
                }
                for (int x = 0; x < rows; ++x) {
                    for (int y = 0; y < columns; ++y) {
                        if (distance[id[r][c]][id[x][y]] >= INT_INF) {
                            continue;
                        }
                        if (likeMale) {
                            flow.addEdge(ID, id[x][y], velocity * distance[id[r][c]][id[x][y]], 1);
                        } else {
                            flow.addEdge(id[x][y] + counter, ID, velocity * distance[id[r][c]][id[x][y]], 1);
                        }
                    }
                }
            }
        }
        Pair result = flow.calculate(source, sink);
        if (result.totalFlow != Math.max(males, females)) {
            out.println(-1);
        } else {
            out.println(flow.getMaxCost());
        }
    }

    static class Pair {
        int totalFlow;
        long totalCost;

        public Pair(int totalFlow, long totalCost) {
            this.totalFlow = totalFlow;
            this.totalCost = totalCost;
        }
    }

    static class Edge {
        int begin, end;
        long cost;
        int capacity;
        Edge reverse;

        public Edge(int begin, int end, long cost, int capacity) {
            this.begin = begin;
            this.end = end;
            this.cost = cost;
            this.capacity = capacity;
        }

        @Override
        public String toString() {
            return begin + "-(" + cost + "/" + capacity +")->" + end;
        }
    }

    static class MinCostMaxFlow {
        List<Edge>[] graph;
        Heap heap;
        long[] phi;
        long[] distance;
        Edge[] from;

        MinCostMaxFlow(int vertices) {
            graph = new List[vertices];
            for (int i = 0; i < vertices; ++i) {
                graph[i] = new ArrayList<>();
            }
            heap = new Heap(vertices, vertices - 1);
            phi = new long[vertices];
            distance = new long[vertices];
            from = new Edge[vertices];
        }

        public void addEdge(int begin, int end, long cost, int capacity) {
            Edge e1 = new Edge(begin, end, cost, capacity);
            Edge e2 = new Edge(end, begin, -cost, 0);
            e1.reverse = e2;
            e2.reverse = e1;
            graph[begin].add(e1);
            graph[end].add(e2);
        }

        public Pair calculate(int source, int sink) {
            int totalFlow = 0;
            long totalCost = 0;
            while (dijkstra(source, sink)) {
                System.err.println(distance[sink]);
                System.err.println(source + " " + sink);
                int pushedFlow = Integer.MAX_VALUE;
                long cost = 0;
                int current = sink;
                while (current != source) {
                    cost += from[current].cost;
                    pushedFlow = Math.min(pushedFlow, from[current].capacity);
                    current = from[current].begin;
                }
                totalFlow += pushedFlow;
                totalCost += pushedFlow * cost;
                current = sink;
                while (current != source) {
                    from[current].capacity -= pushedFlow;
                    from[current].reverse.capacity += pushedFlow;
                    current = from[current].begin;
                }
//                if (pushedFlow == 0) break;
                System.err.println(pushedFlow + " " + cost);
            }
            return new Pair(totalFlow, totalCost);
        }

        long getMaxCost() {
            System.err.println(Arrays.toString(graph));
            long maxCost = -1;
            for (List<Edge> list : graph)
                for (Edge edge : list) {
                        if (edge.capacity == 0) {
                            maxCost = Math.max(maxCost, edge.cost);
                        }
                    }
            return maxCost;
        }

        private boolean dijkstra(int source, int sink) {
            Arrays.fill(from, null);
            Arrays.fill(distance, LONG_INF);
            distance[source] = 0;
            heap.clear();
            heap.add(source, distance[source]);
            while (!heap.empty()) {
                int current = heap.extractMin();
                System.err.println("   " + current + " " + distance[current]);
                for (Edge edge : graph[current]) {
                    if (edge.capacity <= 0) continue;
                    int next = edge.end;
                    long newDistance = distance[current] - (phi[next] - phi[current]) + edge.cost;
                    if (newDistance < distance[next]) {
                        from[next] = edge;
                        distance[next] = newDistance;
                        heap.update(next, distance[next]);
                    }
                }
            }
            System.arraycopy(distance, 0, phi, 0, distance.length);
            return distance[sink] < LONG_INF;
        }
    }

    static class Heap {
        int[] key;
        long[] value;
        int[] position;
        int size;

        Heap(int size, int maxElement) {
            key = new int[size];
            value = new long[size];
            position = new int[maxElement + 1];
            clear();
        }

        void add(int key, long value) {
            this.key[size] = key;
            this.value[size] = value;
            position[key] = size;
            size++;
            siftUp(position[key]);
        }

        void update(int key, long value) {
            int idx = position[key];
            if (idx == -1) {
                add(key, value);
            } else {
                assert this.value[idx] >= value;
                this.value[idx] = value;
                siftUp(idx);
            }
        }

        int extractMin() {
            int min = key[0];
            size--;
            swap(0, size);
            position[min] = -1;
            siftDown(0);
            return min;
        }

        void clear() {
            Arrays.fill(position, -1);
            Arrays.fill(key, -1);
            Arrays.fill(value, -1);
            size = 0;
        }

        int size() {
            return size;
        }

        boolean empty() {
            return size == 0;
        }

        private void siftUp(int idx) {
            while (idx > 0) {
                int parent = (idx - 1) >>> 1;
                if (value[parent] <= value[idx]) {
                    break;
                }
                swap(idx, parent);
                idx = parent;
            }
        }

        private void siftDown(int idx) {
            while (idx < size) {
                int child = (idx << 1) + 1;
                if (child >= size) {
                    break;
                }
                if (child + 1 < size && value[child + 1] < value[child]) {
                    child = child + 1;
                }
                if (value[child] >= value[idx]) {
                    break;
                }
                swap(idx, child);
                idx = child;
            }
        }

        private void swap(int a, int b) {
            {
                long tmp = value[a];
                value[a] = value[b];
                value[b] = tmp;
            }
            {
                int tmp = key[a];
                key[a] = key[b];
                key[b] = tmp;
            }
            position[key[a]] = a;
            position[key[b]] = b;
        }
    }
}
