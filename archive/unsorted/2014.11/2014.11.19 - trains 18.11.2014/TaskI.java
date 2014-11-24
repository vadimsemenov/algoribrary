package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskI {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        int[] permutation = new int[length];
        int[] at = new int[length];
        for (int i = 0; i < length; ++i) {
            permutation[i] = at[i] = i;
        }
        print(permutation, out);
        int lcs = in.nextInt();
        if (lcs == length) return;

        int next = length - 1;
        int initial = at[next];
        int[] best = permutation.clone();
        int[] bestAt = at.clone();

        for (int iter = 0; iter < 5 * length * length; ++iter) {
            if (at[next] == initial) {
                swap(permutation, at, at[next], at[next] - 1);
                iter--;
                continue;
            }

            print(permutation, out);
            int curLCS = in.nextInt();
            if (curLCS > lcs) {
                best = permutation.clone();
                bestAt = at.clone();
                lcs = curLCS;
                while (at[next] != 0) {
                    swap(permutation, at, at[next], at[next] - 1);
                }
            }
            if (lcs == length) return;

            if (at[next] == 0 || (at[next] == 1 && initial == 0)) {
                next = next - 1;
                permutation = best.clone();
                at = bestAt.clone();
                initial = at[next];
                while (at[next] != length - 1) {
                    swap(permutation, at, at[next], at[next] + 1);
                }
            } else {
                swap(permutation, at, at[next], at[next] - 1);
            }
        }

        throw new RuntimeException("polundra!");
    }

    private void print(int[] permutation, PrintWriter out) {
        for (int i = 0; i < permutation.length; ++i) {
            if (i != 0) out.print(' ');
            out.print(permutation[i] + 1);
        }
        out.println();
        out.flush();
    }

    private void swap(int[] permutation, int[] at, int i, int j) {
        int tmp = permutation[i];
        permutation[i] = permutation[j];
        permutation[j] = tmp;
        at[permutation[i]] = i;
        at[permutation[j]] = j;
    }
}
