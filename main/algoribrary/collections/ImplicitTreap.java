package algoribrary.collections;

/**
 * Created by vadim on 19-03-2014.
 */
public class ImplicitTreap {
    private ImplicitTreap left, right;
    private int size, priority;
    private int value, subtreeValue;

    public ImplicitTreap(int priority, int value, ImplicitTreap left, ImplicitTreap right) {
        this.priority = priority;
        this.left = left;
        this.right = right;
        size = 1;
        if (left != null) {
            size += left.size;
        }
        if (right != null) {
            size += right.size;
        }
        this.value = value;
        recalc();
    }

    public static ImplicitTreap merge(ImplicitTreap first, ImplicitTreap second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        if (first.priority > second.priority) {
            return new ImplicitTreap(first.priority, first.value, first.left, merge(first.right, second));
        }
        return new ImplicitTreap(second.priority, second.value, merge(first, second.left), second.right);
    }

    // (-inf, index), [index, +inf)
    public TreapPair split(int index) {
        int leftSize = left == null ? 0 : left.size;
        TreapPair result = null;
        if (leftSize > index) {
            TreapPair pair = left.split(index);
            result = new TreapPair(pair.first, new ImplicitTreap(priority, value, pair.second, right));
        } else if (leftSize == index) {
            result = new TreapPair(left, new ImplicitTreap(priority, value, null, right));
        } else {
            TreapPair pair = right.split(index - leftSize - 1);
            result = new TreapPair(new ImplicitTreap(priority, value, left, pair.first), pair.second);
        }
        return result;
    }

    public ImplicitTreap add(int index, int value, int priority) {
        int leftSize = left == null ? 0 : left.size;
        int rightSize = right == null ? 0 : right.size;
        if (index == 0) {
            return merge(new ImplicitTreap(priority, value, null, null), this);
        }
        if (index == size) {
            return merge(this, new ImplicitTreap(priority, value, null, null));
        }
        TreapPair pair = split(index);

        return pair.first;
    }

    private void recalc() {
        subtreeValue = value;
        if (left != null) {
            subtreeValue = Math.min(subtreeValue, left.subtreeValue);
        }
        if (right != null) {
            subtreeValue = Math.min(subtreeValue, right.subtreeValue);
        }
    }

    private static class TreapPair {
        ImplicitTreap first, second;

        private TreapPair(ImplicitTreap first, ImplicitTreap second) {
            this.first = first;
            this.second = second;
        }
    }
}
