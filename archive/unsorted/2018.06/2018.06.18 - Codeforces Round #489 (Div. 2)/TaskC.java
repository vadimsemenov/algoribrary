package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskC {
    private static final int MODULO = 1_000_000_000 + 7;

    public void solve(int __, InputReader in, PrintWriter out) {
        long qty = in.nextLong();
        long months = in.nextLong();
        if (qty == 0) {
            out.println(0);
            return;
        }
        qty %= MODULO;
        long pow = pow(2, months + 1);
        qty = (qty * pow) % MODULO;
        long sub = (pow - 2 + MODULO) % MODULO * pow(2, MODULO - 2) % MODULO;
        qty = (qty - sub + MODULO) % MODULO;
        out.println(qty);
    }

    private long pow(long base, long power) {
        long result = 1;
        while (power > 0) {
            if ((power & 1) != 0) {
                result = (result * base) % MODULO;
            }
            base = (base * base) % MODULO;
            power >>>= 1;
        }
        return result;
    }
}
