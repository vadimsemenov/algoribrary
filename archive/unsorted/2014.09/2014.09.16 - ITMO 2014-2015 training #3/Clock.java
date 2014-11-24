package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Clock {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int d1 = in.nextInt();
        int d2 = in.nextInt();
        if (check(d1, d2)) {
            print(d1, d2, out);
        } else if (check(d2, d1)) {
            print(d2, d1, out);
        } else {
            out.println("No solution");
        }
    }

    private void print(int hh, int mm, PrintWriter out) {
        hh /= 30; mm /= 6;
        if (hh < 10) out.print("0" + hh);
        else out.print(hh);
        out.print(":");
        if (mm < 10) out.print("0" + mm);
        else out.print(mm);
    }

    private boolean check(int hh, int mm) {
        if (mm % 12 != 0) return false;
        if (hh % 30 != mm / 12) return false;
        return true;
    }
}

// 5:60 == 30:360