package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.*;

public class Barica {
    private static final int MAX_COORD = 100_000;
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int needEnergy = in.nextInt();
        List<Integer>[] xs = new List[MAX_COORD + 1];
        List<Integer>[] ys = new List[MAX_COORD + 1];
        final int[] x = new int[counter];
        final int[] y = new int[counter];
        int[] f = new int[counter];
        for (int i = 0; i < counter; ++i) {
            x[i] = in.nextInt();
            if (xs[x[i]] == null) {
                xs[x[i]] = new ArrayList<>();
            }
            xs[x[i]].add(i);
            y[i] = in.nextInt();
            if (ys[y[i]] == null) {
                ys[y[i]] = new ArrayList<>();
            }
            ys[y[i]].add(i);
            f[i] = in.nextInt();
        }
        Comparator<Integer> xcomp = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(x[o1], x[o2]);
            }
        };
        Comparator<Integer> ycomp = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(y[o2], y[o1]);
            }
        };
        for (int i = 0; i <= MAX_COORD; ++i) {
            if (xs[i] != null) {
                Collections.sort(xs[i], ycomp);
            }
            if (ys[i] != null) {
                Collections.sort(ys[i], xcomp);
            }
        }
        int[] energy = new int[counter];
        Arrays.fill(energy, Integer.MIN_VALUE);
        energy[0] = 0;
        int[] pred = new int[counter];
        pred[0] = -1;
        for (int yc = 0; yc <= MAX_COORD; ++yc) {
            if (ys[yc] != null) {
                for (int idx : ys[yc]) {
                    if (energy[idx] + f[idx] < needEnergy) continue;
                    int en = energy[idx] + f[idx] - needEnergy;
                    for (int nidx : ys[y[idx]]) {
                        if (x[nidx] > x[idx]) {
                            if (energy[nidx] < en) {
                                energy[nidx] = en;
                                pred[nidx] = idx;
                            }
                        }
                    }
                    for (int nidx : xs[x[idx]]) {
                        if (y[nidx] > y[idx]) {
                            if (energy[nidx] < en) {
                                energy[nidx] = en;
                                pred[nidx] = idx;
                            }
                        }
                    }
                }
            }
        }
        out.println(energy[counter - 1] + f[counter - 1]);
        List<Integer> o = new ArrayList<>();
        int current = counter - 1;
        while (current != -1) {
            o.add(current);
            current = pred[current];
        }
        Collections.reverse(o);
        out.println(o.size());
        for (int i : o) {
            out.println(x[i] + " " + y[i]);
        }
    }
}
