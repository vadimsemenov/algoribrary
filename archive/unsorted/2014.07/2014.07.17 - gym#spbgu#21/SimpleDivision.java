package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class SimpleDivision {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int x;
        while ((x = in.nextInt()) != 0) {
            List<Integer> list = new ArrayList<>();
            do list.add(x); while ((x = in.nextInt()) != 0);
            int res = 0;
            for (int i = 0; i < list.size(); i++)
                for (int j = i + 1; j < list.size(); j++)
                    res = gcd(res, Math.abs(list.get(i) - list.get(j)));
            out.println(res);
        }
    }

    private int gcd(int fst, int snd) {
        while (fst != 0) {
            int tmp = snd % fst;
            snd = fst;
            fst = tmp;
        }
        return snd;
    }
}
