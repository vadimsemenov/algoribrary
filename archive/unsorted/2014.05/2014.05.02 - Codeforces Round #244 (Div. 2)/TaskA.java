package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int policemanCnt = 0;
        int answer = 0;
        for (int i = 0; i < counter; i++) {
            int current = in.nextInt();
            if (current == -1) {
                if (policemanCnt == 0) answer++;
                else policemanCnt--;
            } else policemanCnt += current;
        }
        out.println(answer);
    }
}
