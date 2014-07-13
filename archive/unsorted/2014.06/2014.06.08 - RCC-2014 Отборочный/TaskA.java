package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int tasks = in.nextInt();
        int time = in.nextInt();
        int changeTime = in.nextInt();
        String[] wishes = new String[counter];
        for (int i = 0; i < counter; i++) {
            wishes[i] = in.next();
        }
        int currentTask = 0;
        int changes = 0;
        while (currentTask < tasks) {
            int max = -1;
            for (int i = 0; i < counter; i++) {
                int curLength = 0;
                while (curLength + currentTask < tasks && wishes[i].charAt(currentTask + curLength) == '1')
                    curLength++;
                max = Math.max(max, curLength);
            }
            if (max == -1) throw new AssertionError(currentTask);
            changes++;
            currentTask += max;
        }
        changes--;
        out.println(changes * changeTime + tasks * time);
    }
}
