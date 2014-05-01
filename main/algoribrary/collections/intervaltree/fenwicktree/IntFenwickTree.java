package algoribrary.collections.intervaltree.fenwicktree;

/**
 * @author Vadim Semenov
 */
public class IntFenwickTree {
    private int[] dataAdd, dataMul;

    public IntFenwickTree(int size) {
        dataAdd = new int[size];
        dataMul = new int[size];
    }

    public void update(int from, int to, int delta) {
        internalUpdate(from, -delta * (from - 1), delta);
        internalUpdate(to + 1, delta * to, -delta);
    }

    private void internalUpdate(int at, int add, int mul) {
        while (at < dataAdd.length) {
            dataAdd[at] += add;
            dataMul[at] += mul;
            at |= at + 1;
        }
    }

    public int query(int from, int to) {
        return query(to) - query(from - 1);
    }

    public int query(int at) {
        int start = at;
        int add = 0;
        int mul = 0;
        while (at >= 0) {
            add += dataAdd[at];
            mul += dataMul[at];
            at = (at & at + 1) - 1;
        }
        return start * mul + add;
    }
}
