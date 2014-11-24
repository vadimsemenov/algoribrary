package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int number = in.nextInt();
        for (int i = 4; i < number; ++i) {
            int j = number - i;
            if (notPrime[i] && notPrime[j]) {
                out.println(i + " " + j);
                return;
            }
        }
        throw new RuntimeException();
    }

    static boolean[] notPrime = new boolean[1_000_000];
    static {
        notPrime[0] = notPrime[1] = true;
        for (int p = 2; p < notPrime.length; ++p) {
            if (!notPrime[p]) {
                int d = p * p;
                if (d >= notPrime.length) break;
                for (; d < notPrime.length; d += p) {
                    notPrime[d] = true;
                }
            }
        }
    }
}
