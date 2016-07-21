package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class ShilAndCrazyOperation {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        long times = in.nextLong();
        int[] array = in.nextIntArray(counter);
        int[] permutation = in.nextIntArray(counter);
        for (int i = 0; i < counter; ++i) --permutation[i];
        boolean[] visited = new boolean[counter];
        int[] answer = new int[counter];
        for (int i = 0; i < counter; ++i) if (!visited[i]) {
            int cur = i;
            int length = 0;
            while (!visited[cur]) {
                visited[cur] = true;
                ++length;
                cur = permutation[cur];
            }
            int remainder = (int) (times % length);
            int ptr = i;
            for (int ii = 0; ii < remainder; ++ii) {
                ptr = permutation[ptr];
            }
            cur = i;
            for (int ii = 0; ii < length; ++ii) {
                answer[cur] = array[ptr];
                ptr = permutation[ptr];
                cur = permutation[cur];
            }
        }
        for (int i = 0; i < counter; ++i) {
            if (i > 0) out.print(' ');
            out.print(answer[i]);
        }
    }
}
