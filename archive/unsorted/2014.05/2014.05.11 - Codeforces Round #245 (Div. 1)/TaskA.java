package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TaskA {
    private List<Integer>[] tree;
    private int[] initial, goal;
    private List<Integer> answer;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        tree = new List[counter];
        initial = new int[counter];
        goal = new int[counter];
        for (int i = 0; i < counter; i++) tree[i] = new ArrayList<>();
        for (int i = 1; i < counter; i++) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            tree[u].add(v);
            tree[v].add(u);
        }
        for (int i = 0; i < counter; i++) initial[i] = in.nextInt();
        for (int i = 0; i < counter; i++) goal[i] = in.nextInt();
        answer = new ArrayList<>();
        dfs(0, -1, false, false);
        out.println(answer.size());
        for (int a : answer) out.println(a);
    }

    private void dfs(int vertex, int parent, boolean current, boolean next) {
        if (current) initial[vertex] ^= 1;
        if (initial[vertex] != goal[vertex]) {
            answer.add(vertex + 1);
            current = !current;
        }
        for (int to : tree[vertex]) if (to != parent) {
            dfs(to, vertex, next, current);
        }
    }
}
