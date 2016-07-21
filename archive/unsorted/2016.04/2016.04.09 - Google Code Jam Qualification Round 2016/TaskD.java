package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TaskD {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int length = in.nextInt();
        int counter = in.nextInt();
        int limit = in.nextInt();
        int currentLayer = 0;
        long currentPosition = 0;
        List<Long> result = new ArrayList<>(limit);
        int last = -1;
        for (int i = 0; i < length; ++i) {
            if (currentLayer == counter) {
                result.add(currentPosition + 1);
                currentLayer = 0;
                currentPosition = 0;
                last = i - 1;
            }
            currentPosition *= length;
            currentPosition += i;
            ++currentLayer;
        }
        if (last != length - 1) {
            while (currentLayer < counter) {
                currentPosition *= length;
                ++currentLayer;
            }
            result.add(currentPosition + 1);
        }

        out.print("Case #" + testNumber + ": ");
        if (result.size() <= limit) {
            assert result.size() * counter >= length;
            for (long i : result) {
                out.print(i + " ");
            }
            out.println();
        } else {
            out.println("IMPOSSIBLE");
        }
    }
}
