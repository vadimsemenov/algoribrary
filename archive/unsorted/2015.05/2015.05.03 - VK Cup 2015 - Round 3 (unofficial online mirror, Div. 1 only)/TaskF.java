package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TaskF {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int limit = in.nextInt();
        int[] time = new int[counter];
        int[] cost = new int[counter];
        List<Integer>[] level = new List[limit];
        for (int i = 0; i < limit; ++i) level[i] = new ArrayList<>();
        for (int i = 0; i < counter; ++i) {
            time[i] = in.nextInt() - 1;
            cost[i] = in.nextInt();
            level[time[i]].add(cost[i]);
        }
        for (int l = 0; l + 1 < limit; ++l) {
            Collections.sort(level[l]);
            for (int i = level[l].size() - 1; i > 0; i -= 2) {
                level[l + 1].add(level[l].get(i) + level[l].get(i - 1));
            }
            if (level[l].size() % 2 == 1) {
                level[l + 1].add(level[l].get(0));
            }
        }
        Collections.sort(level[limit - 1]);
        out.println(level[limit - 1].get(level[limit - 1].size() - 1));
    }
}
