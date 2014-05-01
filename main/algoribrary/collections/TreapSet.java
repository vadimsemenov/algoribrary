package algoribrary.collections;

import java.io.PrintWriter;
import java.util.Random;

/**
 * Created by vadim on 15-03-2014.
 */
public class TreapSet {
    private static final int NONE_VALUE = -1;

    private final Random random;
    private Node root;
    private int size;

    public TreapSet() {
        this(new Random());
    }

    public TreapSet(Random random) {
        this.random = random;
    }

    private TreapSet(Node root) {
        this();
        this.root = root;
    }

    // slow version
    public void add(int key) {
        if (contains(key)) {
            return;
        }
        size++;
        Node newNode = new Node(key, random.nextInt(), null, null);
        if (root == null) {
            root = newNode;
        } else {
            Node.NodePair pair = root.slowSplit(key);
            Node result = pair.second == null ? newNode : newNode.slowMerge(pair.second);
            root = pair.first == null ? result : pair.first.slowMerge(result);
        }
    }

    // slow version
    public void remove(int key) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        if (!contains(key)) {
            throw new RuntimeException();
        }
        size--;
        Node.NodePair pair = root.slowSplit(key + 1);
        Node left = pair.first.slowSplit(key).first;
        root = left == null ? pair.second : left.slowMerge(pair.second);
    }

    // successor
    public int succ(int key) {
        Node current = root;
        Node previous = null;
        while (current != null) {
            if (current.key == key) {
                current = current.right;
            } else if (current.key > key) {
                previous = current;
                current = current.left;
            } else {
                current = current.right;
            }
        }
        if (current == null) {
            return previous == null ? NONE_VALUE : previous.key;
        }
        return current.key;
    }

    public int upperBound(int key) {
        Node current = root;
        Node previous = null;
        while (current != null) {
            if (current.key > key) {
                previous = current;
                current = current.left;
            } else if (current.key < key) {
                current = current.right;
            } else {
                break;
            }
        }
        if (current == null) {
            return previous == null ? NONE_VALUE : previous.key;
        }
        return current.key;
    }

    //predecessor
    public int pred(int key) {
        Node current = root;
        Node previous = null;
        while (current != null) {
            if (current.key == key) {
                current = current.left;
            } else if (current.key < key) {
                previous = current;
                current = current.right;
            } else {
                current = current.left;
            }
        }
        if (current == null) {
            return previous == null ? NONE_VALUE : previous.key;
        }
        return current.key;
    }

    public void print(PrintWriter writer) {
        root.print(" ", writer);
    }

    public boolean contains(int key) {
        Node current = root;
        while (current != null) {
            if (current.key == key) {
                return true;
            }
            if (current.key > key) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node {
        private int key, priority;
        private Node left, right;

        private Node(int key, int priority, Node left, Node right) {
            this.key = key;
            this.priority = priority;
            this.left = left;
            this.right = right;
        }

        // slow
        private Node slowMerge(Node another) {
            if (another == null) {
                return this;
            }
            Node result;
            if (this.priority > another.priority) {
                result = this;
                Node left = this.right;
                result.right = left == null ? another : left.slowMerge(another);
            } else {
                result = another;
                Node right = another.left;
                result.left = this.slowMerge(right);
            }
            return result;
        }

//        // Doesn't work
//        private Node merge(Node another) {
//            if (another == null) {
//                return this;
//            }
//            Node result;
//            Node left, right, parent;
//            if (another.priority > this.priority) {
//                left = this;
//                right = another.left;
//                result = another;
//                result.left = null;
//            } else {
//                left = this.right;
//                right = another;
//                result = this;
//                result.right = null;
//            }
//            parent = result;
//            while (true) {
//                if (left == null) {
//                    if (parent.key > right.key) {
//                        parent.left = right;
//                    } else {
//                        parent.right = right;
//                    }
//                    break;
//                }
//                if (right == null) {
//                    if (parent.key > left.key) {
//                        parent.left = left;
//                    } else {
//                        parent.right = left;
//                    }
//                    break;
//                }
//                if (right.priority > left.priority) {
//                    if (parent.priority > right.priority) {
//                        parent.left = right;
//                    } else {
//                        parent.right = right;
//                    }
//                    Node next = right.left;
//                    right.left = null;
//                    parent = right;
//                    right = next;
//                } else {
//                    if (parent.priority > left.priority) {
//                        parent.left = left;
//                    } else {
//                        parent.right = left;
//                    }
//                    Node next = left.right;
//                    left.right = null;
//                    parent = left;
//                    left = next;
//                }
//            }
//            return result;
//        }

        // slow
        private NodePair slowSplit(int key) {
            if (this.key < key) {
                if (right == null) {
                    return new NodePair(this, null);
                }
                NodePair pair = right.slowSplit(key);
                Node first = new Node(this.key, this.priority, left, pair.first);
                return new NodePair(first, pair.second);
            } else {
                if (left == null) {
                    return new NodePair(null, this);
                }
                NodePair pair = left.slowSplit(key);
                Node second = new Node(this.key, this.priority, pair.second, right);
                return new NodePair(pair.first, second);
            }
        }

//        // Doesn't work
//        private NodePair split(int key) {
//            Node first = null, second = null;
//            Node left = null, right = null;
//            Node splited = this;
//            while (splited != null) {
//                if (splited.key < key) {
//                    if (right == null) {
//                        first = splited;
//                        right = splited;
//                    } else {
//                        right.left = splited;
//                        right = right.left;
//                    }
//                    Node next = splited.left;
//                    splited.left = null;
//                    splited = next;
//                } else {
//                    if (left == null) {
//                        second = splited;
//                        left = splited;
//                    } else {
//                        left.right = splited;
//                        left = left.right;
//                    }
//                    Node next = splited.right;
//                    splited.right = null;
//                    splited = next;
//                }
//            }
//            return new NodePair(first, second);
//        }

        private void print(String prefix, PrintWriter writer) {
            if (left != null) {
                left.print(prefix + "\t", writer);
            }
            writer.println(prefix + key + " " + priority);
            if (right != null) {
                right.print(prefix + "\t", writer);
            }
        }

        private static class NodePair {
            Node first, second;

            private NodePair(Node first, Node second) {
                this.first = first;
                this.second = second;
            }
        }
    }
}
