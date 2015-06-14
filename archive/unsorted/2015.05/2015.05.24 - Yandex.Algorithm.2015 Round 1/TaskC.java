package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskC {
    private static final int MODULO = 1_000_000_000 + 7;

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt() - 1;
        triangle = new int[n + 1][n + 2];
        for (int[] t : triangle) Arrays.fill(t, -1);
        out.println(rec(n, k));
    }

    private int[][] triangle;

    private int rec(int n, int k) {
        if (k < 0 || k > n) return 0;
        int res = triangle[n][k];
        if (res == -1) {
            if (k == 0 && n >= 0) {
                res = 1;
            } else {
                long tmp;
                {
                    tmp = n - k;
                    tmp *= rec(n - 1, k - 1);
                    tmp %= MODULO;
                }
                res = (int) tmp;
                {
                    tmp = k + 1;
                    tmp *= rec(n - 1, k);
                    tmp %= MODULO;
                }
                res += (int) tmp;
                if (res >= MODULO) res -= MODULO;
            }
        }
        return triangle[n][k] = res;
    }

    private void oeis() {
        int length = 10;
        int[] answer = new int[length];
        int[] permutation = new int[length];
        for (int i = 0; i < length; ++i) permutation[i] = i;
        do {
            int bottom = 0;
            int top = length - 1;
            int ptr = 0;
            while (bottom < top) {
                if (permutation[ptr] == bottom) {
                    ++bottom;
                } else if (permutation[ptr] == top) {
                    --top;
                }
                ptr = (ptr + 1) % length;
            }
            answer[bottom]++;
        } while (nextPermutation(permutation));
        System.out.println(Arrays.toString(answer));
    }

    private boolean nextPermutation(int[] permutation) {
        int idx = permutation.length - 2;
        while (idx >= 0 && permutation[idx] > permutation[idx + 1]) {
            --idx;
        }
        if (idx < 0) return false;
        int toSwap = idx + 1;
        for (int i = idx + 2; i < permutation.length; ++i) {
            if (permutation[i] > permutation[idx] && permutation[i] < permutation[toSwap]) {
                toSwap = i;
            }
        }
        int tmp = permutation[idx];
        permutation[idx] = permutation[toSwap];
        permutation[toSwap] = tmp;
        Arrays.sort(permutation, idx + 1, permutation.length);
        return true;
    }
}
