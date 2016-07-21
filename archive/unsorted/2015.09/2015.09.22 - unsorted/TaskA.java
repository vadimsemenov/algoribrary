package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.math.BigInteger;

public class TaskA {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        BigInteger from = in.nextBigInteger();
        int length = in.nextInt();
        String _binary = from.toString(2);
        boolean[] binary = new boolean[Math.max(_binary.length(), 4 * length)];
        for (int i = 0; i < _binary.length(); ++i) binary[_binary.length() - 1 - i] = _binary.charAt(i) == '1';
        int lastOne = 0;
        while (!binary[lastOne]) ++lastOne;
        for (int i = 0; i < length; ++i) {
            out.print(lastOne + 1);
            out.print(' ');
            int next = 0;
            while (binary[next]) {
                binary[next++] = false;
            }
            lastOne = next;
            binary[lastOne] = true;
        }
        out.println();
    }
}
