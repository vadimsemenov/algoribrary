package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int variants = in.nextInt();
        int limit = in.nextInt();
        int t0 = in.nextInt();
        outer: for (int i = 0; i < variants; ++i) {
            int counter = in.nextInt();
            int sum = 0;
            int maxTime = 0;
            for (int cnt = 0; cnt < counter; ++cnt) {
                int time = in.nextInt();
                maxTime = Math.max(maxTime, time);
                sum += time;
                if (sum > limit) {
                    sum += t0 - maxTime;
                    if (sum <= limit) {
                        while (++cnt < counter) {
                            time = in.nextInt();
                            if (sum + time <= limit) {
                                sum += time;
                            } else {
                                out.println(cnt);
                                break;
                            }
                        }
                        if (cnt == counter) {
                            out.println(cnt);
                        }
                    } else {
                        out.println(cnt);
                    }
                    while (++cnt < counter) in.nextInt();
                    continue outer;
                }
            }
            out.println(counter);
        }
    }
}
