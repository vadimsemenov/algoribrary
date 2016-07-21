package tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Balance {
    public String canTransform(String[] initial, String[] target) {
        List<List<Integer>> _initial = normalize(initial);
        List<List<Integer>> _target = normalize(target);
        return equals(_initial, _target) ? "Possible" : "Impossible";
    }

    private List<List<Integer>> normalize(String[] table) {
        final int n = table.length;
        final int m = table[0].length();
        int[][] queue = new int[n * m][2];
        DSU dsu = new DSU(1 + n * m);
        final int _FAKE = n * n;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if ((i == 0 || i == n - 1 || j == 0 || j == m - 1) && table[i].charAt(j) == '#') {
                    dsu.unite(i * m + j, _FAKE);
                }
                if (i < n - 1 && table[i].charAt(j) == table[i + 1].charAt(j)) {
                    dsu.unite(i * m + j, (i + 1) * m + j);
                }
                if (j < m - 1 && table[i].charAt(j) == table[i].charAt(j + 1)) {
                    dsu.unite(i * m + j, i * m + j + 1);
                }
            }
        }
        final int FAKE = dsu.get(_FAKE);
        int[] map = new int[n * m + 1];
        Arrays.fill(map, -1);
        List<List<Integer>> graph = new ArrayList<>();
        map[FAKE] = graph.size();
        graph.add(new ArrayList<>());
        int head = 0;
        int tail = 0;
        boolean[][] visited = new boolean[n][n];
        for (int i = 0; i < n; ++i) {
            for (int it = 0, j = 0; it < 2; ++it, j = m - 1) {
                if (dsu.get(i * m + j) == FAKE && !visited[i][j]) {
                    queue[tail][0] = i;
                    queue[tail][1] = j;
                    ++tail;
                    visited[i][j] = true;
                }
                if (dsu.get(j * m + i) == FAKE && !visited[j][i]) {
                    queue[tail][0] = j;
                    queue[tail][1] = i;
                    ++tail;
                    visited[j][i] = true;
                }
            }
        }

        if (tail == 0) {
            map[dsu.get(0 * m + 0)] = graph.size();
            graph.add(new ArrayList<>());
            graph.get(map[FAKE]).add(map[dsu.get(0 * m + 0)]);
            queue[tail][0] = 0;
            queue[tail][1] = 1;
            ++tail;
            visited[0][0] = true;
        }
        final int[] dx = new int[]{-1, 0, 1, 0};
        final int[] dy = new int[]{0, -1, 0, 1};
        while (head < tail) {
            int x = queue[head][0];
            int y = queue[head][1];
            ++head;
            final int my = map[dsu.get(x * m + y)];
            for (int d = 0; d < 4; ++d) {
                int xx = x + dx[d];
                int yy = y + dy[d];
                if (0 <= xx && xx < n && 0 <= yy && yy < m) {
                    int vertex = dsu.get(xx * m + yy);
                    if (map[vertex] == -1) {
                        map[vertex] = graph.size();
                        graph.add(new ArrayList<>());
                        graph.get(my).add(map[vertex]);
                    }
                    if (!visited[xx][yy]) {
                        visited[xx][yy] = true;
                        queue[tail][0] = xx;
                        queue[tail][1] = yy;
                        ++tail;
                    }
                }
            }
        }
        return graph;
    }

    private boolean equals(List<List<Integer>> initial, List<List<Integer>> target) {
        long[] initialHash = normalize(initial);
        long[] targetHash = normalize(target);
        return equals(0, initialHash, initial, 0, targetHash, target);
    }

    private boolean equals(int u, long[] initialHash, List<List<Integer>> initial,
                           int v, long[] targetHash, List<List<Integer>> target) {
        if (initial.get(u).size() != target.get(v).size() || initialHash[u] != targetHash[v]) return false;
        for (int i = 0; i < initial.get(u).size(); ++i) {
            if (!equals(initial.get(u).get(i), initialHash, initial, target.get(v).get(i), targetHash, target)) {
                return false;
            }
        }
        return true;
    }

    private final Random random = new Random();
    private long[] normalize(List<List<Integer>> graph) {
        final long[] order = new long[graph.size()];
        for (int i = graph.size() - 1; i >= 0; --i) {
            random.setSeed(0x7777777);
            order[i] = random.nextLong();
            Collections.sort(graph.get(i), (first, second) -> Long.compare(order[first], order[second]));
            for (int child : graph.get(i)) {
                order[i] = order[i] + random.nextLong() * order[child];
            }
        }
        return order;
    }

    static class DSU {
        int[] dad;

        DSU(int capacity) {
            dad = new int[capacity];
            clear();
        }

        void clear() {
            for (int i = 0; i < dad.length; ++i) {
                dad[i] = i;
            }
        }

        int get(int v) {
            return v == dad[v] ? v : (dad[v] = get(dad[v]));
        }

        void unite(int u, int v) {
            dad[get(u)] = get(v);
        }
    }
}
