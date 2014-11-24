package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Signal {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        List<Integer>[] tree = new List[vertices];
        for (int i = 0; i < vertices; ++i) tree[i] = new ArrayList<>();
        for (int i = 1; i < vertices; ++i) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            tree[u].add(v);
            tree[v].add(u);
        }
        out.println(go(tree, 0, -1));
    }

    private int go(List<Integer>[] tree, int vertex, int parent) {
        List<Integer> buffer = new ArrayList<>();
        for (int to : tree[vertex]) if (to != parent) {
            buffer.add(go(tree, to, vertex));
        }
        Collections.sort(buffer);
        Collections.reverse(buffer);
        int result = 0;
        for (int i = 0; i < buffer.size(); ++i) {
            result = Math.max(result, buffer.get(i) + i + 1);
        }
        return result;
    }
}
