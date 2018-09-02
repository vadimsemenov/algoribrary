package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskA {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int need = in.nextInt();
        char[] stages = in.next().toCharArray();
        Arrays.sort(stages);
        char next = 'a';
        int answer = 0;
        int have = 0;
        for (int i = 0; i < stages.length && have < need; ++i) {
            if (next <= stages[i]) {
                answer += stages[i] - 'a' + 1;
                next = (char) (stages[i] + 2);
                have++;
            }
        }
        out.println(have < need ? -1 : answer);
    }
}
