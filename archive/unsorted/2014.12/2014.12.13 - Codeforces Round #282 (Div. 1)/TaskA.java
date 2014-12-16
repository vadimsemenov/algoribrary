package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        char[] sequence = in.next().toCharArray();
        int cnt = 0;
        for (int i = 0; i < sequence.length; ++i) if (sequence[i] == '#') cnt++;
        int[] answer = new int[cnt];
        cnt = 0;
        int balance = 0;
        for (int i = 0; i < sequence.length; ++i) {
            if (sequence[i] == '(') {
                balance++;
            } else if (sequence[i] == ')') {
                balance--;
            } else if (sequence[i] == '#') {
                answer[cnt++] = 1;
                balance--;
            } else throw new AssertionError(sequence[i]);

            if (balance < 0) {
                out.println(-1);
                return;
            }
        }
        answer[answer.length - 1] += balance;
        cnt = 0;
        balance = 0;
        for (int i = 0; i < sequence.length; ++i) {
            if (sequence[i] == '(') {
                balance++;
            } else if (sequence[i] == ')') {
                balance--;
            } else if (sequence[i] == '#') {
                balance -= answer[cnt++];
            } else throw new AssertionError(sequence[i]);

            if (balance < 0) {
                out.println(-1);
                return;
            }
        }
        if (balance != 0) throw new AssertionError(balance);
        for (int ans : answer) {
            out.println(ans);
        }
    }
}
