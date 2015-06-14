package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MAXDIST {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        Thread worker = new Thread(null, new Runnable() {
            @Override
            public void run() {
                int vertices = in.nextInt();
                BEST = -1;
                BEST_DEPTH = -1;
                CNT = -1;
                FROM = new int[vertices];
                List<Integer>[] tree = new List[vertices];
                for (int i = 0; i < vertices; ++i) tree[i] = new ArrayList<>();
                for (int i = 1; i < vertices; ++i) {
                    int u = in.nextInt() - 1;
                    int v = in.nextInt() - 1;
                    tree[u].add(v);
                    tree[v].add(u);
                }
                dfs(tree, 0, -1, 0);
                int first = BEST;
                dfs(tree, BEST, -1, 0);
                int second = BEST;
                int diameter = BEST_DEPTH;
                if (diameter < 2) {
                    out.println("NO");
                    return;
                }
                if (diameter % 2 == 0) {
                    int middle = second;
                    for (int i = 0; i + i < diameter; ++i) {
                        middle = FROM[middle];
                    }
                    int cnt = 0;
                    for (int child : tree[middle]) {
                        dfs(tree, child, middle, 0);
                        if ((BEST_DEPTH + 1) * 2 == diameter) {
                            ++cnt;
                        }
                    }
                    out.println(cnt > 2 ? "NO" : "YES");
                } else {
                    int foo = second;
                    for (int i = 0; i + i < diameter - 1; ++i) {
                        foo = FROM[foo];
                    }
                    int[] middle = new int[]{foo, FROM[foo]};
                    int[] cnt = new int[2];
                    for (int it = 0; it < 2; ++it) {
                        for (int child : tree[middle[it]])
                            if (child != middle[it ^ 1]) {
                                dfs(tree, child, middle[it], 0);
                                if ((BEST_DEPTH + 1) * 2 + 1 == diameter) {
                                    ++cnt[it];
                                }
                            }
                    }
                    out.println(Math.min(cnt[0], cnt[1]) > 1 ? "NO" : "YES");
                }
            }
        }, "foo", 1 << 20);
        worker.start();
        try {
            worker.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int BEST, BEST_DEPTH, CNT;
    private int[] FROM;
    private void dfs(List<Integer>[] tree, int vertex, int parent, int depth) {
        FROM[vertex] = parent;
        int best = vertex;
        int bestDepth = depth;
        int cnt = 1;
        for (int to : tree[vertex]) if (to != parent) {
            dfs(tree, to, vertex, depth + 1);
            if (bestDepth < BEST_DEPTH) {
                best = BEST;
                bestDepth = BEST_DEPTH;
                cnt = CNT;
            } else if (bestDepth == BEST_DEPTH) {
                cnt += CNT;
            }
        }
        BEST = best;
        BEST_DEPTH = bestDepth;
        CNT = cnt;
    }
}
