package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

public class Numbereater {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[] array = new int[counter];
        for (int i = 0; i < counter; ++i) array[i] = in.nextInt();
        Set<BitSet> total = new HashSet<>();
        BitSet set = new BitSet(500);
        for (int i = 0; i < counter; ++i) {
            set.clear();
            for (int j = i; j < counter; ++j) {
                set.set(array[j]);
                total.add((BitSet) set.clone());
            }
        }
        out.println(total.size());
    }
}
