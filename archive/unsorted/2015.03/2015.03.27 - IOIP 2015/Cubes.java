package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Cubes {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] height = new int[counter];
        int max = -1;
        for (int i = 0; i < counter; ++i) {
            height[i] = in.nextInt();
            max = Math.max(max, height[i]);
        }
        int answer = Integer.MAX_VALUE;
        for (int target = 0; target <= max; ++target) {
            int less = 0;
            int greater = 0;
            for (int h : height) {
                if (h < target) {
                    less += target - h;
                } else if (h > target) {
                    greater += h - target;
                }
            }
            answer = Math.min(answer, Math.max(less, greater));
        }
        out.println(answer);
    }
}
