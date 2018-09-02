package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] array = in.nextIntArray(counter);
        int[][] f = new int[counter][];
        f[0] = Arrays.copyOf(array, counter);
        for (int len = 1; len < counter; ++len) {
            f[len] = new int[counter - len];
            for (int start = 0; start < counter - len; ++start) {
                f[len][start] = f[len - 1][start] ^ f[len - 1][start + 1];
            }
        }
        int[][] max = new int[counter][];
        max[0] = Arrays.copyOf(f[0], counter);
        for (int len = 1; len < counter; ++len) {
            max[len] = new int[counter - len];
            for (int start = 0; start < counter - len; ++start) {
                max[len][start] = Math.max(f[len][start], Math.max(max[len - 1][start], max[len - 1][start + 1]));
            }
        }
        int queries = in.nextInt();
        for (int q = 0; q < queries; ++q) {
            int left = in.nextInt() - 1;
            int right = in.nextInt() - 1;
            int len = right - left;
            out.println(max[len][left]);
        }
    }
}
