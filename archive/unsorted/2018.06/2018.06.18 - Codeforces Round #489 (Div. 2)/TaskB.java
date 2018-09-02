package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int min = in.nextInt();
        int max = in.nextInt();
        int gcd = in.nextInt();
        int lcm = in.nextInt();
        if (lcm % gcd != 0) {
            out.println(0);
            return;
        }
        int quote = lcm / gcd;
        List<Integer> rest = new ArrayList<>();
        for (int d = 2; d * d <= quote; ++d) {
            if (quote % d == 0) {
                int power = 1;
                while (quote % d == 0) {
                    quote /= d;
                    power *= d;
                }
                rest.add(power);
            }
        }
        if (quote != 1) {
            rest.add(quote);
        }
        int answer = 0;
        for (int mask = 0; mask < (1 << rest.size()); ++mask) {
            int fst = 1;
            for (int bit = 0; bit < rest.size(); ++bit) {
                if (((mask >>> bit) & 1) != 0) {
                    fst *= rest.get(bit);
                }
            }
            int snd = lcm / fst;
            fst *= gcd;
            if (min <= fst && fst <= max && min <= snd && snd <= max) {
                answer++;
            }
        }
        out.println(answer);
    }
}
