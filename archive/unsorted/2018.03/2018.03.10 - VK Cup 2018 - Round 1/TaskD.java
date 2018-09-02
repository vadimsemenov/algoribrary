package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        char[] fst = in.next().toCharArray();
        char[] snd = in.next().toCharArray();
        int[] af = calcOne(fst);
        int[] as = calcOne(snd);
        int[] sumf = calcParity(fst);
        int[] sums = calcParity(snd);
        int queries = in.nextInt();
        for (int q = 0; q < queries; ++q) {
            int l1 = in.nextInt();
            int r1 = in.nextInt();
            int l2 = in.nextInt();
            int r2 = in.nextInt();
            // count of A at suffix
            int fstSuffix = Math.min(r1 - l1 + 1, af[r1]);
            int sndSuffix = Math.min(r2 - l2 + 1, as[r2]);
            // count of B and C in substring
            int fstQty = sumf[r1] - sumf[l1 - 1];
            int sndQty = sums[r2] - sums[l2 - 1];

            boolean possible = true;
            if (fstSuffix < sndSuffix) {
                possible = false;
            } else {
                boolean canAdd = false;
                if (fstSuffix > sndSuffix) {
                    canAdd = true;
                    if (fstSuffix % 3 != sndSuffix % 3) {
                        fstQty += 2; // apply A -> BC
                    }
                } // now endings are same
                if (fstQty > sndQty || (fstQty == 0 && sndQty != 0 && !canAdd)) {
                    possible = false; // cannot delete B or C, cannot crete from nowhere
                } else if (fstQty % 2 != sndQty % 2) {
                    possible = false; // only A -> BC transformation
                }
            }
            out.print(possible ? "1" : "0");
        }
        out.println();
    }

    private int[] calcOne(char[] string) {
        int[] a = new int[string.length + 1];
        for (int i = 1; i <= string.length; ++i) {
            if (string[i - 1] == 'A') {
                a[i] = 1 + a[i - 1];
            }
        }
        return a;
    }

    private int[] calcParity(char[] string) {
        int[] sum = new int[string.length + 1];
        for (int i = 1; i <= string.length; ++i) {
            sum[i] = sum[i - 1];
            if (string[i - 1] != 'A') {
                sum[i]++;
            }
        }
        return sum;
    }
}
