package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] position = new int[counter];
        for (int i = 0; i < counter; ++i) position[in.nextInt() - 1] = i;
        int answer = 1;
        for (int i = 1; i < counter; ++i) if (position[i] < position[i - 1]) ++answer;
        out.println(answer);
    }
}
