package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Divisors {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int x = in.nextInt();
        List<String> list = new ArrayList<>();
        for (int d = 2; d <= x; ++d) {
            if (x % d == 0) {
                list.add(String.valueOf(d));
                while (x % d == 0) x /= d;
            }
        }
        Collections.sort(list);
        out.println(list.get(0));
    }
}
