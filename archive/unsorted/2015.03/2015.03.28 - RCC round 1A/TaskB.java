package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        String first = in.next();
        String second = in.next();
        int[] a = new int[first.length()];
        for (int i = 0; i < first.length(); ++i) {
            a[i] = first.charAt(first.length() - 1 - i) - '0';
        }
        int[] b = new int[second.length()];
        for (int i = 0; i < second.length(); ++i) {
            b[i] = second.charAt(second.length() - 1 - i) - '0';
        }
        int[] c = new int[a.length + b.length - 1];
        for (int i = 0; i < a.length; ++i) {
            for (int j = 0; j < b.length; ++j) {
                c[i + j] += a[i] * b[j];
            }
        }
//        System.err.println(Arrays.toString(c));
        String third = in.next();
        if (third.length() != c.length) {
            out.println("Finite");
            return;
        }
        for (int i = 0; i < third.length(); ++i) {
            if (third.charAt(i) - '0' != c[c.length - 1 - i]) {
                out.println("Finite");
                return;
            }
        }
        out.println("Infinity");
    }
}
