package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class ANKINTER {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int length = in.nextInt();
        int limit = in.nextInt();
        int[] array = new int[length + 1];
        for (int i = 0; i < length; ++i) array[i] = in.nextInt();
        int[] min = new int[length + 1];
        int minPtr = 0;
        int[] max = new int[length + 1];
        int maxPtr = 0;
        long answer = 0;
        for (int i = 0; i <= length; ++i) {
            if (i == length) array[i] = Integer.MAX_VALUE;
            while (maxPtr > 0 && array[max[maxPtr - 1]] < array[i]) {
                int left = maxPtr > 1 ? max[maxPtr - 2] : -1;
                int right = i;
                // max[maxPtr - 1] -- maximum on interval (left, right)
                int ll = -1;
                int rr = minPtr;
                while (rr - ll > 1) {
                    int middle = (ll + rr) >>> 1;
                    int currentLeft = middle == 0 ? -1 : min[middle - 1];
                    currentLeft = Math.max(currentLeft, left);
                    if (right - currentLeft - 2 >= limit) {
                        ll = middle;
                    } else {
                        rr = middle;
                    }
                }
                int to = ll;
                ll = -1;
                rr = minPtr;
                while (rr - ll > 1) {
                    int middle = (ll + rr) >>> 1;
                    if (min[middle] > left) {
                        rr = middle;
                    } else {
                        ll = middle;
                    }
                }
                int from = rr;
                if (from <= to) {
                    answer += from - to + 1;
                }
                --maxPtr;
            }
            if (i == length) array[i] = Integer.MIN_VALUE;
            while (minPtr > 0 && array[min[minPtr - 1]] < array[i]) {
                int left = minPtr > 1 ? min[minPtr - 2] : -1;
                int right = i;
                // min[minPtr - 1] -- minimum on interval (left, right)
                int ll = -1;
                int rr = maxPtr;
                while (rr - ll > 1) {
                    int middle = (ll + rr) >>> 1;
                    int lll = middle == 0 ? -1 : max[middle - 1];
                    lll = Math.max(lll, left);
                    if (right - lll - 2 >= limit) {
                        ll = middle;
                    } else {
                        rr = middle;
                    }
                }
                int to = ll;
                ll = -1;
                rr = maxPtr;
                while (rr - ll > 1) {
                    int middle = (ll + rr) >>> 1;
                    if (max[middle] > left) {
                        rr = middle;
                    } else {
                        ll = middle;
                    }
                }
                int from = rr;
                if (from <= to) {
                    answer += from - to + 1;
                }
                --minPtr;
            }

            max[maxPtr++] = i;
            min[minPtr++] = i;
        }
        out.println(answer);
    }
}
