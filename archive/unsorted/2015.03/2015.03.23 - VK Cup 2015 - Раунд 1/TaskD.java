package tasks;



import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class TaskD {
    private static final int DAY = 24 * 60 * 60;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int limit = in.nextInt();
        int length = in.nextInt();
        int[] times = new int[counter];
        Integer[] order = new Integer[counter];
        for (int i = 0; i < counter; ++i) {
            order[i] = i;
            times[i] = parseTime(in.next());
        }
        Arrays.sort(order, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(times[o1], times[o2]);
            }
        });
        int[] queue = new int[counter];
        int head = 0;
        int tail = 0;
        int[] id = new int[counter];
        int total = 0;
        int[] cnt = new int[counter + 1];
        int nextId = 1;
        boolean wasMax = false;
        for (int i : order) {
            while (head < tail && times[i] - times[queue[head]] >= length) {
                cnt[id[queue[head]]]--;
                if (cnt[id[queue[head]]] == 0) {
                    total--;
                }
                head++;
            }
            total++;
            queue[tail++] = i;
            if (total > limit) {
                total--;
                id[i] = id[queue[tail - 2]];
            } else {
                id[i] = nextId++;
            }
            if (total == limit) {
                wasMax = true;
            }
            cnt[id[i]]++;
        }
        if (wasMax) {
            out.println(nextId - 1);
            for (int i = 0; i < counter; ++i) {
                out.println(id[i]);
            }
        } else {
            out.println("No solution");
        }
    }

    private int parseTime(String time) {
        if (time.length() != 8) throw new IllegalArgumentException(time);
        int hh = (time.charAt(0) - '0') * 10 + (time.charAt(1) - '0');
        int mm = (time.charAt(3) - '0') * 10 + (time.charAt(4) - '0');
        int ss = (time.charAt(6) - '0') * 10 + (time.charAt(7) - '0');
        return (hh * 60 + mm) * 60 + ss;
    }
}
