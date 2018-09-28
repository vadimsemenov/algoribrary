package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        String[] strings = in.nextStringArray(counter);
        Arrays.sort(strings, Comparator.comparingInt(String::length));
        for (int i = 1; i < counter; ++i) {
            if (!strings[i].contains(strings[i - 1])) {
                out.println("NO");
                return;
            }
        }
        out.println("YES");
        for (String string : strings) {
            out.println(string);
        }
    }
}
