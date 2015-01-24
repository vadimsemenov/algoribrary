package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class Autocomplete {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        long start = System.currentTimeMillis();
        Node root = new Node();
        int counter = in.nextInt();
        int answer = 0;
        for (int i = 0; i < counter; ++i) {
            char[] word = in.next().toCharArray();
//            boolean ready = false;
//            Node current = root;
//            for (char ch : word) {
//                if (!ready) answer++;
//                int child = ch - 'a';
//                if (current.children[child] == null) {
//                    ready = true;
//                    current.children[child] = new Node();
//                }
//                current = current.children[child];
//            }
            answer += root.add(word, 0);
        }
        long finish = System.currentTimeMillis();
        print(out, testNumber, answer, finish - start);
    }

    private void print(PrintWriter out, int testNumber, int answer, long time) {
        System.err.println("Case #" + testNumber + ": " + answer + ", ready in " + time + ".ms");
               out.println("Case #" + testNumber + ": " + answer);
    }

    public static class Node {
        private Node[] children = new Node[26];

        public int add(char[] word, int ptr) {
            if (ptr == word.length) return 0;
            int child = word[ptr] - 'a';
            if (children[child] == null) {
                children[child] = new Node();
                children[child].add(word, ptr + 1);
                return 1;
            }
            return 1 + children[child].add(word, ptr + 1);
        }
    }
}
