package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public final class TaskF {
    public void solve(int __, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int foo = in.nextInt();
        int[] a = in.nextIntArray(n);
        int m = in.nextInt();
        int bar = in.nextInt();
        int[] b = in.nextIntArray(m);
        int answer = 2;
        Map<Integer, Integer> map = new HashMap<>();
        for (int step = 2; step < (1 << 30); step <<= 1) {
            map.clear();
            answer = Math.max(answer, countReminders(a, map, step, 0));
            answer = Math.max(answer, countReminders(b, map, step, step >>> 1));
        }
        out.println(answer);
    }

    private int countReminders(int[] a, Map<Integer, Integer> reminders, int step, int ad) {
        int max = 0;
        for (int i : a) {
            int reminder = (i + ad) % step;
            int count = reminders.getOrDefault(reminder, 0) + 1;
            reminders.put(reminder, count);
            max = Math.max(max, count);
        }
        return max;
    }
}
