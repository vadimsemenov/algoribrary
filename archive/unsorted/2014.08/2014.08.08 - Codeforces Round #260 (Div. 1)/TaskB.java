package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskB {
    private static final int MAX_SUM_LENGTH = 100_000;
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int games = in.nextInt();
        int[][] trie = new int[MAX_SUM_LENGTH + 1][26];
        for (int[] t : trie) Arrays.fill(t, -1);
        int size = 1;
        for (int i = 0; i < counter; ++i) {
            int ptr = 0;
            for (char ch : in.next().toCharArray()) {
                int next = trie[ptr][ch - 'a'];
                if (next == -1) {
                    next = size++;
                }
                ptr = trie[ptr][ch - 'a'] = next;
            }
        }
        boolean[] canWin = new boolean[size];
        boolean[] canLose = new boolean[size];
        for (int i = size - 1; i >= 0; --i) {
            boolean isLeave = true;
            for (int c = 0; c < 26; ++c) {
                isLeave &= trie[i][c] == -1;
            }
            if (isLeave) {
                canLose[i] = true;
                continue;
            }
            for (int c = 0; c < 26; ++c) if (trie[i][c] != -1) {
                canLose[i] |= !canLose[trie[i][c]];
                canWin[i] |= !canWin[trie[i][c]];
            }
        }
        if (!canWin[0]) {
            out.println("Second");
        } else if (canLose[0]) {
            out.println("First");
        } else {
            out.println(games % 2 == 1 ? "First" : "Second");
        }
    }
}
