package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskD1 {
    private static final String IMPOSSIBLE = "IMPOSSIBLE";
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] maxChild = new int[counter];
        int[] maxLeftChild = new int[counter];
        for (int i = 0; i < counter; ++i) maxChild[i] = maxLeftChild[i] = i;
        int restrictions = in.nextInt();
        int[] parents = new int[restrictions];
        int[] children = new int[restrictions];
        boolean[] needCheck = new boolean[restrictions];
        for (int i = 0; i < restrictions; ++i) {
            parents[i] = in.nextInt() - 1;
            children[i] = in.nextInt() - 1;
            if (children[i] <= parents[i]) {
                out.println(IMPOSSIBLE);
                return;
            }
            String direction = in.next();
            if (direction.equals("LEFT")) {
                maxLeftChild[parents[i]] = Math.max(maxLeftChild[parents[i]], children[i]);
            } else if (direction.equals("RIGHT")) {
                needCheck[i] = true;
            } else throw new AssertionError(direction);
            maxChild[parents[i]] = Math.max(maxChild[parents[i]], children[i]);
        }
        {
            int[] stack = new int[counter];
            int top = 0;
            for (int i = counter - 1; i >= 0; --i) {
                int lastTop = top;
                while (top > 0 && stack[top - 1] <= maxChild[i]) {
                    if (stack[top - 1] <= maxLeftChild[i]) {
                        maxLeftChild[i] = Math.max(maxLeftChild[i], maxChild[stack[top - 1]]);
                    }
                    maxChild[i] = Math.max(maxChild[i], maxChild[stack[top - 1]]);
                    top--;
                }
                while (lastTop > top) {
                    if (maxChild[stack[lastTop - 1]] <= maxLeftChild[i]) {
                        maxChild[stack[lastTop - 1]] = maxLeftChild[i];
                    } else {
                        maxChild[stack[lastTop - 1]] = maxChild[i];
                    }
                    lastTop--;
                }
                stack[top++] = i;
            }
//            System.err.println(Arrays.toString(maxChild));
//            System.err.println(Arrays.toString(maxLeftChild));
        }
        for (int i = 0; i < restrictions; ++i) {
            if (needCheck[i]) {
                if (!(maxLeftChild[parents[i]] < children[i] && children[i] <= maxChild[parents[i]])) {
                    out.println(IMPOSSIBLE);
                    return;
                }
            }
        }
        int[] answer = new int[counter];
        int[] succ = new int[counter];
        int[] pred = new int[counter];
        for (int i = 0; i < counter; ++i) {
            succ[i] = i + 1;
            pred[i] = i - 1;
        }
        for (int i = 0, ptr = 0; i < counter; i++) {
            int idx = ptr + (maxLeftChild[i] - i);
            answer[idx] = i + 1;
            if (pred[idx] >= 0) {
                succ[pred[idx]] = succ[idx];
            }
            if (succ[idx] < counter) {
                pred[succ[idx]] = pred[idx];
            }
            if (maxLeftChild[i] == i) {
                ptr = succ[ptr];
            }
        }
        for (int i = 0; i < counter; ++i) {
            if (i > 0) out.print(' ');
            out.print(answer[i]);
        }
        out.println();
    }
}
