package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class DifferenceAndProduct {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int difference = in.nextInt();
        int product = in.nextInt();
        int result = 0;
        if (difference < 0) {
        } else if (product == 0) {
            result = difference == 0 ? 1 : 4;
        } else {
            for (int a = 1; a * a <= Math.abs(product); ++a) {
                int b = product / a;
                if (a * b == product && Math.abs(b - a) == difference) {
                    result += Math.abs(a) == Math.abs(b) ? 2 : 4;
                }
            }
        }
        out.println(result);
    }
}
