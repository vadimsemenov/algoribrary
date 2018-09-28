package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskF {
    public void solve(int __, InputReader in, PrintWriter out) {
        int limit = in.nextInt();
        String text = in.next();
        int[][] distance = new int[text.length() + 2][text.length() + 2];
        for (int[] d : distance) Arrays.fill(d, Integer.MAX_VALUE / 3);
        for (int i = 0; i < distance.length; ++i) {
            distance[0][i] = 0;
            distance[i][text.length() + 1] = 0;
        }
        for (int i = 1; i <= text.length(); ++i) {
            for (int j = text.length(); j > i; --j) {
                distance[i][j] = distance[i - 1][j + 1] + Math.abs(text.charAt(i - 1) - text.charAt(j - 1));
            }
        }
        int left = 0;
        int right = (text.length() + 3) / 2;
        while (right - left > 1) {
            int length = (left + right) >>> 1;
            boolean possible = false;
            check:
            for (int i = 1; i + 2 * length <= text.length() + 1; ++i) {
                int fstEnd = i + length - 1;
                for (int j = fstEnd + 1; j + length <= text.length() + 1; ++j) {
                    int sndEnd = j + length - 1;
                    int dist = distance[fstEnd][j] - distance[i - 1][sndEnd + 1];
                    if (dist <= limit) {
                        possible = true;
                        break check;
                    }
                }
            }
            if (possible) {
                left = length;
            } else {
                right = length;
            }
        }
        out.println(left);
    }
}
