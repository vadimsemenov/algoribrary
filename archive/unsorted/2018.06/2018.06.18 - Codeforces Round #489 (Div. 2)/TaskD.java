package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int qty = in.nextInt();
        int ratio = in.nextInt();
        int[] array = in.nextIntArray(qty);
        int[] nextNotOne = new int[qty];
        int next = qty;
        for (int i = qty - 1; i >= 0; --i) {
            nextNotOne[i] = next;
            if (array[i] != 1) {
                next = i;
            }
        }
        int answer = ratio == 1 ? qty : 0;
        int left = next;
        for (int i = next; i < qty; i = nextNotOne[i]) {
            long product = array[i];
            long sum = array[i] + (nextNotOne[i] - i - 1);
            for (int pos = nextNotOne[i]; pos < qty; pos = nextNotOne[pos]) {
                sum += array[pos];
                if (product > Long.MAX_VALUE / array[pos]) {
                    break;
                }
                product *= array[pos];
                int right = nextNotOne[pos] - pos - 1;
                long diff = product - sum * ratio;
                if (diff >= 0 && diff % ratio == 0 && diff / ratio <= left + right) {
                    answer += Math.min(Math.min(diff / ratio, left + right - diff / ratio), Math.min(left, right)) + 1;
                }
                sum += right;
            }
            left = nextNotOne[i] - i - 1;
        }
        out.println(answer);
    }
}
