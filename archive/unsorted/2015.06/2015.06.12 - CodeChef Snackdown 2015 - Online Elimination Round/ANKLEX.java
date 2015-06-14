package tasks;

import algoribrary.io.InputReader;
import algoribrary.strings.StringUtils;

import java.io.PrintWriter;

public class ANKLEX {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        System.err.println();
        char[] _string = (in.next() + "#").toCharArray();
        int[] string = new int[_string.length];
        for (int i = 0; i < _string.length; ++i) {
            string[i] = _string[i] - '#';
        }
        int[] sa = StringUtils.buildSuffixArray(string);
        int[] lcp = StringUtils.buildLCP(string, sa);
        int[] inverse = new int[string.length];
        for (int i = 0; i < string.length; i++) {
            inverse[sa[i]] = i;
        }
        int[][] idx = new int[20][string.length];
        idx[0] = sa.clone();
        for (int log = 1; log < 20; ++log) {
            int len = 1 << (log - 1);
            for (int i = 0; i + 2 * len <= string.length; ++i) {
                idx[log][i] = Math.min(idx[log - 1][i], idx[log - 1][i + len]);
            }
        }
        int[][] minLcp = new int[20][lcp.length];
        minLcp[0] = lcp.clone();
        for (int log = 1; log < 20; ++log) {
            int len = 1 << (log - 1);
            for (int i = 0; i + 2 * len <= lcp.length; ++i) {
                minLcp[log][i] = Math.min(minLcp[log - 1][i], minLcp[log - 1][i + len]);
            }
        }
        int[] log = new int[string.length];
        for (int i = 2; i < log.length; ++i) {
            log[i] = log[i >> 1] + 1;
        }
        int queries = in.nextInt();
        for (int q = 0; q < queries; ++q) {
            int index = in.nextInt() - 1;
            int length = in.nextInt();
            int left = index - 1;
            int right = string.length - 1;
            while (right - left > 1) {
                int mid = (left + right) >>> 1;
                int lev = log[mid - 1];
                int min = Math.min(minLcp[lev][index], minLcp[lev][index + (1 << lev)]);
                if (min > length) {
                    left = mid;
                } else {
                    right = mid;
                }
            }
            int from = right;
            left = from;
            right = string.length - 1;
            while (right - left > 1) {
                int middle = (left + right) >>> 1;

            }
        }
    }
}
