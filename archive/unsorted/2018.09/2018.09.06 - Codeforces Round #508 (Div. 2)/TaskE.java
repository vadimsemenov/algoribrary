package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class TaskE {
    private static final int VERTICES = 4;
    private static final int EDGES = 6;
    private static final int[][] TYPE_BY_EDGE = new int[VERTICES][VERTICES];
    private static final Edge[] EDGE_BY_TYPE = new Edge[EDGES + VERTICES];

    static {
        prepare(TYPE_BY_EDGE, EDGE_BY_TYPE);
    }

    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        List<Edge>[] edges = new List[EDGES + VERTICES];
        int[] sum = new int[edges.length];
        Arrays.setAll(edges, i -> new ArrayList<>());
        for (int e = 0; e < count; ++e) {
            Edge edge = Edge.readEdge(in);
            int type = TYPE_BY_EDGE[edge.u][edge.v];
            edges[type].add(edge);
            sum[type] += edge.cost;
        }
        for (List<Edge> list : edges) {
            list.sort(Comparator.reverseOrder());
        }
        boolean[][] state = new boolean[1 << 12][4];
        for (int i = 0; i < 4; ++i) {
            state[0][i] = true;
        }
        for (int mask = 0; mask < state.length; ++mask) {
            for (int color = 0; color < VERTICES; ++color) {
                if (!state[mask][color]) continue;
                for (int type = 0; type < EDGES; ++type) {
                    Edge mock = EDGE_BY_TYPE[type];
                    if (!mock.contains(color)) continue;
                    int qty = (mask >> (2 * type)) & 0x3;
                    if (qty + 1 <= Math.min(3, edges[type].size())) {
                        int nextMask = mask ^ (qty << (2 * type)) ^ ((qty + 1) << (2 * type));
                        int nextColor = mock.next(color);
                        state[nextMask][nextColor] = true;
                    }
                }
            }
        }
        int answer = 0;
        boolean[] visited = new boolean[VERTICES];
        for (int mask = 0; mask < state.length; ++mask) {
            for (int color = 0; color < VERTICES; ++color) {
                if (!state[mask][color]) continue;
                Arrays.fill(visited, false);
                int current = sum[EDGES + color];
                visited[color] = true;
                for (int type = 0; type < EDGES; ++type) {
                    int qty = (mask >> (2 * type)) & 0x3;
                    if (qty == 0) continue;
                    current += sum[type];
                    if (qty % 2 != edges[type].size() % 2) {
                        current -= edges[type].get(edges[type].size() - 1).cost;
                    }
                    Edge mock = EDGE_BY_TYPE[type];
                    for (int v : new int[]{mock.u, mock.v}) {
                        if (!visited[v]) {
                            visited[v] = true;
                            current += sum[6 + v];
                        }
                    }
                }
                answer = Math.max(answer, current);
            }
        }
        out.println(answer);
    }

    private static void prepare(int[][] typeByEdge, Edge[] edgeByType) {
        int type = 0;
        for (int i = 0; i < VERTICES; ++i) {
            for (int j = 0; j < i; ++j) {
                edgeByType[type] = new Edge(i, j, 0);
                typeByEdge[i][j] = typeByEdge[j][i] = type;
                type++;
            }
        }
        for (int i = 0; i < VERTICES; ++i) {
            edgeByType[type] = new Edge(i, i, 0);
            typeByEdge[i][i] = type;
            type++;
        }
    }

    private static class Edge implements Comparable<Edge> {
        final int u, v;
        final int cost;

        static Edge readEdge(InputReader in) {
            int u = in.nextInt() - 1;
            int cost = in.nextInt();
            int v = in.nextInt() - 1;
            return new Edge(u, v, cost);
        }

        private Edge(int u, int v, int cost) {
            this.u = Math.max(u, v);
            this.v = Math.min(u, v);
            this.cost = cost;
        }

        boolean contains(int vertex) {
            return u == vertex || v == vertex;
        }

        int next(int vertex) {
            return vertex ^ u ^ v;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(cost, o.cost);
        }
    }
}
