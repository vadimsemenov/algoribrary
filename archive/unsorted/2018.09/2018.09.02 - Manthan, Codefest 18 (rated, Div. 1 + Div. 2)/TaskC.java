package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        char[][] table = in.nextCharTable(2, length);
        int answer = 0;
        for (int i = 0; i < length; ) {
            int j = i + 1;
            if (table[0][i] != table[1][i]) {
                while (j < length && table[0][j - 1] != table[0][j] && table[0][j] != table[1][j]) {
                    ++j;
                }
                answer += (j - i) / 2 + (j - i) % 2;
            }
            i = j;
        }
        out.println(answer);
    }
}
