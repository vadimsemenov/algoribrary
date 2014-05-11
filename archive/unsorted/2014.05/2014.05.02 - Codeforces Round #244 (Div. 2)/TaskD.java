package tasks;



import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskD {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        String s = in.next();
        String t = in.next();
        int[][] lcpS = calculateLCP(s, s);
        int[][] lcpT = calculateLCP(t, t);
        int[][] lcp  = calculateLCP(s, t);
        int[] uniqueS = calculateUnique(s, lcpS);
        int[] uniqueT = calculateUnique(t, lcpT);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < t.length(); j++) {
                if (lcp[i][j] > 0) {
                    int temp = Math.max(uniqueS[i], uniqueT[j]);
                    if (temp <= lcp[i][j] && min > temp) min = temp;
                }
            }
        }
        if (min == Integer.MAX_VALUE) min = -1;
        out.println(min);
    }

    private int[] calculateUnique(String string, int[][] lcp) {
        int[] unique = new int[string.length()];
        Arrays.fill(unique, 1);
        for (int i = 0; i < string.length(); i++)
            for (int j = 0; j < string.length(); j++)
                if (i != j) unique[i] = Math.max(unique[i], lcp[i][j] + 1);
        return unique;
    }

    private int[][] calculateLCP(String first, String second) {
        int[][] lcp = new int[first.length() + 1][second.length() + 1];
        for (int i = first.length() - 1; i >= 0; i--)
            for (int j = second.length() - 1; j >= 0; j--)
                if (first.charAt(i) == second.charAt(j))
                    lcp[i][j] = 1 + lcp[i + 1][j + 1];
        return lcp;
    }
}
