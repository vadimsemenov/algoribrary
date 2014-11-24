package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class TaskD {

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] from = new int[counter];
        int[] gcd = new int[counter];
        int top = 0;
        Map<Integer, Long> cnt = new HashMap<>();
        for (int i = 0; i < counter; ++i) {
            int a = in.nextInt();
            gcd[top] = a;
            from[top] = i;
            top++;
            int newTop = 0;
            for (int j = 0; j < top; ++j) {
                gcd[j] = gcd(a, gcd[j]);
                if (j == 0) {
                    from[0] = 0;
                    newTop++;
                } else if (gcd[j] != gcd[j - 1]){
                    gcd[newTop] = gcd[j];
                    from[newTop] = from[j];
                    newTop++;
                }
            }
            top = newTop;
            for (int j = 0; j < top; ++j) {
                Long count = cnt.get(gcd[j]);
                if (count == null) {
                    count = 0L;
                }
                count += (j == top - 1 ? i : (from[j + 1] - 1)) - from[j] + 1;
                cnt.put(gcd[j], count);
            }
        }
        int queries = in.nextInt();
        for (int i = 0; i < queries; ++i) {
            int g = in.nextInt();
            Long result = cnt.get(g);
            if (result == null) {
                result = 0L;
            }
            out.println(result);
        }
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}
