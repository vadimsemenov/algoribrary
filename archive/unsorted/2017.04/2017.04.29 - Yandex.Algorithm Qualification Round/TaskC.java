package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] cost = in.nextIntArray(counter);
        int[][] tree = new int[counter][];
        for (int i = 0; i < counter; ++i) {
            int qty = in.nextInt();
            tree[i] = new int[qty];
            for (int j = 0; j < qty; ++j) tree[i][j] = in.nextInt() - 1;
        }
        out.println(Arrays.stream(cost).sum() + " " + dfs(0, tree, cost));
    }

    private long dfs(int vertex, int[][] tree, int[] cost) {
        long sum = 0;
        for (int child : tree[vertex]) sum += dfs(child, tree, cost);
        return sum * 2 + cost[vertex];
    }
}
