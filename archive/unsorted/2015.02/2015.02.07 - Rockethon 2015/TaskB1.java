package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskB1 {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        int no = in.nextInt();
        int[] permutation = new int[length];
        for (int i = 0; i < length; ++i) {
            permutation[i] = i + 1;
        }
        long max = -1;
        long number = 0;
        int[] answer = null;
        do {
            long f = 0;
            for (int i = 0; i < length; ++i) {
                int currentMin = length + 1;
                for (int j = i; j < length; ++j) {
                    currentMin = Math.min(currentMin, permutation[j]);
                    f += currentMin;
                }
            }
            if (f > max) {
                max = f;
                number = 1;
            } else if (max == f) {
                number++;
            }
            if (max == f && number == no) {
                answer = permutation.clone();
            }
        } while (nextPermutation(permutation));
        for (int i = 0; i < length; ++i) {
            if (i > 0) out.print(' ');
            out.print(answer[i]);
        }
        out.println();
    }

    private boolean nextPermutation(int[] permutation) {
        int idx = -1;
        for (int i = permutation.length - 1; i > 0; --i) {
            if (permutation[i] > permutation[i - 1]) {
                idx = i - 1;
                break;
            }
        }
        if (idx == -1) return false;
        int minIdx = idx + 1;
        for (int i = idx + 1; i < permutation.length; ++i) {
            if (permutation[i] > permutation[idx] && permutation[i] < permutation[minIdx]) {
                minIdx = i;
            }
        }
        int tmp = permutation[idx];
        permutation[idx] = permutation[minIdx];
        permutation[minIdx] = tmp;
        Arrays.sort(permutation, idx + 1, permutation.length);
        return true;
    }
}
