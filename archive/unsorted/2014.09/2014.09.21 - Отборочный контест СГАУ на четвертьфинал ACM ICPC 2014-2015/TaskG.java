package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskG {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int sum = in.nextInt();
        int[] diff = new int[counter];
        for (int i = 0; i < counter; ++i) diff[i] = in.nextInt();
        long nom = 1;
        int cur = -1;
        while (cur < counter) {
            if (cur == counter - 1 || nom * diff[cur + 1] > sum) break;
            cur++;
            nom *= diff[cur];
        }
        int answer = 0;
        while (sum > 0) {
            answer += sum / nom;
            sum %= nom;
            if (cur < 0) break;
            nom /= diff[cur];
            cur--;
        }
        out.println(answer);
    }
}
