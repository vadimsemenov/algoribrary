package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    String[] names = new String[]{"vaporeon", "jolteon", "flareon", "espeon", "umbreon", "leafeon", "glaceon", "sylveon"};
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        String pattern = in.next();
        for (String name : names) {
            if (name.length() != length) continue;
            boolean it = true;
            for (int i = 0; i < length; i++) {
                if (pattern.charAt(i) == '.') continue;
                if (pattern.charAt(i) != name.charAt(i)) it = false;
            }
            if (it) {
                out.println(name);
                return;
            }
        }
        throw new RuntimeException();
    }
}
