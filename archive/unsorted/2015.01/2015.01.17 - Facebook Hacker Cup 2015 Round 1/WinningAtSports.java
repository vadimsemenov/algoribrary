package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class WinningAtSports {
    private static final int MODULO = 1_000_000_007;
    private static final int MAX_SCORE = 2_000;
    
    private static final int[][] stressFree = new int[MAX_SCORE + 1][MAX_SCORE + 1];
    private static final int[][] aux = new int[MAX_SCORE + 1][MAX_SCORE + 1];

    static {
        long time0 = System.currentTimeMillis();

        stressFree[1][0] = 1;
        for (int i = 2; i <= MAX_SCORE; ++i) {
            for (int j = 0; j < i; ++j) {
                if (j > 0) stressFree[i][j] += stressFree[i][j - 1];
                if (i - 1 > j) stressFree[i][j] += stressFree[i - 1][j];
                if (stressFree[i][j] >= MODULO) stressFree[i][j] -= MODULO;
            }
        }

        long time1 = System.currentTimeMillis();
        System.err.println("stress-free ready in " + (time1 - time0) + ".ms");

        aux[0][0] = 1;
        for (int j = 1; j <= MAX_SCORE; ++j) {
            for (int i = 0; i <= j; ++i) {
                if (i > 0) aux[i][j] += aux[i - 1][j];
                if (i < j) aux[i][j] += aux[i][j - 1];
                if (aux[i][j] >= MODULO) aux[i][j] -= MODULO;
            }
        }

        long time2 = System.currentTimeMillis();
        System.err.println("aux ready in " + (time2 - time1) + ".ms");
    }

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        String[] score = in.next().split("-");
        int my = Integer.parseInt(score[0]);
        int his = Integer.parseInt(score[1]);
        assert my > his : my + " <= " + his;
        int stressful = 0;
        for (int i = 0; i < his; ++i) {
            stressful += aux[i][his - 1];
            if (stressful >= MODULO) stressful -= MODULO;
        }
        if (his == 0) stressful = 1;
        print(out, testNumber, stressFree[my][his] + " " + stressful);
    }

    private void print(PrintWriter out, int testNumber, String answer) {
               out.println("Case #" + testNumber + ": " + answer);
        System.err.println("Case #" + testNumber + ": " + answer);
    }
}
