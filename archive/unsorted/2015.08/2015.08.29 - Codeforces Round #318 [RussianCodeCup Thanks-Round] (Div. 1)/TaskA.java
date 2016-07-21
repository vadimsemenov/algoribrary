package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[] array = in.nextIntArray(counter);
        int gcd = 0;
        for (int i : array) {
            gcd = gcd(i, gcd);
        }
        for (int num : array) {
            num /= gcd;
            while (num % 2 == 0) num /= 2;
            while (num % 3 == 0) num /= 3;
            if (num != 1) {
                out.println("No");
                return;
            }
        }
        out.println("Yes");
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int d = a % b;
            a = b;
            b = d;
        }
        return a;
    }
}
