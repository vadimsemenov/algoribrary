package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskK {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        Prize[] prizes = new Prize[counter];
        for (int i = 0; i < counter; ++i) prizes[i] = new Prize(in.nextInt(), i);
        Prize[] inOrder = prizes.clone();
        Arrays.sort(prizes);
        long first = 0;
        long second = 0;
        boolean[] used = new boolean[counter];
        boolean jack = true;
        for (int i = 0; i < counter; ++i) {
            if (jack) {

            }
        }
        out.println(second + " " + first);
    }

    static class Prize implements Comparable<Prize> {
        int cost, idx;

        Prize(int cost, int idx) {
            this.cost = cost;
            this.idx = idx;
        }

        @Override
        public int compareTo(Prize o) {
            int result = -Integer.compare(cost, o.cost);
            if (result == 0) return Integer.compare(idx, o.idx);
            return result;
        }
    }
}
