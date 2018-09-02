package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Athletic {
    private List<Integer>[] tree;
    private boolean[] good;

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int vertices = in.nextInt();
        good = new boolean[vertices];
        tree = new List[vertices];
        for (int i = 0; i < vertices; i++) {
            tree[i] = new ArrayList<>();
        }
        for (int e = 1; e < vertices; ++e) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            tree[v].add(u);
            tree[u].add(v);
        }
        int counter = in.nextInt();
        for (int i = 0; i < counter; ++i) good[in.nextInt() - 1] = true;
        dfs(0, -1);
        out.println(Math.max(A, B));
    }

    private int A, B;
    private boolean dfs(int vertex, int parent) {
        int cnt = 0;
        if (good[vertex]) ++cnt;
        int sumA = 0;
        int bestSub = Integer.MAX_VALUE;
        int secondSub = Integer.MAX_VALUE;
        for (int to : tree[vertex]) if (to != parent) {
            if (dfs(to, vertex)) {
                ++cnt;
                int sub = A - B;
                if (sub < bestSub) {
                    secondSub = bestSub;
                    bestSub = sub;
                } else if (sub < secondSub) {
                    secondSub = sub;
                }
            }
            sumA += A;
        }
        if (good[vertex]) {
            secondSub = bestSub;
            bestSub = 0;
        }
        A = sumA;
        if (cnt >= 2) A = Math.max(A, 1 + A - bestSub - secondSub);
        B = sumA;
        if (cnt > 0) B -= bestSub;
        return cnt > 0;
    }
}
