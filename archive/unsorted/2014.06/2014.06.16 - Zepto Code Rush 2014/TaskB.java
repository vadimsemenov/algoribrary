package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int rows = in.nextInt();
        int columns = in.nextInt();
        int spiders = in.nextInt();
        int[] answer = new int[columns];
        char[][] table = new char[rows][];
        for (int i = 0; i < rows; i++)
            table[i] = in.next().toCharArray();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (table[i][j] == '.' || table[i][j] == 'D') continue;
                if (table[i][j] == 'U' && (i & 1) == 0) answer[j]++;
                if (table[i][j] == 'L' && j >= i) answer[j - i]++;
                if (table[i][j] == 'R' && (columns - j - 1) >= i) answer[j + i]++;
            }
        }
        for (int ans : answer) {
            out.print(ans);
            out.print(' ');
        }
        out.println();
    }
}
