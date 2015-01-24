package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class NewYearsResolution {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        out.print("Case #" + testNumber + ": ");
        int P = in.nextInt();
        int C = in.nextInt();
        int F = in.nextInt();
        int counter = in.nextInt();
        int[] p = new int[counter];
        int[] c = new int[counter];
        int[] f = new int[counter];
        for (int i = 0; i < counter; ++i) {
            p[i] = in.nextInt();
            c[i] = in.nextInt();
            f[i] = in.nextInt();
        }
        int[] pp = new int[1 << counter];
        int[] cc = new int[1 << counter];
        int[] ff = new int[1 << counter];
        for (int mask = 1; mask < (1 << counter); ++mask) {
            int bit = Integer.bitCount(Integer.highestOneBit(mask) - 1);
            assert bit < counter : mask + " " + bit;
            pp[mask] = p[bit] + pp[mask ^ (1 << bit)];
            cc[mask] = c[bit] + cc[mask ^ (1 << bit)];
            ff[mask] = f[bit] + ff[mask ^ (1 << bit)];
        }
        for (int mask = 0; mask < (1 << counter); ++mask) {
            if (pp[mask] == P && cc[mask] == C && ff[mask] == F) {
                out.println("yes");
                System.err.println("yes");
                return;
            }
        }
        out.println("no");
        System.err.println("no");
    }
}
