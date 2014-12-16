package algoribrary.collections.intervaltree.fenwicktree;

import java.util.Arrays;

/**
 * Created by vadim on 15/12/14.
 * <p/>
 * zero based array
 * from -- inclusive, to -- exclusive
 */
public abstract class SegmentFenwickTree<T> {
    public final T NEUTRAL_VALUE;
    private T[] data; /* data[i] = join a[i], i in (pred(i); i] */
    private T[] aux;  /* aux[i]  = join a[i], i in (i; succ(i)] */

    public SegmentFenwickTree(final T NEUTRAL_VALUE, final int size) {
        this.NEUTRAL_VALUE = NEUTRAL_VALUE;
        data = (T[]) new Object[size];
        Arrays.fill(data, NEUTRAL_VALUE);
        aux = (T[]) new Object[size];
        Arrays.fill(aux, NEUTRAL_VALUE);
    }

    public abstract T join(T leftValue, T rightValue);

    public void update(int at, T val) {
        int i = at;
        while (i < data.length) {
            data[i] = join(data[i], val);
            i |= i + 1;
        }
        i = at - 1;
        while (i >= 0) {
            aux[i] = join(aux[i], val);
            i = (i & i + 1) - 1;
        }
    }

    public T query(int from, int to) {
        T result = NEUTRAL_VALUE;
        int i = to - 1;
        while (i >= 0 && (i & i + 1) >= from) {
            result = join(result, data[i]);
            i = (i & i + 1) - 1;
        }
        int j = from - 1;
        while (j >= 0 && (j | j + 1) <= i) {
            result = join(result, aux[j]);
            j |= j + 1;
        }
        return result;
    }

    public void clear() {
        Arrays.fill(data, NEUTRAL_VALUE);
        Arrays.fill(aux, NEUTRAL_VALUE);
    }
}
