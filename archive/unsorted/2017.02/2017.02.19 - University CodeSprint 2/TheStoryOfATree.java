package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class TheStoryOfATree {
    private int vertices;
    private List<Integer>[] tree;
    private int threshold;
    private List<Integer>[] maybe;
    private int qty;

    public void solve(int __, InputReader in, PrintWriter out) {
        qty = 0;
        vertices = in.nextInt();
        tree = new List[vertices];
        maybe = new List[vertices];
        for (int i = 0; i < vertices; ++i) {
            tree[i] = new ArrayList<>();
            maybe[i] = new ArrayList<>();
        }
        for (int i = 0; i < vertices - 1; ++i) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            tree[u].add(v);
            tree[v].add(u);
        }
        int queries = in.nextInt();
        threshold = in.nextInt();
        for (int i = 0; i < queries; ++i) {
            int parent = in.nextInt() - 1;
            int child = in.nextInt() - 1;
            maybe[child].add(parent);
        }
        for (int i = 0; i < vertices; ++i) Collections.sort(maybe[i]);
        int score = dfs(0, -1);
        go(0, -1, score);
        int gcd = gcd(qty, vertices);
        out.println((qty / gcd) + "/" + (vertices / gcd));
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int c = a % b;
            a = b;
            b = c;
        }
        return a;
    }

    private void go(int vertex, int parent, int score) {
        if (score >= threshold) ++qty;
        for (int child : tree[vertex]) if (child != parent) {
            int ad = check(vertex, child) - check(child, vertex);
            go(child, vertex, score + ad);
        }
    }

    private int dfs(int vertex, int parent) {
        int score = check(vertex, parent);
        for (int child : tree[vertex]) if (child != parent) {
            score += dfs(child, vertex);
        }
        return score;
    }

    private int check(int child, int parent) {
        if (child == -1 || parent == -1) return 0;
        int left = -1;
        int right = maybe[child].size();
        while (left + 1 < right) {
            int middle = (left + right) >>> 1;
            if (maybe[child].get(middle) > parent) {
                right = middle;
            } else {
                left = middle;
            }
        }
        int r = left + 1;
        left = -1;
        while (left + 1 < r) {
            int middle = (left + r) >>> 1;
            if (maybe[child].get(middle) >= parent) {
                r = middle;
            } else {
                left = middle;
            }
        }
        return right - left - 1;
    }
}
