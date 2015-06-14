package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class DIVNINE {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        char[] number = in.next().toCharArray();
        int canIncrease = 0;
        int canDecrease = 0;
        int remainder = 0;
        boolean first = true;
        for (char digit : number) {
            remainder += digit - '0';
            if (first && number.length > 1) {
                first = false;
                canIncrease += '9' - digit;
                canDecrease += digit - '1';
            } else {
                canIncrease += '9' - digit;
                canDecrease += digit - '0';
            }
        }
        remainder %= 9;
        int fst = Integer.MAX_VALUE;
        int snd = Integer.MAX_VALUE;
        if (remainder <= canDecrease) {
            fst = Math.min(fst, remainder);
        }
        if (9 - remainder <= canIncrease) {
            snd = Math.min(snd, 9 - remainder);
        }
        out.println(Math.min(fst, snd));
    }
}
