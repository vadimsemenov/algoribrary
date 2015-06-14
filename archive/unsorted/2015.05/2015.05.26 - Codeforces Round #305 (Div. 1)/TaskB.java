package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[] array = new int[counter];
        for (int i = 0; i < counter; ++i) array[i] = in.nextInt();
        int[] all = array.clone();
        Arrays.sort(all);
        int elements = 1;
        for (int i = 1; i < all.length; ++i) {
            if (all[i] != all[i - 1]) {
                all[elements++] = all[i];
            }
        }
        for (int i = 0; i < counter; ++i) array[i] = Arrays.binarySearch(all, 0, elements, array[i]);
        DSU dsu = new DSU(counter);
        List<Integer>[] positions = new List[elements];
        for (int i = 0; i < elements; ++i) positions[i] = new ArrayList<>();
        for (int i = 0; i < counter; ++i) {
            positions[array[i]].add(i);
        }
        int need = 1;
        for (int element = elements - 1; element >= 0; --element) {
            for (int i : positions[element]) {
                int j = i - 1;
                while (j >= 0 && array[j] >= array[i] && dsu.unite(i, j)) {
                    --j;
                }
                j = i + 1;
                while (j < counter && array[j] >= array[i] && dsu.unite(i, j)) {
                    ++j;
                }
                while (need <= dsu.size(i)) {
                    if (need > 1) out.print(' ');
                    out.print(all[element]);
                    ++need;
                }
            }
        }
//        if (need != counter + 1) throw new AssertionError("WTF?!  " + need);
        out.println();
    }

    static class DSU {
        int[] dad;
        int[] size;

        DSU(int capacity) {
            dad = new int[capacity];
            size = new int[capacity];
            clear();
        }

        int get(int v) {
            return v == dad[v] ? v : (dad[v] = get(dad[v]));
        }

        int size(int v) {
            return size[get(v)];
        }

        boolean unite(int v, int u) {
            v = get(v);
            u = get(u);
            if (u == v) return false;
            if (size[v] < size[u]) {
                int tmp = u; u = v; v = tmp;
            }
            dad[u] = v;
            size[v] += size[u];
            return true;
        }

        void clear() {
            for (int i = 0; i < dad.length; ++i) {
                dad[i] = i;
                size[i] = 1;
            }
        }
    }
}
