package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class Trie {
    private static final class Node {
        int id;
        Node[] children;

        Node(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return Integer.toString(id);
        }
    }

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int nodes = 0;
        Node root = new Node(++nodes);
        String string = in.next();
        for (int i = 0; i < string.length(); ++i) {
            Node current = root;
            for (int j = i; j < string.length(); ++j) {
                int letter = string.charAt(j);
                if (current.children == null) {
                    current.children = new Node[256];
                }
                if (current.children[letter] == null) {
                    current.children[letter] = new Node(++nodes);
                }
                current = current.children[letter];
            }
        }
        out.println(nodes + " " + (nodes - 1));
        Node[] queue = new Node[nodes];
        int head = 0;
        int tail = 0;
        queue[tail++] = root;
        while (head < tail) {
            Node current = queue[head++];
            if (current.children != null) {
                for (int letter = 0; letter < current.children.length; ++letter) {
                    if (current.children[letter] != null) {
                        out.println(current + " " + current.children[letter] + " " + (char) letter);
                        queue[tail++] = current.children[letter];
                    }
                }
            }
        }
    }
}
