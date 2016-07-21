package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskD {
    private static final int MODULO = 1_000_000_000 + 7;
    private static final int MAX = 200_000;
    private static final long[] C = new long[MAX + 1];
    static {
        C[0] = 1;
        for (int n = 1; n < C.length; ++n) {
            C[n] = C[n - 1] * 2 * (2 * n - 1) % MODULO * inverse(n + 1) % MODULO;
        }
    }

    private static long inverse(int number) {
        return power(number, MODULO - 2);
    }

    private static long power(long base, int power) {
        long result = 1;
        while (power > 0) {
            if ((power & 1) == 1) {
                result = (result * base) % MODULO;
            }
            base = (base * base) % MODULO;
            power >>>= 1;
        }
        return result;
    }

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[] stack = new int[counter];
        int top = 0;
        long answer = 1;
        for (int i = 0; i <= counter; ++i) {
            int priority = i == counter ? Integer.MAX_VALUE : in.nextInt();
            while (top > 0 && stack[top - 1] < priority) {
                int cnt = 1;
                while (top - cnt > 0 && stack[top - 1] == stack[top - cnt - 1]) {
                    ++cnt;
                }
                answer = (answer * C[cnt]) % MODULO;
                top -= cnt;
            }
            stack[top++] = priority;
        }
        out.println(answer);
    }
}
