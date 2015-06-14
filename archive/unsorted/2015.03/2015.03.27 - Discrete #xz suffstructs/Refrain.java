package tasks;

import algoribrary.io.InputReader;
import algoribrary.strings.StringUtils;

import java.io.PrintWriter;

public class Refrain {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        int alphabetSize = in.nextInt();
        int[] string = new int[length + 1];
        for (int i = 0; i < length; ++i) {
            string[i] = in.nextInt();
        }
        int[] suffixArray = StringUtils.buildSuffixArray(string);
        int[] lcp = StringUtils.buildLCP(string, suffixArray);
        int[] stack = new int[lcp.length];
        int top = 0;
        int bestPosition = -1;
        int bestLength = -1;
        for (int i = 0; i < lcp.length; ++i) {
            while (top > 0 && lcp[stack[top - 1]] >= lcp[i]) {
                int current = stack[top - 1];
                int previous = top == 1 ? -1 : stack[top - 2];
                int currentLength = i - previous;
                if (bestPosition == -1 ||
                        (long) lcp[bestPosition] * bestLength < (long) lcp[current] * currentLength) {
                    bestPosition = current;
                    bestLength = currentLength;
                }
                top--;
            }
            stack[top++] = i;
        }
        if ((long) lcp[bestPosition] * bestLength < length) {
            out.println(length);
            out.println(length);
            for (int i = 0; i < length; ++i) {
                if (i > 0) out.print(' ');
                out.print(string[i]);
            }
        } else {
            out.println((long) lcp[bestPosition] * bestLength);
            out.println(lcp[bestPosition]);
            for (int i = 0; i < lcp[bestPosition]; ++i) {
                if (i > 0) out.print(' ');
                out.print(string[suffixArray[bestPosition] + i]);
            }
        }
    }
}
