package tasks;

import java.util.Arrays;

public class MovingRooksDiv2 {
    private static final int[] factorial;
    static {
        factorial = new int[10];
        factorial[0] = 1;
        for (int i = 1; i < factorial.length; i++)
            factorial[i] = i * factorial[i - 1];
    }

    private boolean[] visited;

    public String move(int[] Y1, int[] Y2) {
        visited = new boolean[factorial[Y1.length]];
        dfs(Y1, indexOf(Y1));
        return visited[indexOf(Y2)] ? "Possible" : "Impossible";
    }

    private void dfs(int[] permutation, int index) {
        visited[index] = true;
        for (int i = 0; i < permutation.length; i++) {
            for (int j = i + 1; j < permutation.length; j++) {
                if (permutation[i] > permutation[j]) {
                    int[] next = Arrays.copyOf(permutation, permutation.length);
                    next[i] = permutation[j];
                    next[j] = permutation[i];
                    int idx = indexOf(next);
                    if (!visited[idx]) dfs(next, idx);
                }
            }
        }
    }

    public int indexOf(int[] permutation) {
        int index = 0;
        int[] elements = new int[permutation.length];
        for (int i = 0; i < elements.length; i++) elements[i] = i;
        for (int i = 0; i < permutation.length; i++) {
            int idx = 0;
            while (elements[idx] != permutation[i]) idx++;
            index += idx * factorial[permutation.length - i - 1];
            while (idx < permutation.length - i - 1) elements[idx++] = elements[idx];
        }
        return index;
    }
}
