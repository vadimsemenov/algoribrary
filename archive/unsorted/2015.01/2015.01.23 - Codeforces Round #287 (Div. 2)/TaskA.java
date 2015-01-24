package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int types = in.nextInt();
        int freeDays = in.nextInt();
        final int[] needDays = new int[types];
        Integer[] order = new Integer[types];
        for (int i = 0; i < types; ++i) {
            order[i] = i;
            needDays[i] = in.nextInt();
        }
        Arrays.sort(order, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(needDays[o1], needDays[o2]);
            }
        });
        int answer = 0;
        while (answer < types && freeDays >= needDays[order[answer]]) {
            freeDays -= needDays[order[answer]];
            answer++;
        }
        out.println(answer);
        for (int i = 0; i < answer; ++i) {
            if (i > 0) out.print(' ');
            out.print(order[i] + 1);
        }
        out.println();
    }
}
