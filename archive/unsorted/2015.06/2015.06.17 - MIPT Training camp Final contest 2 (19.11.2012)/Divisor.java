package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Divisor {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int tests = in.nextInt();
        outer: for (int test = 0; test < tests; ++test) {
            int number = in.nextInt();
            int n = number;
            int divisorNo = in.nextInt();
            int divisorsQty = 1;
            {
                for (int divisor = 2; divisor * divisor <= n; ++divisor) {
                    if (n % divisor == 0) {
                        int cnt = 0;
                        while (n % divisor == 0) {
                            ++cnt;
                            n /= divisor;
                        }
                        divisorsQty *= cnt + 1;
                    }
                }
                if (n > 1) divisorsQty *= 2;
            }
            int no = 0;
            for (int divisor = 1; ; ++divisor) {
                if (number % divisor == 0) {
                    ++no;
                    if (no == divisorNo) {
                        out.println(divisor);
                        continue outer;
                    }
                    if (divisorsQty - no + 1 == divisorNo) {
                        out.println(number / divisor);
                        continue outer;
                    }
                }
            }
        }
    }
}
