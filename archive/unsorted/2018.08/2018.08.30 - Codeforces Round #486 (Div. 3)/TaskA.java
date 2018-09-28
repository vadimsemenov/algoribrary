package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int size = in.nextInt();
        Set<Integer> have = new HashSet<>();
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < counter; ++i) {
            int rating = in.nextInt();
            if (have.add(rating)) {
                ids.add(i + 1);
            }
        }
        if (ids.size() >= size) {
            out.println("YES");
            for (int i = 0; i < size; i++) {
                out.print(ids.get(i) + " ");
            }
        } else {
            out.println("NO");
        }
    }
}
