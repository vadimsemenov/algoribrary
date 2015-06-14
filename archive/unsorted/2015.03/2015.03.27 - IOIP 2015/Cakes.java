package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Cakes {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int totalTime = in.nextInt();
        int[] xs = new int[counter];
        int[] ts = new int[counter];
        Integer[] order = new Integer[counter];
        for (int i = 0; i < counter; ++i) {
            order[i] = i;
            xs[i] = in.nextInt();
            ts[i] = in.nextInt();
        }
        Arrays.sort(order, new Comparator<Integer>() {
            @Override
            public int compare(Integer first, Integer second) {
                return Integer.compare(ts[first], ts[second]);
            }
        });
        int[] position = new int[counter];
        for (int i = 0; i < counter; ++i) {
            position[order[i]] = i;
        }
        boolean[] deleted = new boolean[counter];
        int ptr = 0;
        int sum = 0;
        int cnt = 0;
        while (ptr < counter && xs[counter - 1] + sum + ts[order[ptr]] <= totalTime) {
            sum += ts[order[ptr++]];
            ++cnt;
        }
        int answer = cnt;
        for (int i = counter - 1; i >= 0; --i) {
            int j;
            for (j = i; j >= 0 && xs[j] == xs[i]; --j) {
                if (deleted[position[j]]) throw new AssertionError(j + " was deleted");
                deleted[position[j]] = true;
                if (position[j] < ptr) {
                    sum -= ts[j];
                    cnt--;
                }
            }
            int X = j >= 0 ? xs[j] : 0;
            while (true) {
                while (ptr < counter && deleted[ptr]) {
                    ++ptr;
                }
                if (ptr < counter && X + sum + ts[order[ptr]] <= totalTime) {
                    sum += ts[order[ptr++]];
                    ++cnt;
                } else {
                    break;
                }
            }
            i = j + 1;
//            if (X + sum > totalTime) throw new AssertionError(X + " " + sum + " " + cnt);
            answer = Math.max(answer, cnt);
        }
        out.println(answer);
    }
}
