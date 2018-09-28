package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int teamsQty = in.nextInt();
        int nicksQty = in.nextInt();
        String[] names = in.nextStringArray(teamsQty);
        String[] nicknames = in.nextStringArray(nicksQty);
        Trie prefix = new Trie();
        Trie suffix = new Trie();
        int[] prefixId = new int[nicksQty];
        int[] suffixId = new int[nicksQty];
        for (int idx = 0; idx < nicksQty; ++idx) {
            String nickname = nicknames[idx];
            int current = Trie.ROOT;
            for (int i = 0; nickname.charAt(i) != '-'; ++i) {
                current = prefix.add(current, nickname.charAt(i));
            }
            prefixId[idx] = current;
            current = Trie.ROOT;
            for (int i = nickname.length() - 1; nickname.charAt(i) != '-'; --i) {
                current = suffix.add(current, nickname.charAt(i));
            }
            suffixId[idx] = current;
        }
        for (int idx = 0; idx < names.length; idx++) {
            String name = names[idx];
            int state = Trie.ROOT;
            for (int i = 0; i < name.length(); ++i) {
                state = prefix.go(state, name.charAt(i));
                if (state == -1) break;
                prefix.addId(state, idx);
            }
            state = Trie.ROOT;
            for (int i = name.length(); i-- > 0; ) {
                state = suffix.go(state, name.charAt(i));
                if (state == -1) break;
                suffix.addId(state, idx);
            }
        }
        for (int idx = 0; idx < nicksQty; ++idx) {
            List<Integer> ps = prefix.getIds(prefixId[idx]);
            List<Integer> ss = suffix.getIds(suffixId[idx]);
            int answer = 0;
            for (int i = 0, j = 0; i < ps.size() && j < ss.size(); ++i) {
                int id = ps.get(i);
                while (j < ss.size() && id > ss.get(j)) {
                    j++;
                }
                if (j < ss.size() && id == ss.get(j) && names[id].length() >= nicknames[idx].length() - 1) {
                    answer++;
                }
            }
            out.println(answer);
        }
    }

    static class Trie {
        static final int ROOT = 0;

        private List<int[]> trie;
        private List<List<Integer>> ids;

        Trie() {
            trie = new ArrayList<>();
            ids = new ArrayList<>();
            allocate(); // root
        }

        int go(int state, char letter) {
            int next = trie.get(state)[letter - 'a'];
            return next == ROOT ? -1 : next;
        }

        int add(int state, char letter) {
            int[] links = trie.get(state);
            letter -= 'a';
            int next = links[letter];
            if (next == ROOT) {
                links[letter] = next = allocate();
            }
            return next;
        }

        void addId(int state, int id) {
            ids.get(state).add(id);
        }

        List<Integer> getIds(int state) {
            return ids.get(state);
        }

        private int allocate() {
            assert trie.size() == ids.size();
            int id = trie.size();
            trie.add(new int[26]);
            ids.add(new ArrayList<>());
            return id;
        }
    }
}
