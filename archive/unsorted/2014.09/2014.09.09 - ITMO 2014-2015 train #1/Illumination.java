package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class Illumination {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] array = new int[counter];
        for (int i = 0; i < counter; ++i) array[i] += in.nextInt();
        for (int i = 0; i < counter; ++i) array[i] -= in.nextInt();
        long[] sums = new long[counter];
        sums[0] = array[0];
        for (int i = 1; i < counter; ++i) sums[i] = sums[i - 1] + array[i];
        long[] sorted = Arrays.copyOf(sums, sums.length);
        Arrays.sort(sorted);
        int ptr = 0;
        for (int i = 1; i < counter; ++i) {
            if (sorted[i] != sorted[ptr]) {
                ptr++;
                sorted[ptr] = sorted[i];
            }
        }
        ptr++;
        BIT tree = new BIT(counter);
        long result = 0;
        for (int i = 0; i < counter; ++i) {
            if (sums[i] > 0) result++;
            int hash = Arrays.binarySearch(sorted, 0, ptr, sums[i]);
            result += tree.query(hash - 1);
            tree.update(hash, 1);
        }
        out.println(result);
    }

    static class BIT {
        int[] data;

        BIT(int size) {
            data = new int[size];
        }

        void update(int at, int val) {
            while (at < data.length) {
                data[at] += val;
                at |= at + 1;
            }
        }

        int query(int at) {
            int result = 0;
            while (at >= 0) {
                result += data[at];
                at = (at & at + 1) - 1;
            }
            return result;
        }
    }
}
