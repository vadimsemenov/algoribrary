package algoribrary.collections.intervaltree;

import java.util.Arrays;

/**
 * @author Vadim Semenov
 */
public abstract class SimpleIntIntervalTree {
    public final int INITIAL_VALUE;

    private final int size;
    private int[] data;

    protected SimpleIntIntervalTree(int initialValue, int size) {
        INITIAL_VALUE = initialValue;
        this.size = Integer.highestOneBit(size) << ((size & size - 1) == 0 ? 0 : 1);
        data = new int[this.size << 1];
        Arrays.fill(data, INITIAL_VALUE);
    }

    protected abstract int modifyOperation(int value, int delta);

    protected abstract int joinOperation(int leftValue, int rightValue);

    public void modify(int index, int value) {
        index += size;
        data[index] = modifyOperation(data[index], value);
        index >>= 1;
        while (index > 0) {
            data[index] = joinOperation(data[index << 1], data[(index << 1) | 1]);
            index >>= 1;
        }
    }

    public int query(int from, int to) {
        from += size;
        to += size;
        int left = INITIAL_VALUE;
        int right = INITIAL_VALUE;
        while (from <= to) {
            if ((from & 1) == 1) {
                left = joinOperation(left, data[from]);
                from++;
            }
            if ((to & 1) == 0) {
                right = joinOperation(data[to], right);
                to--;
            }
            from >>= 1;
            to >>= 1;
        }
        return joinOperation(left, right);
    }
}
