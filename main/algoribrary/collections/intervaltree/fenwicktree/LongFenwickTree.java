package algoribrary.collections.intervaltree.fenwicktree;

/**
 * @author Vadim Semenov
 */
public class LongFenwickTree {
    private long[] dataAdd, dataMul;

    public LongFenwickTree(int size) {
        dataAdd = new long[size];
        dataMul = new long[size];
    }

    public void update(int from, int to, long delta) {
        internalUpdate(from, -delta * (from - 1), delta);
        internalUpdate(to + 1, delta * to, -delta);
    }

    private void internalUpdate(int at, long add, long mul) {
        while (at < dataAdd.length) {
            dataAdd[at] += add;
            dataMul[at] += mul;
            at |= at + 1;
        }
    }

    public long query(int from, int to) {
        return internalQuery(to) - internalQuery(from - 1);
    }

    private long internalQuery(int at) {
        int start = at;
        long add = 0;
        long mul = 0;
        while (at >= 0) {
            add += dataAdd[at];
            mul += dataMul[at];
            at = (at & at + 1) - 1;
        }
        return start * mul + add;
    }
}
