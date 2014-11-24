package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        char[][] strings = new char[counter][];
        for (int i = 0; i < counter; ++i) {
            strings[i] = in.next().toCharArray();
        }
        int length = strings[0].length;
        long[] similar = new long[1 << length];
        for (int i = 0; i < counter; ++i) {
            for (int j = i + 1; j < counter; ++j) {
                int mask = 0;
                for (int k = 0; k < length; ++k) {
                    if (strings[i][k] == strings[j][k]) {
                        mask |= (1 << k);
                    }
                }
                similar[mask] |= (1L << i);
                similar[mask] |= (1L << j);
            }
        }
        for (int mask = (1 << length) - 1; mask >= 0; --mask) {
            for (int next = 0; next < length; ++next) {
                if (((mask >> next) & 1) == 1) {
                    similar[mask ^ (1 << next)] |= similar[mask];
                }
            }
        }
        int[][] cnk = new int[length + 1][];
        for (int i = 0; i <= length; ++i) {
            cnk[i] = new int[i + 1];
            cnk[i][0] = cnk[i][i] = 1;
            for (int j = 1; j < i; ++j) {
                cnk[i][j] = cnk[i - 1][j - 1] + cnk[i - 1][j];
            }
        }
        double answer = 0;
        for (int mask = 0; mask < (1 << length); ++mask) {
            for (int next = 0; next < length; ++next) {
                if (((mask >> next) & 1) == 0) {
                    int asked = Integer.bitCount(mask);
                    int guess = Long.bitCount(similar[mask]) - Long.bitCount(similar[mask | (1 << next)]);
                    answer += (asked + 1) * 1.0 / cnk[length][asked] / (length - asked) * guess / counter;
                }
            }
        }
        out.println(answer);
    }
}
