package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskE {
    static class MagicFenwick {
        int[] data, times;
        int time;

        MagicFenwick(int counter) {
            data = new int[counter];
            times = new int[counter];
        }

        void update(int at, int delta) {
            while (at < data.length) {
                data[at] = at(at) + delta;
                at |= at + 1;
            }
        }

        int get(int at) {
            int result = 0;
            while (at >= 0) {
                result += at(at);
                at = (at & at + 1) - 1;
            }
            return result;
        }

        final int at(int i) {
            if (times[i] < time) {
                times[i] = time;
                data[i] = 0;
            }
            return data[i];
        }

        void clear() {
            ++time;
        }
    }

    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[] types = new int[counter];
        int[] times = new int[counter];
        int[] xs = new int[counter];
        for (int i = 0; i < counter; ++i) {
            types[i] = in.nextInt();
            times[i] = in.nextInt();
            xs[i] = in.nextInt();
        }
        int[] cxs;
        int length;
        {
            cxs = Arrays.copyOf(times, times.length);
            Arrays.sort(cxs);
            length = 1;
            for (int i = 1; i < cxs.length; ++i) {
                if (cxs[i - 1] != cxs[i]) {
                    cxs[length++] = cxs[i];
                }
            }
            for (int i = 0; i < counter; ++i) {
                times[i] = Arrays.binarySearch(cxs, 0, length, times[i]);
            }
        }
        MagicFenwick tree = new MagicFenwick(length);
        {
            cxs = Arrays.copyOf(xs, xs.length);
            Arrays.sort(cxs);
            length = 1;
            for (int i = 1; i < cxs.length; ++i) {
                if (cxs[i - 1] != cxs[i]) {
                    cxs[length++] = cxs[i];
                }
            }
            for (int i = 0; i < counter; ++i) {
                xs[i] = Arrays.binarySearch(cxs, 0, length, xs[i]);
            }
        }
        List<Integer>[] at = new List[length];
        for (int i = 0; i < length; ++i) at[i] = new ArrayList<>();
        for (int i = 0; i < counter; ++i) {
            at[xs[i]].add(i);
        }
        int[] answer = new int[counter];
        Arrays.fill(answer, -1);
        for (int i = 0; i < length; ++i) {
            tree.clear();
            for (int t : at[i]) {
                int time = times[t];
                int type = types[t];
                if (type == 1) {
                    tree.update(time, 1);
                } else if (type == 2) {
                    tree.update(time, -1);
                } else if (type == 3) {
                    answer[t] = tree.get(time);
                } else throw new AssertionError(type);
            }
        }
        for (int i : answer) {
            if (i != -1) {
                out.print(i); out.print(' ');
            }
        }
    }
}
