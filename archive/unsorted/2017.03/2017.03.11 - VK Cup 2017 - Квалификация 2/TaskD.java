package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        char[] sequence = in.next().toCharArray();
        int maxBalance = 0;
        int balance = 0;
        int pairsQty = 0;
        for (int i = 0; i < length; ++i) {
            if (sequence[i] == '[') {
                ++balance;
                maxBalance = Math.max(maxBalance, balance);
            } else if (sequence[i] == ']') {
                --balance;
                if (sequence[i - 1] == '[') ++pairsQty;
            } else assert false : "WTF?!: " + sequence[i];
            assert balance >= 0;
        }
        char[][] ascii = new char[2 * maxBalance + 1][length + pairsQty * 3];
        for (char[] row : ascii) Arrays.fill(row, ' ');
        assert balance == 0;
        for (int i = 0, currentColumn = 0; i < length; ++i, ++currentColumn) {
            int bottom = ascii.length - 1 - balance;
            if (sequence[i] == ']') {
                if (sequence[i - 1] == '[') currentColumn += 3;
                --balance;
                bottom = ascii.length - 1 - balance;
                ascii[balance][currentColumn - 1] = ascii[bottom][currentColumn - 1] = '-';
            }
            ascii[balance][currentColumn] = '+';
            for (int row = balance + 1; row < bottom; ++row) {
                ascii[row][currentColumn] = '|';
            }
            ascii[bottom][currentColumn] = '+';
            if (sequence[i] == '[') {
                ascii[balance][currentColumn + 1] = ascii[bottom][currentColumn + 1] = '-';
                ++balance;
            }
        }
        for (char[] row : ascii) out.println(row);
    }
}
