package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int balance = in.nextInt();
        int counter = 0;
        game:
        while (balance > 1) {
            boolean greater = false;
            for (int bit = 0; (1 << bit) <= balance; ++bit) {
                if (greater && isSet(balance, bit)) {
                    counter++;
                    balance >>= bit + 1;
                    continue game;
                }
                greater |= isSet(balance, bit);
            }
            break;
        }
        out.println(counter);
    }

    private boolean isSet(int mask, int bit) {
        return ((mask >>> bit) & 1) == 1;
    }
}
