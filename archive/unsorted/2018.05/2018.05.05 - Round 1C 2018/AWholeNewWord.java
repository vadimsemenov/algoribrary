package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class AWholeNewWord {
    public void solve(int testCase, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int length = in.nextInt();
        String[] words = in.nextStringArray(counter);
        int capacity = counter * length + 1;
        int[][] trie = new int[capacity][26];
        int[] parent = new int[capacity];
        int[] position = new int[capacity];
        char[] character = new char[capacity];
        Arrays.fill(parent, -1);
        Arrays.fill(position, -1);
        position[0] = 0;
        int size = 1;
        for (String word : words) {
            int current = 0;
            for (int i = 0; i < word.length(); ++i) {
                int letter = word.charAt(i) - 'A';
                if (trie[current][letter] == 0) {
                    parent[size] = current;
                    position[size] = position[current] + 1;
                    character[size] = word.charAt(i);
                    trie[current][letter] = size++;
                }
                current = trie[current][letter];
            }
        }
        for (int state = 1; state < size; ++state) {
            if (position[state] == length) continue;
            for (String word : words) {
                int letter = word.charAt(position[state]) - 'A';
                if (trie[state][letter] == 0) {
                    StringBuilder answer = new StringBuilder(length);
                    for (int current = state; current > 0; current = parent[current]) {
                        answer.append(character[current]);
                    }
                    answer.reverse();
                    answer.append(word.substring(position[state]));
                    out.println("Case #" + testCase + ": " + answer.toString());
                    return;
                }
            }
        }
        out.println("Case #" + testCase + ": -");
    }
}
