package task;

import algoribrary.io.InputReader;
import algoribrary.strings.StringUtils;

import java.io.PrintWriter;

public final class TaskE {
    private static final int MODULO = 998244353;

    public void solve(int __, InputReader in, PrintWriter out) {
        char[] number = in.next().toCharArray();
        char[] min = in.next().toCharArray();
        boolean zeroAllowed = min.length == 1 && min[0] == '0';
        char[] max = in.next().toCharArray();
        boolean[] lessMin = compareAll(number, min, true);
        boolean[] lessOrEqualMax = compareAll(number, max, false);
        FenwickTree dp = new FenwickTree(number.length + 1);
        dp.add(0, 1, 1);
        for (int i = 0; i < number.length; ++i) {
            int current = dp.get(i);
            if (current == 0) continue;
            if (number[i] == '0') {
                if (zeroAllowed) {
                    dp.add(i + 1, i + 2, current);
                }
            } else {
                int from = i + min.length;
                if (lessMin[i]) from++;
                int to = i + max.length;
                if (!lessOrEqualMax[i]) to--;
                if (from <= to) {
                    dp.add(from, to + 1, current);
                }
            }
        }
        out.println(dp.get(number.length));
    }

    private boolean[] compareAll(char[] number, char[] threshold, boolean strict) {
        char[] temp = new char[threshold.length + number.length + 1];
        System.arraycopy(threshold, 0, temp, 0, threshold.length);
        temp[threshold.length] = '#';
        System.arraycopy(number, 0, temp, threshold.length + 1, number.length);
        int[] z = StringUtils.buildZFunction(temp);
        int length = number.length;
        boolean[] result = new boolean[length];
        for (int i = 0; i < number.length; ++i) {
            int j = i + threshold.length + 1;
            result[i] = z[j] == threshold.length ? !strict :
                    i + threshold.length > length || number[i + z[j]] < threshold[z[j]];
        }
        return result;
    }

    static class FenwickTree {
        private final int[] tree;

        FenwickTree(int capacity) {
            tree = new int[capacity];
        }

        void add(int from, int delta) {
            int at = from;
            while (at < tree.length) {
                tree[at] = (tree[at] + delta) % MODULO;
                at |= at + 1;
            }
        }

        void add(int from, int to, int delta) {
            add(from, delta);
            add(to, MODULO - delta);
        }

        int get(int at) {
            int result = 0;
            while (at >= 0) {
                result = (result + tree[at]) % MODULO;
                at = (at & at + 1) - 1;
            }
            return result;
        }
    }
}
