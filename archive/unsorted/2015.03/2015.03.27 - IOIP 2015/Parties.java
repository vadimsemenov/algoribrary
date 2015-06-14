package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Parties {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        final int counter = in.nextInt();
        final int[] array = new int[counter];
        final Integer[] order = new Integer[counter];
        for (int i = 0; i < counter; ++i) {
            array[i] = in.nextInt();
            order[i] = i;
        }
        Arrays.sort(order, new Comparator<Integer>() {
            @Override
            public int compare(Integer first, Integer second) {
                return -Integer.compare(array[first], array[second]);
            }
        });
        boolean[] good = new boolean[counter];
        int cnt = 0;
        int sum = 0;
        for (int i : order) {
            if (cnt + 1 + sum + array[i] <= counter) {
                ++cnt;
                sum += array[i];
                good[i] = true;
            }
            if (cnt + sum == counter) {
                break;
            }
        }
        if (cnt + sum == counter) {
            out.println("YES");
            for (boolean g : good) {
                out.print(g ? "1 " : "2 ");
            }
        } else {
            out.println("NO");
        }
    }
}
