package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskA {
    char[] sequence;
    int[] depth, id, begin, end;
    List<Integer>[] children;
    int ptr;
    int cnt;

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        sequence = in.next().toCharArray();
        int counter = sequence.length / 2;
        depth = new int[counter];
        id = new int[counter];
        begin = new int[counter];
        end = new int[counter];
        children = new List[counter];
        for (int i = 0; i < counter; ++i) children[i] = new ArrayList<>();
        int root = parse();
        List<Integer>[] onDepth = new List[depth[root]];
        for (int i = 0; i < onDepth.length; ++i) {
            onDepth[i] = new ArrayList<>();
        }
        for (int i = 0; i < counter; ++i) {
            onDepth[depth[i] - 1].add(i);
        }
        for (int i = 0; i < onDepth.length; ++i) {
            for (int foo : onDepth[i]) Collections.sort(children[foo], cmp);
            Collections.sort(onDepth[i], same);
            int current = 1;
            id[onDepth[i].get(0)] = current;
            for (int j = 1; j < onDepth[i].size(); ++j) {
                if (same.compare(onDepth[i].get(j - 1), onDepth[i].get(j)) != 0) {
                    ++current;
                }
                id[onDepth[i].get(j)] = current;
            }
            out.println(current);
        }
    }

    Comparator<Integer> cmp = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            if (depth[o1] != depth[o2]) {
                return Integer.compare(depth[o1], depth[o2]);
            }
            return Integer.compare(id[o1], id[o2]);
        }
    };

    Comparator<Integer> same = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            int i = 0;
            int j = 0;
            while (i < children[o1].size() && j < children[o2].size()) {
                int cp = cmp.compare(children[o1].get(i++), children[o2].get(j++));
                if (cp != 0) return cp;
            }
            if (i != children[o1].size()) return 1;
            if (j != children[o2].size()) return -1;
            return 0;
        }
    };

    private int parse() {
        int myId = cnt++;
        begin[myId] = ptr;
        if (sequence[ptr++] != '(') throw new AssertionError(ptr + " != (");
        int maxDepth = 0;
        while (sequence[ptr] != ')') {
            int ch = parse();
            maxDepth = Math.max(maxDepth, depth[ch]);
            children[myId].add(ch);
        }
        end[myId] = ptr++;
        depth[myId] = maxDepth + 1;
        return myId;
    }
}
