package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        String[] sides = new String[6];
        for (int i = 0; i < sides.length; ++i) {
            sides[i] = in.next();
        }
        // Front, Top, Bottom, Left, Right, and Rear
        final int[][] axises = {
                {0, 1, 5, 2},
                {1, 4, 2, 3},
                {0, 4, 5, 3}
        };
        int queries = in.nextInt();
        for (int q = 0; q < queries; ++q) {
            String type = in.next();
            int times = in.nextInt();
            int idx;
            if (type.equals("X")) {
                idx = 0;
            } else if (type.equals("Y")) {
                idx = 1;
            } else if (type.equals("Z")) {
                idx = 2;
            } else {
                throw new AssertionError(type);
            }
            for (int i = 0; i < times % 4; ++i) {
                String next = sides[axises[idx][3]];
                for (int j = 0; j < 4; ++j) {
                    String tmp = sides[axises[idx][j]];
                    sides[axises[idx][j]] = next;
                    next = tmp;
                }
            }
        }
        for (String s : sides) {
            out.print(s + " ");
        }
        out.println();
    }
}
