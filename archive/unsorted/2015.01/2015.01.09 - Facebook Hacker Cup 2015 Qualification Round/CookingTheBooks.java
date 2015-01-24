package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class CookingTheBooks {
    private static final int[] powers = new int[10];
    static {
        powers[0] = 1;
        for (int i = 1; i < powers.length; ++i) {
            powers[i] = powers[i - 1] * 10;
        }
    }

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int initial = in.nextInt();
        int min, max;
        min = max = initial;
        for (int i = 0; i < Integer.toString(initial).length(); ++i) {
            for (int j = 0; j < i; ++j) {
                assert initial >= powers[i];
                int a = initial / powers[i] % 10;
                int b = initial / powers[j] % 10;
                if (i == Integer.toString(initial).length() - 1 && b == 0) continue;
                int tmp = initial + (b - a) * powers[i] + (a - b) * powers[j];
                min = Math.min(min, tmp);
                max = Math.max(max, tmp);
            }
        }
        out.printf("Case #%d: %d %d\n", testNumber, min, max);
    }
}
