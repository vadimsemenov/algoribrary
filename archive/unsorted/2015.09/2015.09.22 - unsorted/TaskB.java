package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int total = in.nextInt();
        int limit = in.nextInt();
        int qty = in.nextInt();
        int cash = in.nextInt();
        int[] array = new int[total];
        for (int i = 0; i < total; ++i) {
            array[i] = in.nextInt();
        }
        boolean[] good = new boolean[total];
        for (int i = 0; i < qty; ++i) {
            good[in.nextInt() - 1] = true;
        }
        int[] other = new int[total - qty];
        int ptr = 0;
        for (int i = 0; i < total; ++i) if (!good[i]) {
            other[ptr++] = array[i];
        }
        Arrays.sort(other);
        for (int i = 0; i + i < other.length; ++i) {
            int tmp = other[i];
            other[i] = other[other.length - 1 - i];
            other[other.length - 1 - i] = tmp;
        }
        int left = 0;
        int right = Integer.MAX_VALUE;
        int foo = -1;
        while (right > left + 1) {
            int middle = (left + right) >>> 1;
            int cnt = 0;
            int have = cash;
            for (int i : other) {
                if (i < middle) {
                    int need = middle - i;
                    if (have >= need) {
                        have -= need;
                    } else {
                        break;
                    }
                }
                ++cnt;
            }
            if (cnt >= limit - qty + 1) {
                foo = cnt;
                left = middle;
            } else {
                right = middle;
            }
        }
        int min = left;
        if (foo + qty > limit) ++min;
        long answer = 0;
        for (int i = 0; i < total; ++i) {
            if (good[i] && array[i] < min) {
                answer += min - array[i];
            }
        }
        out.println(answer);
    }
}
