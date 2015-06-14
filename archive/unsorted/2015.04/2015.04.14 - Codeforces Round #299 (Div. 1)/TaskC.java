package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class TaskC { // doesn't work :(
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] s = new int[counter];
        int[] r = new int[counter];
        for (int i = 0; i < counter; ++i) {
            s[i] = in.nextInt();
            r[i] = in.nextInt();
        }
        int[] ss = Arrays.copyOf(s, counter);
        int[] rr = Arrays.copyOf(r, counter);
        Arrays.sort(ss);
        Arrays.sort(rr);
        int sp = 1;
        int rp = 1;
        for (int i = 1; i < counter; ++i) {
            if (ss[i] != ss[i - 1]) {
                ss[sp++] = ss[i];
            }
            if (rr[i] != rr[i - 1]) {
                rr[rp++] = rr[i];
            }
        }
        for (int i = 0; i < counter; ++i) s[i] = Arrays.binarySearch(ss, 0, sp, s[i]);
        for (int i = 0; i < counter; ++i) r[i] = Arrays.binarySearch(rr, 0, rp, r[i]);

        Integer[] byS = new Integer[counter];
        for (int i = 0; i < counter; ++i) byS[i] = i;
        Arrays.sort(byS, new Comparator<Integer>() {
            @Override
            public int compare(Integer first, Integer second) {
                return -Integer.compare(s[first], s[second]);
            }
        });
        int[] stack = new int[counter];
        int top = 0;
        for (int i = 0; i < counter; ++i) {
            int current = byS[i];
            while (top > 0 && s[current] == s[stack[top - 1]] && r[current] > r[stack[top - 1]]) {
                --top;
            }
            while (top > 1) {
                int fst = stack[top - 2];
                int snd = stack[top - 1];
                int thr = current;
                long lhs = (long) rr[r[fst]] * ss[s[thr]] * (ss[s[fst]] - ss[s[snd]]) * (rr[r[thr]] - rr[r[snd]]);
                long rhs = (long) rr[r[thr]] * ss[s[fst]] * (ss[s[snd]] - ss[s[thr]]) * (rr[r[snd]] - rr[r[fst]]);
                if (lhs <= rhs) break;
                --top;
            }
            if (top == 0 || (s[stack[top - 1]] == s[current] && r[stack[top - 1]] == r[current]) ||
                    r[stack[top - 1]] < r[current]) {
                stack[top++] = current;
            }
        }
        {
            int lastS = Integer.MAX_VALUE;
            int lastR = -1;
            for (int i = 0; i < top; ++i) {
                if (lastS < s[stack[i]] || lastR > r[stack[i]]) {
                    throw new AssertionError(lastS + " " + s[stack[i]] + " " + lastR + " " + r[stack[i]]);
                }
                lastS = s[stack[i]];
                lastR = r[stack[i]];
            }
        }
//        if (top < 2 && counter > 1) throw new AssertionError(top);
        Arrays.sort(stack, 0, top);
        for (int i = 0; i < top; ++i) {
            if (i > 0) out.print(' ');
            out.print(stack[i] + 1);
        }
        out.println();
    }
}
