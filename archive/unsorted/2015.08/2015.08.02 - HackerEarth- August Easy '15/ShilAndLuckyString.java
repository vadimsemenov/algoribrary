package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;

public class ShilAndLuckyString {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        final int LETTERS = 26;
        int length = in.nextInt();
        String string = in.next();
        int[] fst = new int[LETTERS];
        int[] snd = new int[LETTERS];
        for (int i = 0; i < length / 2; ++i) {
            ++fst[string.charAt(i) - 'a'];
        }
        for (int i = (length + 1) / 2; i < length; ++i) {
            ++snd[string.charAt(i) - 'a'];
        }
        int answer = 0;
        for (int i = 0; i < LETTERS; ++i) {
            answer += Math.abs(fst[i] - snd[i]);
        }
        if (answer % 2 != 0) throw new AssertionError("WTF?! " + answer);
        answer /= 2;
        answer = Math.min(answer, doit(fst, snd));
        answer = Math.min(answer, doit(snd, fst));
        out.println(answer);
    }

    private int doit(int[] fst, int[] snd) {
        int answer = 0;
        int balance = 0;
        if (fst[fst.length - 1] != 0) {
            answer += fst[fst.length - 1];
            balance += fst[fst.length - 1];
        }
        for (int i = 0; i < fst.length; ++i) {
            balance -= snd[i];
            if (balance < 0) {
                answer -= balance;
                balance = 0;
            }
            balance += fst[i];
        }
        return answer;
    }
}
