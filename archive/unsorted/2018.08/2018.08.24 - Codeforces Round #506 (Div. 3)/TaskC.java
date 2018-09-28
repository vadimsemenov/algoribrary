package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int[][] segments = in.nextIntTable(count, 2);
        int minIdx = 0;
        int maxIdx = 0;
        int minCount = 1;
        int maxCount = 1;
        int secondMin = Integer.MIN_VALUE;
        int secondMax = Integer.MAX_VALUE;
        for (int i = 1; i < count; ++i) {
            int left = segments[i][0];
            int right = segments[i][1];
            if (left > segments[minIdx][0]) {
                secondMin = segments[minIdx][0];
                minIdx = i;
                minCount = 1;
            } else if (left == segments[minIdx][0]) {
                minCount++;
            } else if (left > secondMin) {
                secondMin = left;
            }
            if (right < segments[maxIdx][1]) {
                secondMax = segments[maxIdx][1];
                maxIdx = i;
                maxCount = 1;
            } else if (right == segments[maxIdx][1]) {
                maxCount++;
            } else if (right < secondMax) {
                secondMax = right;
            }
        }
        int answer = Math.max(0, segments[maxIdx][1] - segments[minIdx][0]);
        if (minCount == 1 && maxCount == 1) {
            if (minIdx == maxIdx) {
                answer = Math.max(answer, secondMax - secondMin);
            } else {
                answer = Math.max(answer, Math.max(secondMax - segments[minIdx][0], segments[maxIdx][1] - secondMin));
            }
        } else if (minCount == 1) {
            answer = Math.max(answer, segments[maxIdx][1] - secondMin);
        } else if (maxCount == 1) {
            answer = Math.max(answer, secondMax - segments[minIdx][0]);
        }
        out.println(answer);
    }
}
