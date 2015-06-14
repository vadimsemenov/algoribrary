package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[] x = new int[counter];
        int[] y = new int[counter];
        int[] cnt = new int[counter];
        int total = 0;
        for (int i = 0; i < counter; ++i) {
            x[i] = in.nextInt();
            y[i] = in.nextInt();
            cnt[i] = in.nextInt();
            total += cnt[i];
        }
        Integer[] order = new Integer[counter];
        Integer[] anotherOrder = new Integer[counter];
        Integer[] yetAnotherOrder = new Integer[counter];
        for (int i = 0; i < counter; i++) {
            order[i] = i;
            anotherOrder[i] = i;
            yetAnotherOrder[i] = i;
        }
        Arrays.sort(order, new Comparator<Integer>() {
            @Override
            public int compare(Integer first, Integer second) {
                return y[first] != y[second] ? y[first] - y[second] : x[first] - x[second];
            }
        });
        int answer = total;
        for (int i = 0; i < counter; ++i) {
            final int current = order[i];
            final int finalI = i;
            Arrays.sort(anotherOrder, new Comparator<Integer>() {
                @Override
                public int compare(Integer first, Integer second) {
                    if (first <= finalI && second <= finalI) return 0;
                    if (first <= finalI) return 1;
                    if (second <= finalI) return -1;
                    first = order[first];
                    second = order[second];
                    int x1 = x[first] - x[current];
                    int x2 = x[second] - x[current];
                    int y1 = y[first] - y[current];
                    int y2 = y[second] - y[current];
                    return -(x1 * y2 - x2 * y1);
                }
            });

            Arrays.sort(yetAnotherOrder, new Comparator<Integer>() {
                @Override
                public int compare(Integer first, Integer second) {
                    if (first >= finalI && second >= finalI) return 0;
                    if (first >= finalI) return 1;
                    if (second >= finalI) return -1;
                    first = order[first];
                    second = order[second];
                    int x1 = x[first] - x[current];
                    int x2 = x[second] - x[current];
                    int y1 = y[first] - y[current];
                    int y2 = y[second] - y[current];
                    return -(x1 * y2 - x2 * y1);
                }
            });
            int sum = 0;
            for (int j = 0; j < i; ++j) {
                sum += cnt[order[j]];
            }
            int ptr = 0;
            for (int k = 0; k < counter - i - 1; ++k) {
                int dot = order[anotherOrder[k]];
                while (ptr < i && ccw(x[current], y[current], x[dot], y[dot], x[order[yetAnotherOrder[ptr]]], y[order[yetAnotherOrder[ptr]]]) >= 0) {
                    sum -= cnt[order[yetAnotherOrder[ptr]]];
                    ++ptr;
                }
                for (int mul1 = 0; mul1 < 2; ++mul1) for (int mul2 = 0; mul2 < 2; ++mul2) {
                    answer = Math.min(answer, Math.abs(total - 2 * (sum + mul1 * cnt[current] + mul2 * cnt[dot])));
                }
                sum += cnt[dot];
            }
        }
        out.println(answer);
    }

    private int ccw(int x0, int y0, int x1, int y1, int x2, int y2) {
        int dx1 = x1 - x0;
        int dx2 = x2 - x0;
        int dy1 = y1 - y0;
        int dy2 = y2 - y0;
        return dx1 * dy2 - dx2 * dy1;
    }
}
