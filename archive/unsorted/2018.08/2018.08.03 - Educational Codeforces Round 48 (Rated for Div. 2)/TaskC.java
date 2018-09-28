package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        long[][] table = in.nextLongTable(2, length);
        long[][] mul = new long[2][length + 1];
        long[][] add = new long[2][length + 1];
        for (int i = 0; i < 2; ++i) {
            for (int j = length - 1; j >= 0; --j) {
                mul[i][j] = mul[i][j + 1] + (j + 1) * table[i][j];
                add[i][j] = add[i][j + 1] + table[i][j];
            }
        }
        long have = 0;
        long answer = 0;
        for (int i = 0; i < length; ++i) {
            for (int j = 0, currentRow = i % 2; j < 2; ++j, currentRow ^= 1) {
                int nextRow = currentRow ^ 1;
                int currentTime = i * 2 + j;
                int nextTime = currentTime + (length - i);
                long current = have +
                        (currentTime - i - 1) * add[currentRow][i] + mul[currentRow][i] +
                        (nextTime + length) * add[nextRow][i + 1] - mul[nextRow][i + 1];
                if (j == 0) {
                    current += (2 * length - 1) * table[nextRow][i];
                }
                answer = Math.max(answer, current);
                have += currentTime * table[currentRow][i];
            }
        }
        out.println(Math.max(answer, have));
    }
}
