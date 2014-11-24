package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskC {
    private static final int MODULO = 1_000_000_007;

    private int[] lastChange;
    private int[] lastShift;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        String input = in.next();
        int queries = in.nextInt();
        int[][] nums = new int[queries + 1][];
        nums[0] = new int[input.length()];
        int[] digit = new int[queries + 1];
        for (int i = 0; i < input.length(); ++i) nums[0][i] = input.charAt(i) - '0';
        for (int q = 1; q <= queries; ++q) {
            String query = in.next();
            digit[q] = query.charAt(0) - '0';
            nums[q] = new int[query.length() - 3];
            for (int i = 3; i < query.length(); ++i) nums[q][i - 3] = query.charAt(i) - '0';
        }
        lastChange = new int[10];
        for (int i = 0; i < 10; ++i) lastChange[i] = i;
        lastShift = new int[10];
        Arrays.fill(lastShift, 1);
        for (int q = queries; q > 0; --q) {
            calculate(nums[q]);
            lastChange[digit[q]] = SUM;
            lastShift[digit[q]] = SHIFT;
        }
        calculate(nums[0]);
        out.println(SUM);
    }

    int SHIFT, SUM;
    private void calculate(int[] number) {
        long result = 0;
        SHIFT = 0;
        for (int i : number) {
            SHIFT += lastShift[i];
            if (SHIFT >= MODULO - 1) SHIFT -= MODULO - 1;
            result *= pow(10, lastShift[i]);
            result += lastChange[i];
            result %= MODULO;
        }
        SUM = (int) result;
    }

    private int pow(long base, int power) {
        long result = 1;
        while (power > 0) {
            if ((power & 1) == 1) {
                result *= base;
                result %= MODULO;
            }
            power >>= 1;
            base *= base;
            base %= MODULO;
        }
        return (int) result;
    }
}
