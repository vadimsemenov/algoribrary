package task;

import algoribrary.io.InputReader;
import algoribrary.misc.ArrayUtils;

import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int[] coordinates = in.nextIntArray(count * 2);
        ArrayUtils.sort(coordinates);
        long max = coordinates[coordinates.length - 1] - coordinates[0];
        long answer = ((long) coordinates[count - 1] - coordinates[0]) * (coordinates[2 * count - 1] - coordinates[count]);
        for (int i = 1; i + count < coordinates.length; ++i) {
            answer = Math.min(answer, max * (coordinates[i + count - 1] - coordinates[i]));
        }
        out.println(answer);
    }
}
