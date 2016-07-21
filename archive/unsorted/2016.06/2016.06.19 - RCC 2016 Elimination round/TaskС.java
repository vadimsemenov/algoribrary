package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskС {
    private static final int MODULO = 1_000_000_000 + 7;

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int tests = in.nextInt();
        while (tests --> 0) {
            solveCase(in, out);
        }
    }

    List<Integer>[] tree;
    int[] degree;
    int[] max, maxId, secondMax;
    int[] result;

    private void solveCase(InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        tree = new List[counter];
        for (int i = 0; i < counter; ++i) tree[i] = new ArrayList<>();
        degree = new int[counter];
        max = new int[counter];
        maxId = new int[counter];
        secondMax = new int[counter];
        result = new int[counter];

        for (int i = 1; i < counter; ++i) {
            int v = in.nextInt() - 1;
            int u = in.nextInt() - 1;
            tree[u].add(v);
            tree[v].add(u);
            ++degree[u];
            ++degree[v];
        }

        for (int start = 0; start < counter; ++start) {
            if (degree[start] < 3) {
                boolean possible = prepare(start, -1);
                if (!possible) {
                    out.println(-1);
                    return;
                }
                dfs(start, -1, 0);
                break;
            }
        }

        int bestId = -1;
        for (int i = 0; i < counter; ++i) {
            if (degree[i] < 3 && (bestId < 0 || result[i] < result[bestId])) {
                bestId = i;
            }
        }
        int answer = 1;
        for (int i = 0; i <= result[bestId]; ++i) {
            answer = 2 * answer % MODULO;
        }
        answer = (answer + (MODULO - counter - 1)) % MODULO;
        out.println((bestId + 1) + " " + answer);
    }

    private void dfs(int vertex, int parent, int up) {
        List<Integer> children = tree[vertex];
        result[vertex] = Math.max(up, max[vertex]);
        for (int i = 0; i < children.size(); i++) {
            int child = children.get(i);
            if (child != parent) {
                int childUp = Math.max(up, i == maxId[vertex] ? secondMax[vertex] : max[vertex]) + 1;
                dfs(child, vertex, childUp);
            }
        }
    }

    private boolean prepare(int vertex, int parent) {
        if (degree[vertex] > 3) {
            return false;
        }
        maxId[vertex] = -1;
        List<Integer> children = tree[vertex];
        for (int i = 0; i < children.size(); i++) {
            int child = children.get(i);
            if (child != parent) {
                if (!prepare(child, vertex)) return false;
                if (maxId[vertex] < 0) {
                    maxId[vertex] = i;
                    max[vertex] = max[child] + 1;
                } else if (max[vertex] < max[child] + 1) {
                    secondMax[vertex] = max[vertex];
                    maxId[vertex] = i;
                    max[vertex] = max[child] + 1;
                } else if (secondMax[vertex] < max[child] + 1) {
                    secondMax[vertex] = max[child] + 1;
                }
            }
        }
        return true;
    }
}
