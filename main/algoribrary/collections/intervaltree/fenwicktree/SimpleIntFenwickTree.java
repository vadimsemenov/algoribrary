package algoribrary.collections.intervaltree.fenwicktree;

/**
 * @author Vadim Semenov
 */
public class SimpleIntFenwickTree {
    private int[] data;

    public SimpleIntFenwickTree(int size) {
        data = new int[size];
    }

    public void update(int at, int delta) {
        while (at < data.length) {
            data[at] += delta;
            at |= at + 1;
        }
    }

    public int query(int from, int to) {
        return query(to) - query(from - 1);
    }

    public int query(int at) {
        int result = 0;
        while (at >= 0) {
            result += data[at];
            at = (at & at + 1) - 1;
        }
        return result;
    }
}
