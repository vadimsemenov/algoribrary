package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskE {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int rows = in.nextInt();
        int columns = in.nextInt();
        int rooks = in.nextInt();
        int queries = in.nextInt();
        List<Integer>[][] rooksHere = new List[2][];
        rooksHere[0] = new List[rows];
        rooksHere[1] = new List[columns];
        List<Integer>[][] endsHere = new List[2][];
        endsHere[0] = new List[rows];
        endsHere[1] = new List[columns];
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < endsHere[i].length; ++j) {
                rooksHere[i][j] = new ArrayList<>();
                endsHere[i][j] = new ArrayList<>();
            }
        }
        for (int i = 0; i < rooks; ++i) {
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1;
            rooksHere[0][x].add(y);
            rooksHere[1][y].add(x);
        }
        int[][][] area = new int[2][2][queries];
        for (int i = 0; i < queries; ++i) {
            for (int j = 0; j < 2; ++j) {
                for (int k = 0; k < 2; ++k) {
                    area[j][k][i] = in.nextInt() - 1;
                    if (j == 1) {
                        endsHere[k][area[j][k][i]].add(i);
                    }
                }
            }
        }
        boolean[] isProtected = new boolean[queries];
        for (int dir = 0; dir < 2; ++dir) {
            RMQ rmq = new RMQ(endsHere[dir ^ 1].length);
            for (int coordinate = 0; coordinate < endsHere[dir].length; ++coordinate) {
                for (int i : rooksHere[dir][coordinate]) {
                    rmq.update(i, coordinate);
                }
                for (int i : endsHere[dir][coordinate]) {
                    isProtected[i] |= rmq.query(area[0][dir ^ 1][i], area[1][dir ^ 1][i]) >= area[0][dir][i];
                }
            }
        }
        for (boolean yes : isProtected) {
            out.println(yes ? "YES" : "NO");
        }
    }

    private static class RMQ {
        private int[] data;
        private int offset;

        public RMQ(int capacity) {
            offset = Integer.highestOneBit(capacity);
            if ((capacity & capacity - 1) != 0) offset <<= 1;
            data = new int[offset << 1];
            Arrays.fill(data, -1);
        }

        public void update(int at, int val) {
            at += offset;
            data[at] = val;
            at >>>= 1;
            while (at > 0) {
                data[at] = Math.min(data[at << 1], data[(at << 1) | 1]);
                at >>>= 1;
            }
        }

        public int query(int from, int to) {
            from += offset;
            to += offset;
            int result = Integer.MAX_VALUE;
            while (from <= to) {
                if ((from & 1) == 1) {
                    result = Math.min(result, data[from]);
                    from++;
                }
                if ((to & 1) == 0) {
                    result = Math.min(result, data[to]);
                    to--;
                }
                from >>>= 1;
                to >>>= 1;
            }
            return result;
        }
    }
}
