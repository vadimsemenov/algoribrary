package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Queries {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        final int[] powers = new int[10];
        powers[9] = 1;
        for (int i = 8; i >= 0; --i) {
            powers[i] = powers[i + 1] * 10;
        }

        int counter = in.nextInt();
        int queries = in.nextInt();
        int[] array = in.nextIntArray(counter);
        final List<int[][]> trie = new ArrayList<>(counter);
        trie.add(new int[2][10]);
        final int root = 0;
        for (int number : array) {
            int current = root;
            for (int i = 0; i < powers.length; ++i) {
                int x = number / powers[i];
                assert 0 <= x && x <= 9;
                number -= x * powers[i];
                int next = trie.get(current)[0][x];
                if (next == 0) {
                    next = trie.get(current)[0][x] = trie.size();
                    trie.add(new int[2][10]);
                }
                current = next;
            }
            ++trie.get(current)[1][9];
        }
        for (int i = trie.size() - 1; i >= 0; --i) {
            int ad = trie.get(i)[1][9];
            if (ad != 0) {
                continue;
            }
            for (int j = 9; j >= 0; --j) {
                trie.get(i)[1][j] = ad;
                int child = trie.get(i)[0][j];
                if (child != 0) {
                    ad += trie.get(child)[1][9];
                }
            }
            trie.get(i)[1][9] = ad;
        }
        if (trie.get(root)[1][9] != counter) {
            throw new AssertionError("WTF?! " + trie.get(root)[1][9]);
        }
        for (int[][] node : trie) {
            node[1][9] = 0;
        }

        while (queries --> 0) {
            String query = in.next();
            out.println(dfs(query, 0, trie, root, 0));
        }
    }

    private long dfs(final String number, final int ptr, final List<int[][]> trie, final int vertex, final int ad) {
        if (ptr >= number.length()) {
            return ad;
        }
        long result = 0;
        if (number.charAt(ptr) == '?') {
            for (int i = 9; i >= 0; --i) {
                int next = trie.get(vertex)[0][i];
                if (next != 0) {
                    result += dfs(number, ptr + 1, trie, next, ad + trie.get(vertex)[1][i]);
                }
            }
        }
        return result;
    }
}
