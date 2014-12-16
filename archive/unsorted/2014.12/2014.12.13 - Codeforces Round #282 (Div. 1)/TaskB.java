package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskB {
    private static final int MODULO = 1_000_000_007;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        String text = in.next();
        String word = in.next();
        int[] pf = prefixFunction(word + "#" + text);
//        System.err.println(Arrays.toString(pf));
        int[] pred = new int[pf.length];
        for (int i = 1; i < pf.length; ++i) {
            if (pf[i] == word.length()) {
                pred[i] = i - word.length();
            } else {
                pred[i] = pred[i - 1];
            }
        }
        int[] ways = new int[pf.length];
        ways[word.length()] = 1;
        int[] sums = new int[ways.length];
        sums[word.length()] = 1;
        for (int i = word.length() + 1; i < pf.length; ++i) {
            ways[i] = ways[i - 1];
            ways[i] += sums[pred[i]];
            if (ways[i] >= MODULO) ways[i] -= MODULO;
            sums[i] = sums[i - 1] + ways[i];
            if (sums[i] >= MODULO) sums[i] -= MODULO;
        }
        out.println((ways[ways.length - 1] - 1 + MODULO) % MODULO);
    }

    private int[] prefixFunction(String s) {
        int[] pf = new int[s.length()];
        for (int i = 1; i < s.length(); ++i) {
            int k = pf[i - 1];
            while (k > 0 && s.charAt(i) != s.charAt(k)) {
                k = pf[k - 1];
            }
            if (s.charAt(i) == s.charAt(k)) {
                ++k;
            }
            pf[i] = k;
        }
        return pf;
    }
}
