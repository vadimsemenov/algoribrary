package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int divisor = in.nextInt();
        int[] array = in.nextIntArray(count);
        int[][] mods = new int[11][count];
        int[] sorted = new int[count * mods.length];
        int ptr = 0;
        long mul = 1;
        for (int power = 0; power < mods.length; ++power, mul = mul * 10 % divisor) {
            for (int i = 0; i < count; ++i) {
                mods[power][i] = (int) (mul * array[i] % divisor);
                sorted[ptr++] = mods[power][i];
            }
        }
        Arrays.sort(sorted);
        ptr = 1;
        for (int i = 1; i < sorted.length; ++i) {
            if (sorted[i] != sorted[i - 1]) {
                sorted[ptr++] = sorted[i];
            }
        }
        int[][] qty = new int[mods.length][ptr];
        for (int power = 0; power < mods.length; ++power) {
            for (int i = 0; i < count; ++i) {
                qty[power][Arrays.binarySearch(sorted, 0, ptr, mods[power][i])]++;
            }
        }
        long answer = 0;
        for (int i = 0; i < count; i++) {
            int need = (divisor - mods[0][i]) % divisor;
            int needIdx = Arrays.binarySearch(sorted, 0, ptr, need);
            if (needIdx < 0) continue;
            int power = 0;
            int number = array[i];
            long test = number;
            while (number > 0) {
                power++;
                test = test * 10 % divisor;
                number /= 10;
            }
            answer += qty[power][needIdx];
            if (test == need) {
                answer -= 1;
            }
        }
        out.println(answer);
    }
}
