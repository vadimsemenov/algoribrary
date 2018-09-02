package tasks;

import algoribrary.collections.intervaltree.fenwicktree.IntFenwickTree;
import algoribrary.collections.intervaltree.fenwicktree.LongFenwickTree;
import algoribrary.collections.intervaltree.fenwicktree.SimpleIntFenwickTree;
import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

public final class TaskE {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int queries = in.nextInt();
        //noinspection unchecked
        NavigableSet<Long>[] sets = new NavigableSet[Integer.SIZE + 1];
        for (int i = 0; i < sets.length; ++i) {
            sets[i] = new TreeSet<>();
        }
        int[] powers = in.nextIntArray(counter);
        FenwickTree tree = new FenwickTree(counter);
        for (int i = 0; i < counter; ++i) {
            tree.update(i, powers[i]);
            sets[Integer.numberOfLeadingZeros(powers[i])].add(encode(i, powers[i]));
        }
        for (int query = 0; query < queries; ++query) {
            int shamanPosition = -1;
            int position = in.nextInt() - 1;
            int newPower = in.nextInt();
            int oldPower = powers[position];
            powers[position] = newPower;
            sets[Integer.numberOfLeadingZeros(oldPower)].remove(encode(position, oldPower));
            sets[Integer.numberOfLeadingZeros(newPower)].add(encode(position, newPower));
            tree.update(position, newPower - oldPower);
            outer: for (NavigableSet<Long> set : sets) {
                if (set.isEmpty()) continue;
                Long code = set.first();
                for (int it = 0; it < Math.min(set.size(), 2); it++, code = set.higher(code)) {
                    int hisIdx = position(code);
                    int hisPower = power(code);
                    long prefixSum = tree.get(hisIdx);
                    if (2 * hisPower == prefixSum) {
                        shamanPosition = hisIdx + 1;
                        break outer;
                    }
                }
            }
            out.println(shamanPosition);
        }
    }

    private static long encode(int position, int power) {
        return ((long) position << 32) | power;
    }

    private static int position(long code) {
        return (int) (code >>> 32);
    }

    private static int power(long code) {
        return (int) (code & 0xFFFFFFFFL);
    }

    private static class FenwickTree {
        final long[] data;

        FenwickTree(int size) {
            data = new long[size];
        }

        void update(int at, int delta) {
            while (at < data.length) {
                data[at] += delta;
                at |= at + 1;
            }
        }

        long get(int at) {
            long sum = 0;
            while (at >= 0) {
                sum += data[at];
                at = (at & at + 1) - 1;
            }
            return sum;
        }
    }
}
