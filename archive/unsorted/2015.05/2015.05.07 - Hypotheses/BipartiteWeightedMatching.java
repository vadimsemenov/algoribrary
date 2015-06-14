package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BipartiteWeightedMatching {
    private static final Random RANDOM = new Random(2539);
    
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int vertices = 5;
        long mask = 9900000;
        for (int iteration = 0; ; ++iteration) {
            if (iteration % 1000 == 0) System.err.println(iteration + " graphs tested");
            BipartiteGraph graph = new BipartiteGraph(vertices, vertices, mask++);
            if (mask >= (1L << (vertices * vertices))) break;
            int[] order = new int[vertices];
            for (int i = 0; i < order.length; ++i) {
                order[i] = i;
            }
            int[][] leftMatching = graph.findMatching(order, true);
            boolean[] leftMarked = new boolean[vertices];
            for (int i = 0; i < vertices; ++i) {
                leftMarked[i] = leftMatching[0][i] != -1;
            }
            do {
                int[][] matching = graph.findMatching(order, false);
                boolean[] rightMarked = new boolean[vertices];
                for (int i = 0; i < vertices; ++i) {
                    rightMarked[i] = matching[1][i] != -1;
                }
                if (!graph.existsPerfectMatching(leftMarked, rightMarked)) {
                    out.println("Found!");
                    out.println(Arrays.toString(leftMarked));
                    out.println(Arrays.toString(rightMarked));
                    out.println(vertices);
                    out.println(Arrays.toString(order));
                    for (int i = 0; i < vertices; ++i) {
                        out.println(graph.leftAdjacencyList[i]);
                    }
                    return;
                }
            } while (nextPermutation(order));
        }
    }

    private static boolean nextPermutation(int[] permutation) {
        int idx = permutation.length - 2;
        while (idx >= 0 && permutation[idx] >= permutation[idx + 1]) {
            --idx;
        }
        if (idx < 0) return false;
        int toSwap = idx + 1;
        for (int i = idx + 2; i < permutation.length; ++i) {
            if (permutation[i] > permutation[idx] && permutation[i] < permutation[toSwap]) {
                toSwap = i;
            }
        }
        {
            int tmp = permutation[idx];
            permutation[idx] = permutation[toSwap];
            permutation[toSwap] = tmp;
        }
        Arrays.sort(permutation, idx + 1, permutation.length);
        return true;
    }

    static class BipartiteGraph {
        public static final double THRESHOLD = 0.7;

        private final List<Integer>[] leftAdjacencyList;
        private final List<Integer>[] rightAdjacencyList;

        public BipartiteGraph(int inLeft, int inRight) {
            leftAdjacencyList = new List[inLeft];
            for (int i = 0; i < inLeft; ++i) {
                leftAdjacencyList[i] = new ArrayList<>();
            }
            rightAdjacencyList = new List[inRight];
            for (int i = 0; i < inRight; ++i) {
                rightAdjacencyList[i] = new ArrayList<>();
            }
            for (int i = 0; i < inLeft; ++i) {
                for (int j = 0; j < inRight; ++j) {
                    if (RANDOM.nextDouble() >= THRESHOLD) {
                        leftAdjacencyList[i].add(j);
                        rightAdjacencyList[j].add(i);
                    }
                }
            }
        }

        public BipartiteGraph(int inLeft, int inRight, long mask) {
            leftAdjacencyList = new List[inLeft];
            for (int i = 0; i < inLeft; ++i) {
                leftAdjacencyList[i] = new ArrayList<>();
            }
            rightAdjacencyList = new List[inRight];
            for (int i = 0; i < inRight; ++i) {
                rightAdjacencyList[i] = new ArrayList<>();
            }
            for (int i = 0; i < inLeft; ++i) {
                for (int j = 0; j < inRight; ++j) {
                    if (((mask >>> (i * inRight + j)) & 1) == 1) {
                        leftAdjacencyList[i].add(j);
                        rightAdjacencyList[j].add(i);
                    }
                }
            }
        }
        
        public int[][] findMatching(int[] order, boolean inLeft) {
            int[][] result;
            boolean[] leftMarked = new boolean[leftAdjacencyList.length];
            Arrays.fill(leftMarked, true);
            boolean[] rightMarked = new boolean[rightAdjacencyList.length];
            Arrays.fill(rightMarked, true);
            if (inLeft) {
                result = findMatching(order, leftAdjacencyList, leftMarked, rightMarked);
            } else {
                result = findMatching(order, rightAdjacencyList, rightMarked, leftMarked);
                int[] tmp = result[0];
                result[0] = result[1];
                result[1] = tmp;
            }
            return result;
        }

        public boolean existsPerfectMatching(boolean[] leftMarked, boolean[] rightMarked) {
            int[] order = new int[leftMarked.length];
            for (int i = 0; i < order.length; ++i) order[i] = i;
            int[][] matching = findMatching(order, leftAdjacencyList, leftMarked, rightMarked);
            boolean[][] union = new boolean[][]{leftMarked, rightMarked};
            for (int it = 0; it < 2; ++it) {
                for (int i = 0; i < matching[it].length; ++i) {
                    if (union[it][i] && matching[it][i] == -1) {
                        System.err.println(Arrays.deepToString(matching));
                        System.err.println(Arrays.deepToString(union));
                        return false;
                    }
                    if (matching[it][i] != -1 && !union[it][i]) {
                        throw new AssertionError("WTF?!");
                    }
                }
            }
            return true;
        }
        
        private static int[][] findMatching(int[] order, List<Integer>[] adjacencyList,
                                            boolean[] leftMarked, boolean[] rightMarked) {
            if (order.length != adjacencyList.length || order.length != leftMarked.length) {
                throw new IllegalArgumentException();
            }
            int[] lMatching = new int[leftMarked.length];
            Arrays.fill(lMatching, -1);
            int[] rMatching = new int[rightMarked.length];
            Arrays.fill(rMatching, -1);
            boolean[] visited = new boolean[leftMarked.length];
            for (int vertex : order) {
                if (leftMarked[vertex] && dfs(vertex, adjacencyList, lMatching, rMatching, rightMarked, visited)) {
                    Arrays.fill(visited, false);
                }
            }
            return new int[][]{lMatching, rMatching};
        }

        private static boolean dfs(int vertex, List<Integer>[] adjacencyList, int[] lMatching, int[] rMatching,
                                   boolean[] available, boolean[] visited) {
            if (vertex == -1) return true;
            if (visited[vertex]) return false;
            visited[vertex] = true;
            for (int to : adjacencyList[vertex]) {
                if (available[to] && dfs(rMatching[to], adjacencyList, lMatching, rMatching, available, visited)) {
                    lMatching[vertex] = to;
                    rMatching[to] = vertex;
                    return true;
                }
            }
            return false;
        }
    }
}
