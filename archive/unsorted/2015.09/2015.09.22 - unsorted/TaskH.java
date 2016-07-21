package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskH {
    static final int MODULO = 1_000_000_000 + 9;
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        long a = in.nextLong() % MODULO;
        long b = in.nextLong() % MODULO;
        int n = in.nextInt();
//        long a = 1_000_000_000_000_000_000L % MODULO;
//        long b = 1_000_000_000_010_000_000L % MODULO;
//        int n = 10_000_000;
        MagicQueue queue = new MagicQueue(n);
        for (int i = 0; i < n; ++i) {
            queue.push((int) ((a + i) % MODULO));
        }
        long answer = queue.getProduct();
        for (int cur = (int) a; cur != b; cur = (cur + 1) % MODULO) {
            queue.pop();
            queue.push(cur + n);
            answer += queue.getProduct();
        }
        out.println(answer % MODULO);
    }

    static class MagicQueue {
        int[] data;
        int[] product;
        int top, bottom;

        MagicQueue(int size) {
            data = new int[size + 2];
            product = new int[size + 2];
            data[0] = product[0] = 1;
            data[size + 1] = product[size + 1] = 1;
            top = 1;
            bottom = size;
        }

        void push(int val) {
            data[bottom] = val;
            product[bottom] = (int) ((long) val * product[bottom + 1] % MODULO);
            --bottom;
        }

        int getProduct() {
            return (int) ((long) product[bottom + 1] * product[top - 1] % MODULO);
        }

        void pop() {
            if (top == 1) {
                while (bottom < data.length - 2) {
                    data[top] = data[++bottom];
                    product[top] = (int) ((long) data[top] * product[top - 1] % MODULO);
                    ++top;
                }
            }
            --top;
        }
    }
}
