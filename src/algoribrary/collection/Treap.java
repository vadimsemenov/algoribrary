package algoribrary.collection;

/**
 * Created by vadim on 15 Mar 2014.
 */
public class Treap {
    private Treap left, right;
    private int key, priority;

    public Treap(int key, int priority, Treap left, Treap right) {
        this.key = key;
        this.priority = priority;
        this.left = left;
        this.right = right;
    }

    public Treap merge(Treap first, Treap second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        Treap result;
        if (first.priority > second.priority) {
            result = new Treap(first.key, first.priority, first.left, merge(first.right, second));
        } else {
            result = new Treap(second.key, second.priority, merge(first, second.left), second.right);
        }
        return result;
    }

    public void split(int key, Treap first, Treap second) {
        Treap newTreap = null;
        if (this.key >= key) {
            if (left == null) {
                first = null;
            } else {
                left.split(key, first, newTreap);
            }
            second = new Treap(this.key, priority, newTreap, right);
        } else {
            if (right == null) {
                second = null;
            } else {
                right.split(key, newTreap, second);
            }
            first = new Treap(this.key, priority, left, newTreap);
        }
    }
}
