package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int queries = in.nextInt();
        int[] array = in.nextIntArray(count);
        int[] min = new int[queries + 1];
        Arrays.fill(min, count);
        int[] max = new int[queries + 1];
        for (int i = 0; i < array.length; ++i) {
            min[array[i]] = Math.min(min[array[i]], i);
            max[array[i]] = Math.max(max[array[i]], i);
        }
        int[] stack = new int[queries + 1];
        int size = 0;
        for (int i = 0; i < array.length; ++i) {
            int x = array[i];
            if (x == 0) continue;
            while (size > 0 && max[stack[size - 1]] <= i) {
                --size;
            }
            if (size > 0 && stack[size - 1] > x) {
                out.println("NO");
                return;
            }
            if (min[x] == i) {
                stack[size++] = x;
            }
        }
        boolean wasZero = min[0]       <= max[0];
        boolean wasLast = min[queries] <= max[queries];
        if (!wasLast && !wasZero) {
            out.println("NO");
            return;
        }
        if (!wasLast) {
            for (int i = 0; i < array.length; ++i) {
                if (array[i] == 0) {
                    array[i] = queries;
                    break;
                }
            }
        }
        out.println("YES");
        int last = 1;
        for (int number : array) {
            if (number == 0) {
                out.print(last + " ");
            } else {
                out.print(number + " ");
                last = number;
            }
        }
        out.println();
    }
}
