package algoribrary.collections;

/**
 * Created by vadim on 05-04-2014.
 */
public class SplayTree {
    private static final int NONE_VALUE = -1;
    private Node root;

    public void insert(int key) {
        if (root == null) {
            root = new Node(key);
        }
        splay(key);
        if (root.key == key) {
            return;
        }
        Node temp = new Node(key);
        if (root.key > key) {
            temp.right = root;
        } else {
            temp.left = root;
        }
        root = temp;
    }

    public void remove(int key) {
        if (root == null) {
            return;
        }
        splay(key);
        if (root.key != key) {
            return;
        }
        if (root.right == null) {
            root = root.left;
        } else {
            Node temp = root.right;
            root = root.left;
            splay(key);
            root.right = temp;
        }
    }

    public int getMin() {
        if (root == null) {
            return NONE_VALUE;
        }
        Node current = root;
        while (current.left != null) {
            current = current.left;
        }
        splay(current.key);
        return root.key;
    }

    public int getMax() {
        if (root == null) {
            return NONE_VALUE;
        }
        Node current = root;
        while (current.right != null) {
            current = current.right;
        }
        splay(current.key);
        return root.key;
    }

    public boolean contains(int key) {
        splay(key);
        return root.key == key;
    }

    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Top-down splay implementation.
     *
     * @param key - after splay tree.root is either greatest element <=
     *            <code>key</code>, or lest elment >= <code>key</code>
     *            in the tree.
     */
    private void splay(int key) {
        Node header = new Node(0);
        Node less, greater;
        less = greater = header;
        Node tree = root;
        while (tree.key != key) {
            if (tree.key > key) {
                if (tree.left == null) {
                    break;
                }
                if (tree.left.key > key) {
                    Node temp = tree.left;
                    tree.left = temp.right;
                    temp.right = tree;
                    tree = temp;
                    if (temp.left == null) {
                        break;
                    }
                }
                greater.left = tree;
                greater = greater.left;
                tree = tree.left;
            } else {
                if (tree.right == null) {
                    break;
                }
                if (tree.right.key < key) {
                    Node temp = tree.right;
                    tree.right = temp.left;
                    temp.left = tree;
                    tree = temp;
                    if (temp.right == null) {
                        break;
                    }
                }
                less.right = tree;
                less = less.right;
                tree = tree.right;
            }
        }
        greater.left = tree.right;
        less.right = tree.left;
        tree.left = header.right;
        tree.right = header.left;
        root = tree;
    }

    private static class Node {
        private int key;
        private Node left, right;

        private Node(int key) {
            this(key, null, null);
        }

        private Node(int key, Node left, Node right) {
            this.key = key;
            this.left = left;
            this.right = right;
        }
    }
}
