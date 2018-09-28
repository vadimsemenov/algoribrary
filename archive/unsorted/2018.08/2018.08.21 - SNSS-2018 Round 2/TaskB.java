package task;



import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int wordsQty = in.nextInt();
        List<int[]> trie = new ArrayList<>();
        trie.add(new int[26]);
        final int root = 0;
        for (int wordId = 0; wordId < wordsQty; ++wordId) {
            String word = in.next();
            for (int i = 0; i < word.length(); ++i) {
                int current = root;
                for (int j = 1; j < 7 && i + j <= word.length(); ++j) {
                    int letter = word.charAt(i + j - 1) - 'a';
                    if (trie.get(current)[letter] == 0) {
                        trie.get(current)[letter] = trie.size();
                        trie.add(new int[26]);
                    }
                    current = trie.get(current)[letter];
                }
            }
        }
        int[] queue = new int[trie.size()];
        int head = 0;
        int tail = 0;
        queue[tail++] = root;
        int[] pred = new int[trie.size()];
        Arrays.fill(pred, -1);
        char[] letter = new char[trie.size()];
        while (head < tail) {
            int current = queue[head++];
            for (int i = 0; i < 26; ++i) {
                int next = trie.get(current)[i];
                if (next == 0) {
                    StringBuilder answer = new StringBuilder();
                    answer.append((char) ('a' + i));
                    while (current > 0) {
                        answer.append(letter[current]);
                        current = pred[current];
                    }
                    out.println(answer.reverse());
                    return;
                }
                assert pred[next] == -1;
                pred[next] = current;
                letter[next] = (char) ('a' + i);
                queue[tail++] = next;
            }
        }
        throw new IllegalStateException("POLUNDRA!");
    }
}
