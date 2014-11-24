package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class SortPerm {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        int[] fst = new int[length];
        int[] atfst = new int[length];
        int[] snd = new int[length];
        int[] atsnd = new int[length];
        for (int i = 0; i < length; ++i) {
            fst[i] = in.nextInt() - 1;
            atfst[fst[i]] = i;
        }
        for (int i = 0; i < length; ++i) {
            snd[i] = in.nextInt() - 1;
            atsnd[snd[i]] = i;
        }
        boolean[] usedf = new boolean[length];
        boolean[] useds = new boolean[length];
        int diff = 0;
        int[] result = new int[length];
        int current = 1;
        for (int i = 0; i < length; ++i) {
            result[atfst[i]] = current;

            if (usedf[atfst[i]]) diff--;
            else usedf[atfst[i]] = true;
            if (useds[atsnd[i]]) diff--;
            else useds[atsnd[i]] = true;

            if (!usedf[atsnd[i]]) {
                diff++;
                usedf[atsnd[i]] = true;
            }
            if (!useds[atfst[i]]) {
                diff++;
                useds[atfst[i]] = true;
            }

            if (diff == 0) {
                current++;
            }
        }
        for (int i : result) {
            out.print(i);
            out.print(' ');
        }
    }
}

// 3 2 5 4 1
// 2 3 1 5 4
// 1 2 3 4 5