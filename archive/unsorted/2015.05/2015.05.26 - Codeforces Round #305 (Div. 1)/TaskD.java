package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskD {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[] xs = new int[counter];
        int[] ys = new int[counter];
        int mx, my;
        for (int i = 0; i < counter; ++i) {
            xs[i] = in.nextInt();
            ys[i] = in.nextInt();
        }
        {
            int[] buf = xs.clone();
            Arrays.sort(buf);
            int ptr = 1;
            for (int i = 1; i < buf.length; ++i) {
                if (buf[i] != buf[i - 1]) {
                    buf[ptr++] = buf[i];
                }
            }
            for (int i = 0; i < xs[i]; ++i) xs[i] = Arrays.binarySearch(buf, 0, ptr, xs[i]);
            mx = ptr;
        }
        {
            int[] buf = ys.clone();
            Arrays.sort(buf);
            int ptr = 1;
            for (int i = 1; i < buf.length; ++i) {
                if (buf[i] != buf[i - 1]) {
                    buf[ptr++] = buf[i];
                }
            }
            for (int i = 0; i < ys[i]; ++i) ys[i] = mx + Arrays.binarySearch(buf, 0, ptr, ys[i]);
            my = ptr;
        }
        int FAKE = mx + my;
        {
            int[] tmp = new int[counter + mx + my];
            System.arraycopy(xs, 0, tmp, 0, counter);
            Arrays.fill(tmp, counter, tmp.length, FAKE);
            xs = tmp;
        }
        {
            int[] tmp = new int[counter + mx + my];
            System.arraycopy(ys, 0, tmp, 0, counter);
            for (int i = counter; i < tmp.length; ++i) {
                tmp[i] = i - counter;
            }
            ys = tmp;
        }
        List<Integer>[] graph = new List[mx + my + 1];
        for (int i = 0; i < graph.length; ++i) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < counter; ++i) {
            graph[xs[i]].add(i);
            graph[ys[i]].add(i);
        }
        for (int i = 0; i < mx + my; ++i) {
            if (graph[i].size() % 2 == 1) {
                graph[FAKE].add(counter + i);
                graph[i].add(counter + i);
                if (xs[counter + i] != FAKE || ys[counter + i] != i) throw new AssertionError(xs[counter + i] + " " + ys[counter + i]);
            }
        }

        char[] answer = new char[counter];
        Arrays.fill(answer, 'x');

        int[] nextEdge = new int[graph.length];
        List<Integer> stack = new ArrayList<>(graph.length);
        List<Integer> edgeStack = new ArrayList<>(graph.length);
        boolean[] used = new boolean[xs.length];
        int top = 0;
        for (int start = FAKE; start >= 0; --start) {
            if (nextEdge[start] >= graph[start].size()) continue;
            edgeStack.add(counter + mx + my);
            stack.add(top++, start);
            boolean blue = true;
            while (top > 0) {
                int current = stack.get(top - 1);
                int edge = -1;
                boolean found = false;
                while (nextEdge[current] < graph[current].size()) {
                    edge = graph[current].get(nextEdge[current]++);
                    if (!used[edge]) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    edge = edgeStack.get(top - 1);
                    if (edge < counter) {
                        answer[edge] = blue ? 'b' : 'r';
                        blue = !blue;
                    }
                    --top;
                } else {
                    used[edge] = true;
                    int next = current ^ xs[edge] ^ ys[edge];
                    edgeStack.add(top, edge);
                    stack.add(top++, next);
                }
            }
        }

        out.println(String.valueOf(answer));
    }
}
