package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskD {
    static long[][] choose;
    static int max = 5_000;
    static final int MODULO = 1_000_000_007;

    static {
        choose = new long[max + 1][];
        for (int i = 1; i <= max; i++) {
            choose[i] = new long[i + 1];
            choose[i][0] = choose[i][i] = 1;
            for (int j = 1; j < i; j++) {
                choose[i][j] = choose[i - 1][j] + choose[i - 1][j - 1];
                if (choose[i][j] > MODULO) choose[i][j] -= MODULO;
            }
        }
    }

    int[] mem;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        mem = new int[counter + 1];
        for (int i = 0; i <= counter && i < 3; i++) mem[i] = 1;
        for (int n = 3; n <= counter; n++) {
            for (int i = 1; i < n; i += 2) {
                mem[n] += (((mem[i] * choose[n - 1][i]) % MODULO) * mem[n - 1 - i]) % MODULO;
                mem[n] %= MODULO;
            }
        }
        out.println(mem[counter]);
    }
}
