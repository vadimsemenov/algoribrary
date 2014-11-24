package tasks;

import algoribrary.collections.intervaltree.fenwicktree.IntFenwickTree;
import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskD {
    int[] n, t;
    int counter;
    int inf;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        counter = in.nextInt();
        n = new int[] {in.nextInt(), in.nextInt(), in.nextInt()};
        t = new int[] {in.nextInt(), in.nextInt(), in.nextInt()};
        inf = (t[0] + t[1] + t[2]) * counter;
        tree = new FenwickTree(inf);
        for (int time = 0; ; ++time) {
            while (can(time) && counter > 0) {
                go(time);
                counter--;
            }
            if (counter == 0) {
                out.println(time + t[0] + t[1] + t[2]);
                return;
            }
        }
    }

    private void go(int time) {
        tree.update(time, 1);
        tree.update(time + t[0], -1);
        tree.update(time + t[0], 20000);
        tree.update(time + t[0] + t[1], -20000);
        tree.update(time + t[0] + t[1], 400000000);
        tree.update(time + t[0] + t[1] + t[2], -400000000);
    }

    FenwickTree tree;

    private boolean can(int time) {
        return (tree.query(time) % 20000) < n[0] &&
                (tree.query(time + t[0]) % 400000000) / 20000 < n[1] &&
                (tree.query(time + t[0] + t[1]) / 400000000) < n[2];
    }
}

class FenwickTree {
    long[] data;
    int size;

    FenwickTree(int size) {
        this.size = size;
        data = new long[size];
    }

    void update(int at, int by) {
        while (at < size) {
            data[at] += by;
            at |= at + 1;
        }
    }

    long query(int at) {
        long result = 0;
        while (at >= 0) {
            result += data[at];
            at = (at & at + 1) - 1;
        }
        return result;
    }
}