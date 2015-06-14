package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Accounts {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int hints = in.nextInt();
        int[][] account = new int[hints][];
        long[][] sum = new long[hints][];
        List<Integer>[] hint = new List[counter];
        for (int i = 0; i < counter; ++i) {
            hint[i] = new ArrayList<>();
        }
        long[] upper = new long[counter];
        long[] lower = new long[counter];
        long[] daysUpper = new long[hints];
        long[] daysLower = new long[hints];
        long[] daysGCD = new long[counter];
        for (int i = 0; i < hints; ++i) {
            int cnt = in.nextInt();
            account[i] = new int[cnt];
            sum[i] = new long[cnt];
            long day = 0;
            for (int j = 0; j < cnt; ++j) {
                account[i][j] = in.nextInt() - 1;
                sum[i][j] = in.nextLong();
                day = gcd(sum[i][j], day);
                upper[account[i][j]] = gcd(sum[i][j], upper[account[i][j]]);
                hint[account[i][j]].add(i);
            }
            daysUpper[i] = day;
            for (int j = 0; j < cnt; ++j) {
                daysGCD[account[i][j]] = gcd(day, daysGCD[account[i][j]]);
            }
        }
        DSU dsu = new DSU(upper);
        for (int i = 0; i < hints; ++i) {
            int cnt = account[i].length;
            for (int j = 0; j < cnt; ++j) {
                long a = sum[i][j] / daysUpper[i];
                lower[j] = a / gcd(a, lower[j]) * lower[j];
                long b = sum[i][j] / upper[j];
                daysLower[i] = b / gcd(b, daysLower[i]) * daysLower[i];
                if (j > 0) {
                    dsu.unite(account[i][j], account[i][j - 1]);
                }
            }
        }
        int[] queue = new int[hints];
        int head = 0;
        int tail = 0;
        for (int i = 0; i < counter; ++i) {

        }
    }

    private static long gcd(long a, long b) {
        while (b != 0) {
            long tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    private static class DSU {
        private boolean[] ready;
        private int[] parent;
        private long[] gcd;
        private int[] size;

        public DSU(long[] gcds) {
            int capacity = gcds.length;
            gcd = new long[capacity];
            ready = new boolean[capacity];
            parent = new int[capacity];
            size = new int[capacity];
            clear();
            System.arraycopy(gcds, 0, gcd, 0, capacity);
        }

        public void clear() {
            for (int i = 0; i < parent.length; ++i) {
                parent[i] = i;
            }
            Arrays.fill(ready, false);
            Arrays.fill(gcd, 0);
            Arrays.fill(size, 1);
        }

        public int get(int v) {
            return v == parent[v] ? v : (parent[v] = get(parent[v]));
        }

        public long getGCD(int v) {
            return gcd[get(v)];
        }

        public boolean unite(int a, int b) {
            a = get(a);
            b = get(b);
            if (a == b) {
                return false;
            }
            if (size[a] < size[b]) {
                int tmp = a; a = b; b = tmp;
            }
            parent[b] = a;
            size[a] += size[b];
            ready[a] |= ready[b];
            gcd[a] = gcd(gcd[a], gcd[b]);
            return true;
        }

        public void setReady(int v) {
            ready[get(v)] = true;
        }

        public boolean isReady(int v) {
            return ready[get(v)];
        }
    }
}
