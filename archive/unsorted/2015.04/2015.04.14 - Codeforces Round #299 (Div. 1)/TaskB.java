package tasks;

import algoribrary.io.InputReader;
import algoribrary.strings.StringUtils;

import java.io.PrintWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int counter = in.nextInt();
        char[] word = in.next().toCharArray();
        int[] z = StringUtils.buildZFunction(word);
        int free = 0;
        int last = 0;
        for (int i = 0; i < counter; ++i) {
            int begin = in.nextInt() - 1;
            if (last < begin) {
                free += begin - last;
            } else if (last > begin && z[begin - (last - word.length)] < last - begin) {
                out.println(0);
                return;
            }
            last = begin + word.length;
        }
        if (last < n) {
            free += n - last;
        }
        final int alphabet = 26;
        final int modulo = 1_000_000_000 + 7;
        long answer = 1;
        for (int i = 0; i < free; ++i) {
            answer = (answer * alphabet) % modulo;
        }
        out.println(answer);
    }
}
