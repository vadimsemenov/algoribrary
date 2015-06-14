package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[] raiting = in.nextIntArray(counter);
        for (int i = 0; i < counter; ++i) {
            int place = 1;
            for (int j = 0; j < counter; ++j) {
                if (raiting[i] < raiting[j]) ++place;
            }
            out.print(place + " ");
        }
    }
}
