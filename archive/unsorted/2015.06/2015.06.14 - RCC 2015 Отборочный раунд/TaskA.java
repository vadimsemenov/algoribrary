package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskA {
    private static final int MODULO = 1_000_000_000 + 7;
    private static final int MAX = 123;
    private static final int[][] cnk = new int[MAX][];
    static {
        for (int i = 0; i < MAX; ++i) {
            cnk[i] = new int[i + 1];
            cnk[i][0] = cnk[i][i] = 1;
            for (int j = 1; j < i; ++j) {
                cnk[i][j] = (cnk[i - 1][j] + cnk[i - 1][j - 1]) % MODULO;
            }
        }
    }

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int initialLength = in.nextInt();
        int targetLength = in.nextInt();
        char[] string = in.next().toCharArray();
        int cnt0 = 0;
        int cnt1 = 0;
        for (char ch : string) if (ch == '1') ++cnt1; else ++cnt0;
        int answer = 0;
        for (int twos = 0; twos < initialLength; ++twos) for (int threes = 0; threes < initialLength; ++threes) {
            if (twos * 2 > cnt0 || threes * 3 > cnt1) continue;
            if (initialLength - 2 * twos - 3 * threes == targetLength) {
                answer += cnk[targetLength][cnt0 - 2 * twos];
                if (answer >= MODULO) answer -= MODULO;
            }
        }
        out.println(answer);
    }
}
