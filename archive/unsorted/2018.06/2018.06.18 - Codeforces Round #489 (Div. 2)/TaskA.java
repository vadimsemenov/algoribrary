package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < count; ++i) {
            int x = in.nextInt();
            if (x != 0) set.add(x);
        }
        out.println(set.size());
    }
}
