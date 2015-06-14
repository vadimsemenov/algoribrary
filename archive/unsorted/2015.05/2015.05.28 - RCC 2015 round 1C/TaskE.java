package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskE {
    private static final int MAX = 1000;
    private static final int[] sqrt = new int[MAX * (MAX - 1) / 2 + 1];
    static {
        Arrays.fill(sqrt, -1);
        for (int i = 1; i <= MAX; ++i) {
            sqrt[i * (i - 1) / 2] = i;
        }
    }

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[] xs = new int[counter];
        int[] ys = new int[counter];
        for (int i = 0; i < counter; ++i) {
            xs[i] = in.nextInt();
            ys[i] = in.nextInt();
        }
        Map<Pair, List<Long>> map = new HashMap<>();
        for (int i = 0; i < counter; ++i) {
            for (int j = i + 1; j < counter; ++j) {
                int a = ys[i] - ys[j];
                int b = xs[i] - xs[j]; b = -b;

                if (a < 0 || (a == 0 && b < 0)) {
                    a = -a;
                    b = -b;
                }

                int d = gcd(a, b);
                a /= d;
                b /= d;

                Pair key = new Pair(a, b);
                List<Long> list = map.get(key);
                if (list == null) {
                    list = new ArrayList<>();
                    map.put(key, list);
                }
                long c = a * 1L * xs[i] + b * 1L * ys[i];
                list.add(c);
            }
        }
        final int[] answer = {Math.min(2, counter)};
        map.forEach((foo, list) -> {
            Collections.sort(list);
            int max = -1;
            int secondMax = -1;
            for (int i = 0; i < list.size(); ++i) {
                int j = i + 1;
                while (j < list.size() && list.get(j).equals(list.get(i))) {
                    ++j;
                }
                int cnt = j - i;
                cnt = sqrt[cnt];
                if (cnt > max) {
                    secondMax = max;
                    max = cnt;
                } else if (cnt > secondMax) {
                    secondMax = cnt;
                }
                i = j - 1;
            }
            if (secondMax > 0) {
                answer[0] = Math.max(answer[0], max + secondMax);
            } else {
                answer[0] = Math.max(answer[0], max + (max < counter ? 1 : 0));
            }
        });
        out.println(answer[0]);
    }

    private static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int c = a % b;
            a = b;
            b = c;
        }
        return a;
    }

    static class Pair {
        final int a, b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "a=" + a +
                    ", b=" + b +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;

            Pair pair = (Pair) o;

            if (a != pair.a) return false;
            return b == pair.b;
        }

        @Override
        public int hashCode() {
            int result = a;
            result = 73 * result + b;
            return result;
        }
    }
}
