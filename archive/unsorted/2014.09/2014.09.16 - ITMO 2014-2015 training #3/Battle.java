package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class Battle {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        Man[] mans = new Man[counter];
        for (int i = 0; i < counter; ++i) {
            mans[i] = new Man(in.nextInt(), i + 1);
        }
        Arrays.sort(mans);
        if (counter % 3 == 0) {
            out.println(counter / 3);
            for (int i = 0; i < counter; ++i) {
                if (i % 3 == 0) {
                    out.print(3);
                }
            }
        }
    }

    static class Man implements Comparable<Man> {
        int time, idx;

        Man(int time, int idx) {
            this.time = time;
            this.idx = idx;
        }

        @Override
        public int compareTo(Man o) {
            return Integer.compare(time, o.time);
        }
    }
}
