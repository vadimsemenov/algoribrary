package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskE {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        char[] string = in.next().toCharArray();
        int max = 1;
        int cur = 1;
        Arrays.sort(string);
        for (int i = 1; i < string.length; ++i) {
            if (string[i] == string[i - 1]) {
                cur++;
                max = Math.max(max, cur);
            } else {
                cur = 1;
            }
        }
        out.println(max * 2 > string.length || string.length % 2 != 0 ? "NO" : "YES");
    }
}
