package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int height = in.nextInt();
        long id = in.nextLong();
        boolean isLeft = false;
        long answer = 0;
        while (height > 0) {
            answer++;
            long leavesInSubTree = 1L << (height - 1);
            if (leavesInSubTree < id) {
                id -= leavesInSubTree;
                if (!isLeft) {
                    answer += 2 * leavesInSubTree - 1;
                }
                isLeft = false;
            } else {
                if (isLeft) {
                    answer += 2 * leavesInSubTree - 1;
                }
                isLeft = true;
            }
            height--;
        }
        out.println(answer);
    }
}
