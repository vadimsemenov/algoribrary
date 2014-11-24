package tasks;



import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        char[] path = in.next().toCharArray();
        boolean[] mask = new boolean[path.length];
        for (int i = 0; i < path.length; ++i) {
            mask[i] = true;
            if (path[i] == '/') {
                int j = i;
                while (j < path.length && path[j] == '/') ++j;
                if (j == path.length && i != 0) mask[i] = false;
                i = j - 1;
            }
        }
        for (int i = 0; i < path.length; ++i) if (mask[i])
            out.print(path[i]);
    }
}
