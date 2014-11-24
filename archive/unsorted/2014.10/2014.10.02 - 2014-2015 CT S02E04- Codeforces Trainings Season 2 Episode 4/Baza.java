package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.*;

public class Baza {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        String[] words = new String[counter];
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < counter; ++i) {
            String word = in.next();
            words[i] = word;
            map.put(word, i);
        }
        int queries = in.nextInt();
        String[] requests = new String[queries];
        List<Integer>[] toAdd = new List[counter];
        List<Integer> toCheck = new ArrayList<>();

        for (int i = 0; i < queries; ++i) {
            requests[i] = in.next();
            Integer key = map.get(requests[i]);
            if (key != null) {
                if (toAdd[key] == null)
                    toAdd[key] = new ArrayList<>();
                toAdd[key].add(i);
            } else {
                toCheck.add(i);
            }
        }
        int[][] trie = new int[counter * 40][26];
        for (int[] t : trie)
            Arrays.fill(t, -1);
        int size = 1;
        int[] cnt = new int[counter * 40];
        int[] answer = new int[queries];
        for (int i = 0; i < counter; ++i) {
            int current = 0;
            int tot = ++cnt[current];
            for (int pos = 0; pos < words[i].length(); ++pos) {
                int letter = words[i].charAt(pos) - 'a';
                if (trie[current][letter] == -1) {
                    trie[current][letter] = size++;
                }
                current = trie[current][letter];
                tot += ++cnt[current];
            }
            if (toAdd[i] != null) {
                for (int idx : toAdd[i]) {
                    answer[idx] = tot;
                }
            }
        }
        for (int i : toCheck) {
            int current = 0;
            int tot = cnt[current];
            for (int pos = 0; pos < requests[i].length(); ++pos) {
                int letter = requests[i].charAt(pos) - 'a';
                if (trie[current][letter] == -1) break;
                current = trie[current][letter];
                tot += cnt[current];

            }
            answer[i] = tot;
        }
        for (int i : answer) {
            out.println(i);
        }
    }
}
