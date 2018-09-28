package task;



import algoribrary.io.InputReader;
import algoribrary.misc.ArrayUtils;

import java.io.PrintWriter;
import java.util.*;

public final class TaskF {
    public void solve(int __, InputReader in, PrintWriter out) {
        String s = in.next();
        String t = in.next();
        int[][] result = solve(s.toCharArray(), t.toCharArray());
        out.println(result.length);
        for (int[] pair : result) {
            out.print(pair[0]);
            out.print(' ');
            out.println(pair[1]);
        }
    }

    private int[][] solve(char[] s, char[] t) {
        int[][] result = solveInternal(s, t);
        int[][] anotherResult = solveInternal(t, s);
        if (anotherResult.length < result.length) {
            result = anotherResult;
            for (int[] pair : result) {
                ArrayUtils.swap(pair, 0, 1);
            }
        }
        return result;
    }

    private int[][] solveInternal(char[] s, char[] t) {
        List<Integer> sChangings = getChangings(s, 0, s.length, s, 0, 0, 'a');
        List<Integer> tChangings = getChangings(t, 0, t.length, t, 0, 0, 'b');
        int[][] result = solveGreedy(s, 0, s.length, s, 0, 0, t, 0, t.length, t, 0, 0, 0, Integer.MAX_VALUE);
        assert result != null;
        int balance = (sChangings.size() - tChangings.size()) / 2;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                int sp = (balance > 0 ? balance : 0) + i;
                int tp = (balance < 0 ? -balance : 0) + j;
                if ((sp != 0 && sp >= sChangings.size()) || (tp != 0 && tp >= tChangings.size())) continue;
                int sSep = sp < sChangings.size() ? sChangings.get(sp) : 0;
                int tSep = tp < tChangings.size() ? tChangings.get(tp) : 0;
                int[][] current = solveGreedy(t, 0, tSep, s, sSep, s.length, s, 0, sSep, t, tSep, t.length, 1, result.length);
                if (current != null) {
                    result = current;
                    current[0][0] = sSep;
                    current[0][1] = tSep;
                }
            }
        }
        return result;
    }

    private int[][] solveGreedy(char[] s1, int s1from, int s1to,
                                char[] s2, int s2from, int s2to,
                                char[] t1, int t1from, int t1to,
                                char[] t2, int t2from, int t2to,
                                int offset, int limit) {
        List<Integer> ss = getChangings(s1, s1from, s1to, s2, s2from, s2to, 'a');
        List<Integer> tt = getChangings(t1, t1from, t1to, t2, t2from, t2to, 'b');
        int length = Math.max(ss.size(), tt.size());
        if (offset + length >= limit) {
            return null;
        }
        int[][] result = new int[offset + length][2];
        for (int i = 0; i < length; ++i) {
            result[offset + i][0] = pop(ss);
            result[offset + i][1] = pop(tt);
            List<Integer> tmp = ss;
            ss = tt;
            tt = tmp;
        }
        return result;
    }

    private List<Integer> getChangings(char[] s1, int s1from, int s1to, char[] s2, int s2from, int s2to, char target) {
        List<Integer> changings = new ArrayList<>();
        for (int i = s2to; i-- > s2from; ) {
            char current = s2[i];
            if (current != target) {
                changings.add(s1to - s1from + i - s2from + 1);
                target = current;
            }
        }
        for (int i = s1to; i-- > s1from; ) {
            char current = s1[i];
            if (current != target) {
                changings.add(i - s1from + 1);
                target = current;
            }
        }
        Collections.reverse(changings);
        return changings;
    }

    private static int pop(List<Integer> stack) {
        return stack.isEmpty() ? 0 : stack.remove(stack.size() - 1);
    }
}
