package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskE {
    private static final int MAX_NUMBER = 1_000_000;
    private static final int[] MAX_PRIME = new int[MAX_NUMBER + 1];

    static {
        for (int i = 0; i <= MAX_NUMBER; ++i) {
            MAX_PRIME[i] = i;
        }
        MAX_PRIME[0] = MAX_PRIME[1] = 0;
        for (int p = 2; p <= MAX_NUMBER; ++p) {
            if (MAX_PRIME[p] == p) {
                for (int d = p + p; d <= MAX_NUMBER; d += p) {
                    MAX_PRIME[d] = p;
                }
            }
        }
    }

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int cuts = in.nextInt();
        int[] array = new int[counter];
        int max = -1;
        for (int i = 0; i < counter; ++i) max = Math.max(max, array[i] = in.nextInt());
        int left = -1;
        int right = max + 1;
        while (right - left > 1) {
            int middle = (left + right) >>> 1;
            if (check(array, cuts, middle)) {
                left = middle;
            } else {
                right = middle;
            }
        }
        out.println(left);
    }

    private boolean check(int[] array, int cuts, int min) {
        int ptr = 0;
        int done = 0;
        while (ptr < array.length) {
            ++done;
            int gcd = array[ptr];
            if (MAX_PRIME[gcd] < min) return false;
            do { ++ptr; } while (ptr < array.length && MAX_PRIME[gcd = gcd(array[ptr], gcd)] >= min);
        }
        return done <= cuts;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int c = a % b;
            a = b;
            b = c;
        }
        return a;
    }
}
