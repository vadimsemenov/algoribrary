package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class TaskC {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int pupils = in.nextInt();
        int[] sizes = in.nextIntArray(counter);
        long left = -1;
        long right = counter + 2;
        for (int i = 0; i < counter; ++i) {
            if (sizes[i] > 0) right += sizes[i];
        }
        while (right - left > 1) {
            long middle = (right + left) >>> 1;
            int ptr = counter - 1;
            int inLast = sizes[ptr];
            boolean check = true;
            for (int i = 0; i < pupils; ++i) {
                long hasTime = middle;
                boolean ad = true;
                while (hasTime > 0 && ptr >= 0) {
                    while (ptr >= 0 && inLast == 0) {
                        --ptr;
                        inLast = ptr >= 0 ? sizes[ptr] : 0;
                    }
                    if (inLast > 0 && ad) {
                        ad = false;
                        hasTime -= ptr + 1;
                    }
                    if (hasTime >= inLast) {
                        hasTime -= inLast;
                        inLast = 0;
                    } else {
                        inLast -= hasTime;
                        hasTime = 0;
                    }
                }
                if (hasTime < 0) {
                    check = false;
                    break;
                }
            }
            while (ptr >= 0 && inLast == 0) {
                --ptr;
                inLast = ptr >= 0 ? sizes[ptr] : 0;
            }
            if (inLast != 0) {
                check = false;
            }
            if (check) {
                right = middle;
            } else {
                left = middle;
            }
        }
        out.println(right);
    }
}
