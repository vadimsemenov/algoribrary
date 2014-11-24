package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskB {
    private PrintWriter out;
    private int[] good = new int[8];
    private int[][] points = new int[8][3];

    private int[][] permutations = new int[][]{
            new int[]{0, 1, 2},
            new int[]{0, 2, 1},
            new int[]{1, 0, 2},
            new int[]{1, 2, 0},
            new int[]{2, 0, 1},
            new int[]{2, 1, 0}
    };

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        this.out = out;
        for (int i = 0; i < 8; ++i) for (int j = 0; j < 3; ++j)
            points[i][j] = in.nextInt();
        if (!go(1)) out.println("NO");
    }

    private boolean go(int idx) {
        if (idx == 8) {
            if (check()) {
                out.println("YES");
                for (int i = 0; i < 8; ++i) {
                    out.println(
                            points[i][permutations[good[i]][0]] + " " +
                            points[i][permutations[good[i]][1]] + " " +
                            points[i][permutations[good[i]][2]]
                    );
                }
                return true;
            }
            return false;
        }
        for (int perm = 0; perm < permutations.length; ++perm) {
            good[idx] = perm;
            if (go(idx + 1)) return true;
        }
        return false;
    }

    private boolean check() {
        long a = -1, b = -1, c = -1;
        int ca = 0, cb = 0, cc = 0;
        for (int i = 0; i < 8; ++i) for (int j = i + 1; j < 8; ++j) {
            long dx = points[i][permutations[good[i]][0]] - points[j][permutations[good[j]][0]];
            long dy = points[i][permutations[good[i]][1]] - points[j][permutations[good[j]][1]];
            long dz = points[i][permutations[good[i]][2]] - points[j][permutations[good[j]][2]];
            dx *= dx; dy *= dy; dz *= dz;
            long d = dx + dy + dz;
            if (a == -1 || d == a) {
                a = d; ca++;
            } else if (b == -1 || d == b) {
                b = d; cb++;
            } else if (c == -1 || d == c) {
                c = d; cc++;
            } else {
                return false;
            }
        }
        if (b < a && b < c) {
            long tmp = a; a = b; b = tmp;
            int ttmp = ca; ca = cb; cb = ttmp;
        }
        if (c < a && c < b) {
            long tmp = a; a = c; c = tmp;
            int ttmp = ca; ca = cc; cc = ttmp;
        }
        if (b > c) {
            long tmp = c; c = b; b = tmp;
            int ttmp = cc; cc = cb; cb = ttmp;
        }
        return 2 * a == b && 3 * a == c && ca == 12 && cb == 12 && cc == 4;
    }
}
