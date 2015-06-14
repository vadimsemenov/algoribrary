package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class TaskG {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[] cash = new int[counter];
        Integer[] order = new Integer[counter];
        for (int i = counter - 1; i >= 0; --i) {
            cash[i] = in.nextInt();
            order[i] = i;
        }
        Arrays.sort(order, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -Integer.compare(cash[o1],cash[o2]);
            }
        });
        int[] ad = new int[counter];
        for (int i = 0; i + 1 < counter; ++i) {
            int j = i + 1;

        }
    }
}
