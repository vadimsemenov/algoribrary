package tasks;

import algoribrary.io.InputReader;
import algoribrary.misc.ArrayUtils;

import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public class TaskA {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int n = in.nextInt();
        int[] a = new int[n];
        int[] gcd = new int[n * n];
        Map<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < gcd.length; ++i) {
            gcd[i] = in.nextInt();
            Integer cnt = map.get(gcd[i]);
            if (cnt == null) {
                cnt = 0;
            }
            ++cnt;
            map.put(gcd[i], cnt);
        }
        ArrayUtils.sort(gcd);
        int ptr = n - 1;
        for (int i = gcd.length - 1; i >= 0; --i) {
            if (map.get(gcd[i]) == 0) continue;
            a[ptr] = gcd[i];
            map.put(a[ptr], map.get(a[ptr]) - 1);
            for (int j = ptr + 1; j < a.length; ++j) {
                int foo = gcd(a[ptr], a[j]);
                map.put(foo, map.get(foo) - 2);
            }
            --ptr;
        }
        if (ptr != -1) throw new AssertionError(ptr);
        for (int i : a) {
            out.print(i + " ");
        }
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int c = a % b;
            a = b;
            b = c;
        }
        return a;
    }
}
