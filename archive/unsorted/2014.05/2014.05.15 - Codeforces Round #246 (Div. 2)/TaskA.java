package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int times = in.nextInt();
        int number = 0;
        for (int i = 0; i < counter; i++) {
            int x = in.nextInt();
            if (5 - x >= times) number++;
        }
        out.println(number / 3);
    }
}
