package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Guessing {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[] begin = new int[counter];
        int[] end = new int[counter];
        int[] even = new int[counter];
        int[] all = new int[counter * 2];
        int ptr = 0;
        for (int i = 0; i < counter; ++i) {
            begin[i] = in.nextInt() - 1;
            end[i] = in.nextInt();
            even[i] = in.nextInt();
            all[ptr++] = begin[i];
            all[ptr++] = end[i];
        }
        Arrays.sort(all);
        ptr = 1;
        for (int i = 1; i < all.length; ++i) if (all[i] != all[i - 1]) {
            all[ptr++] = all[i];
        }
        System.err.println(Arrays.toString(Arrays.copyOfRange(all, 0, ptr)));
        for (int i = 0; i < begin.length; ++i) begin[i] = Arrays.binarySearch(all, 0, ptr, begin[i]);
        for (int i = 0; i < end.length; ++i) end[i] = Arrays.binarySearch(all, 0, ptr, end[i]);
        List<Integer>[][] graph = new List[2][ptr];
        for (int it = 0; it < 2; ++it) for (int i = 0; i < graph[it].length; ++i) {
            graph[it][i] = new ArrayList<>();
        }
        int left = 0;
        int right = counter + 1;
        int[] part = new int[ptr];
        int[] queue = new int[ptr];
        while (right - left > 1) {
            for (List[] gg : graph) for (List g : gg) g.clear();
            int middle = (left + right) >>> 1;
            for (int i = 0; i < middle; ++i) {
                graph[even[i]][begin[i]].add(end[i]);
                graph[even[i]][end[i]].add(begin[i]);
            }
            boolean check = true;
            Arrays.fill(part, -1);
            check: for (int v = 0; v < ptr; ++v) {
                if (part[v] != -1) continue;
                part[v] = 0;
                int head = 0;
                int tail = 0;
                queue[tail++] = v;
                while (head < tail) {
                    int cur = queue[head++];
                    for (int it = 0; it < 2; ++it) {
                        int nextPart = part[cur] ^ it;
                        for (int to : graph[it][cur]) {
                            if (part[to] == -1) {
                                part[to] = nextPart;
                                queue[tail++] = to;
                            } else if (part[to] != nextPart) {
                                check = false;
                                break check;
                            }
                        }
                    }
                }
            }
            if (check) {
                left = middle;
            } else {
                right = middle;
            }
        }
        out.println(left);
    }

    static class DSU {
        int[] dad;

        DSU(int capacity) {
            dad = new int[capacity];
            clear();
        }

        int get(int v) {
            return v == dad[v] ? v : (dad[v] = get(dad[v]));
        }

        boolean unite(int u, int v) {
            u = get(u); v = get(v);
            if (u == v) return false;
            dad[u] = v;
            return true;
        }

        void clear() {
            for (int i = 0; i < dad.length; ++i) dad[i] = i;
        }
    }
}
