package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        // int k = in.nextInt();
        for (int k = 4; k < 100; ++k) {
            Set<Pair> result = solve(k);

            int res = 0;
            for (int a = 1; a <= 3 * k * k; ++a) {
                for (int b = a; b <= 3 * k * k; ++b) {
                    if ((a + b) * k == a * b) {
                        System.err.println(a + " " + b);
                        res++;
                        if (a != b) res++;
                    }
                }
            }
            if (res == result.size()) continue;
            out.println(k);
            out.println(res);
            out.println(result.size());
            for (Pair pair : result) {
                out.println(pair);
            }
            return;
        }
    }

    private Set<Pair> solve(int k) {
        Set<Pair> result = new HashSet<>();
        for (int d = 1; d * d <= k; ++d) {
            if (k % d == 0) {
                int e = k / d;
                int g = gcd(d, e);

                long fst = d;
                long snd = e / g;
                long sum = fst + snd;
                if ((fst + snd) * k == fst * snd * sum) {
                    result.add(new Pair(fst * sum, snd * sum));
                    result.add(new Pair(snd * sum, fst * sum));
                }

                fst = d / g;
                snd = e;
                sum = fst + snd;
                if ((fst + snd) * k == fst * snd * sum) {
                    result.add(new Pair(fst * sum, snd * sum));
                    result.add(new Pair(snd * sum, fst * sum));
                }

                fst = d / g;
                snd = e / g;
                sum = (fst + snd) * g;
                if ((fst + snd) * k == fst * snd * sum) {
                    result.add(new Pair(fst * sum, snd * sum));
                    result.add(new Pair(snd * sum, fst * sum));
                }
            }
        }
        result.add(new Pair(2L * k, 2L * k));
        return result;
    }

    class Pair {
        long x, y;

        @Override
        public String toString() {
            return x + " " + y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            Pair pair = (Pair) o;

            if (x != pair.x) return false;
            if (y != pair.y) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = (int) (x ^ (x >>> 32));
            result = 31 * result + (int) (y ^ (y >>> 32));
            return result;
        }

        Pair(long y, long x) {

            this.y = y;
            this.x = x;
        }
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }
}

/*
(a + b) * k == ab
d = (a, b)
d * (a + b) * k = d * d * a * b
(a + b) * k = d * a * b  // k % a == 0, k % b == 0, d == a + b;

(x + y) * z * x * y == d * x * y
2a * k == a^2

a = x * z * (x + y)
b = y * z * (x + y)
a + b = (x + y)^2 * z
a * b = z^2 * x * y * (x + y)^2
z * x * y == 1;
 */