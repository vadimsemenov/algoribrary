package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskC {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int tests = in.nextInt();
        while (tests --> 0) {
            int a1 = in.nextInt();
            int b1 = -in.nextInt();
            int a2 = in.nextInt();
            int b2 = -in.nextInt();
            int time = in.nextInt();
            int currentTime = 0;
            double answer = 0;
            boolean goodTime = a1 > a2;
            int v1 = a1;
            int v2 = a2;
            int x1 = 0;
            int x2 = 0;
            while (currentTime < time) {
                int nx1 = x1 + v1;
                int nx2 = x2 + v2;
                if (goodTime) {
                    if (nx2 < nx1) {
                        answer++;
                    } else {
                        if (v2 != v1) {
                            answer += (double) (x1 - x2) / (v2 - v1);
                        }
                        goodTime = false;
                    }
                } else {
                    if (nx2 < nx1) {
                        if (v1 != v2) {
                            answer += 1. - (double) (x2 - x1) / (v1 - v2);
                        }
                        goodTime = true;
                    } else {
                        // nothing
                    }
                }
                x1 = nx1;
                x2 = nx2;
                ++currentTime;
                if (currentTime % 12 == 0) {
                    v1 ^= a1 ^ b1;
                    v2 ^= a2 ^ b2;
                }
            }
//            answer = 1.1;
            out.println(answer);
        }
    }
}
