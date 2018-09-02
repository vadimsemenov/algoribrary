package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class RoundingError {
    public void solve(int testCase, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int languages = in.nextInt();
        int[] qty = in.nextIntArray(languages);
        int[] result = new int[counter + 1];
        for (int i = 0; i <= counter; ++i) {
            result[i] = (int) Math.round(i * 100. / counter);
        }
        int[] next = new int[counter + 1];
        next[counter] = 0;
        for (int i = counter - 1; i >= 0; --i) {
            if (result[i] != result[i + 1]) {
                next[i] = 1;
            } else {
                next[i] = 1 + next[i + 1];
            }
        }

    }

    static class Item implements Comparable<Item> {
        final int ad;
        final int diff;

        Item(int ad, int diff) {
            this.ad = ad;
            this.diff = diff;
        }

        @Override
        public int compareTo(Item o) {
            return 0; // TODO
        }
    }
}
