package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskE {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[] vals = new int[counter];
        for (int i = 0; i < counter; ++i) {
            vals[i] = in.nextInt();
        }
        if (vals[counter - 1] == 1) {
            out.println("NO");
            return;
        }
        if (counter == 1) {
            out.println("YES\n0");
            return;
        }
        if (vals[counter - 2] == 1) { // X -> 1 -> 0 => 1 -> 0 => 0
            out.println("YES");
            for (int i = 0; i < counter; ++i) {
                if (i > 0) out.print("->");
                out.print(vals[i]);
            }
            out.println();
            return;
        }
        // now end with 0, 0
        int j = counter - 3;
        while (j >= 0 && vals[j] == 1) {
            --j;
        }
        if (j < 0) {
            out.println("NO");
            return;
        }
        out.println("YES");
        // vals[j] == 0
        // X -> (0 -> (Y)) -> 0 => X -> 1 -> 0 => 1 -> 0 => 0
        for (int i = 0; i < j; ++i) {
            out.print(vals[i]);
            out.print("->");
        }
        out.print("(");
        out.print("0->");
        for (int i = j + 1; i < counter - 1; ++i) {
            if (i == j + 1) out.print("(");
            out.print(vals[i]);
            if (i + 1 == counter - 1) out.print("))");
            out.print("->");
        }
        out.println("0");
    }
}
