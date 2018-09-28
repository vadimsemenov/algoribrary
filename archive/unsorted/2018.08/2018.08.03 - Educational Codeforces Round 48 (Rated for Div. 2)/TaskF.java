package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class TaskF {
    Edge[][] tree;
    List<Integer> path = new ArrayList<>();

    public void solve(int __, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        int count = in.nextInt();
        int[] degree = new int[vertices];
        Edge[] edges = new Edge[vertices - 1];
        for (int e = 0; e < vertices - 1; ++e) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            edges[e] = new Edge(u, v, in.nextInt());
            degree[u]++;
            degree[v]++;
        }
        tree = buildTree(vertices, degree, edges);
        int[] lengthes = in.nextIntArray(count);
        dfs(0, -1);
        long baseDistance = 0;
        for (int i = 1; i < path.size(); ++i) {
            baseDistance += distanceBetween(path.get(i - 1), path.get(i));
        }
        boolean uselessCycle = false;
        int degThree = 0;
        for (int v = 0; v < vertices; ++v) {
            if (degree[v] > 3) {
                uselessCycle = true;
            } else if (degree[v] == 3) {
                degThree++;
            }
        }
        for (int i = 1; i < path.size() - 1; ++i) {
            if (degree[path.get(i)] == 3) {
                degThree--;
            }
        }
        if (degThree != 0) {
            uselessCycle = true;
        }
        if (uselessCycle) {
            for (int i = 0; i < count; ++i) {
                out.println(baseDistance);
            }
            return;
        } // else all vertices have degree <= 3 (degree == 3 is possible only on path between 1 and n);
        long[] pre = new long[path.size()];
        long[] suf = new long[path.size()];
        boolean[] hasBranch = new boolean[path.size()];
        for (int i = 1; i < path.size(); ++i) {
            int j = path.size() - 1 - i;
            pre[i] = pre[i - 1] + distanceBetween(path.get(i), path.get(i - 1));
            suf[j] = suf[j + 1] + distanceBetween(path.get(j), path.get(j + 1));
        }
        for (int i = 0; i < path.size(); ++i) {
            long ad = dfs(path.get(i), i > 0 ? path.get(i - 1) : -1, i + 1 < path.size() ? path.get(i + 1) : -1);
            pre[i] += ad;
            suf[i] += ad;
            hasBranch[i] = ad > 0;
        }
        for (int i = 1; i < path.size(); ++i) {
            int j = path.size() - 1 - i;
            pre[i] = Math.max(pre[i], pre[i - 1]);
            suf[j] = Math.max(suf[j], suf[j + 1]);
        }
        long best = 0;
        for (int i = 1; i < path.size(); ++i) {
            if (hasBranch[i - 1] || hasBranch[i]) {
                best = Math.max(best, pre[i - 1] + suf[i]);
            } else {
                if (i > 1) {
                    best = Math.max(best, pre[i - 2] + suf[i]);
                }
                if (i + 1 < path.size()) {
                    best = Math.max(best, pre[i - 1] + suf[i + 1]);
                }
            }
        }
        for (int length : lengthes) {
            out.println(Math.min(baseDistance, best + length));
        }
    }

    private Edge[][] buildTree(int vertices, int[] degree, Edge[] edges) {
        Edge[][] tree = new Edge[vertices][];
        int[] qty = new int[vertices];
        for (int v = 0; v < vertices; ++v) {
            tree[v] = new Edge[degree[v]];
        }
        for (Edge edge : edges) {
            tree[edge.u][qty[edge.u]++] = edge;
            tree[edge.v][qty[edge.v]++] = edge;
        }
        return tree;
    }

    private long distanceBetween(int u, int v) {
        for (Edge edge : tree[u]) {
            if (edge.opposite(u) == v) {
                return edge.weight;
            }
        }
        throw new IllegalArgumentException("there's no edge between " + u + " " + v);
    }

    private long dfs(int vertex, int neightbour1, int neightbour2) {
        for (Edge edge : tree[vertex]) {
            int next = edge.opposite(vertex);
            if (next != neightbour1 && next != neightbour2) {
                return edge.weight + dfs(next, vertex, -1);
            }
        }
        return 0;
    }

    private boolean dfs(int vertex, int parent) {
        if (vertex == tree.length - 1) {
            path.add(vertex);
            return true;
        }
        for (Edge edge : tree[vertex]) {
            int next = edge.opposite(vertex);
            if (next != parent) {
                if (dfs(next, vertex)) {
                    path.add(vertex);
                    return true;
                }
            }
        }
        return false;
    }

    static class Edge {
        final int u, v;
        final int weight;

        Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        int opposite(int from) {
            return from ^ u ^ v;
        }
    }
}
