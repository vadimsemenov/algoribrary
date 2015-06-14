package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        String name = in.next();
        String text = in.next();
        int left = 0;
        int have = 0;
        while (left < text.length()) {
            if (name.charAt(have) == text.charAt(left)) {
                have++;
            }
            if (have == name.length()) {
                break;
            }
            left++;
        }
        if (have < name.length()) {
            out.println(0);
            return;
        }
        int right = text.length() - 1;
        have = name.length() - 1;
        while (right >= 0) {
            if (name.charAt(have) == text.charAt(right)) {
                have--;
            }
            if (have == -1) {
                break;
            }
            right--;
        }
        if (have >= 0 || left > right) {
            out.println(0);
            return;
        }
        out.println(right - left);
    }
}
