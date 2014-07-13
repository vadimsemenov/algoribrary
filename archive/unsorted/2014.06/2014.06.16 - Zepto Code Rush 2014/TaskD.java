package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class TaskD {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int need = in.nextInt();
        final int[] one = new int[counter];
        final int[] two = new int[counter];
        for (int i = 0; i < counter; i++) {
            one[i] = in.nextInt();
            two[i] = in.nextInt();
        }
        Integer[] order = new Integer[counter];
        Arrays.sort(order, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(two[o1], two[o2]);
            }
        });
        int[][] pairs = new int[2 * counter][];
        for (int i = 0; i < counter; i++) {
            pairs[2 * i] = new int[]{ one[i], i };
            pairs[2 * i + 1] = new int[]{ two[i] - one[i], -i };
        }
        Arrays.sort(pairs, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        });
        long answer = 0;
        long sum = 0;
        FenwickTree position = new FenwickTree(2 * counter);
        FenwickTree sums = new FenwickTree(2 * counter);
        for (int i = 0; i < 2 * counter; i++) {
            position.update(i, 1);
            sums.update(i, pairs[i][0]);
        }
        for (int idx : order) {

        }
    }

    class FenwickTree {
        private long[] data;

        FenwickTree(int size) {
            data = new long[size];
        }

        public void update(int at, int value) {
            for (int i = at; i < data.length; i |= i + 1)
                data[i] += value;
        }

        public long query(int at) {
            long result = 0;
            for (int i = at; i >= 0; i = (i & i + 1) - 1)
                result += data[i];
            return result;
        }
    }
}
