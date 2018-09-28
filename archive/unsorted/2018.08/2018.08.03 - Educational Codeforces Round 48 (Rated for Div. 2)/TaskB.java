package task;

import algoribrary.io.InputReader;
import algoribrary.strings.StringUtils;

import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int queries = in.nextInt();
        String text = in.next();
        String pattern = in.next();
        String s = pattern + "#" + text;
        int[] zFunction = StringUtils.buildZFunction(s);
        int[] sums = new int[zFunction.length];
        for (int i = pattern.length(); i < sums.length; ++i) {
            sums[i] = sums[i - 1];
            if (zFunction[i] == pattern.length()) {
                sums[i]++;
            }
        }
        for (int q = 0; q < queries; ++q) {
            int l = in.nextInt() + pattern.length() - 1;
            int r = in.nextInt() + 1;
            if (r < l) {
                out.println(0);
            } else {
                out.println(sums[r] - sums[l]);
            }
        }
    }
}
