package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskA {
    private static final int HOURS = 24;
    private static final int MINUTES = 60;

    public void solve(int __, InputReader in, PrintWriter out) {
        String fst = in.next();
        String snd = in.next();
        int counter = in.nextInt() + 2;
        int[] hh = new int[counter];
        int[] mm = new int[counter];
        hh[0] = getHours(fst);
        mm[0] = getMinutes(fst);
        hh[1] = getHours(snd);
        mm[1] = getMinutes(snd);
        for (int i = 2; i < counter; ++i) {
            String time = in.next();
            hh[i] = getHours(time);
            mm[i] = getMinutes(time);
        }
        int[][] cost = new int[counter][];
        for (int i = 1; i < counter; ++i) {
            cost[i] = new int[i];
            for (int j = 0; j < i; ++j) {
                cost[i][j] = getCost(hh[i], mm[i], hh[j], mm[j]);
            }
        }
        int[][] dp = new int[counter][counter];
        for (int[] d : dp) Arrays.fill(d, Integer.MAX_VALUE / 3);
        dp[0][1] = 0;
        for (int i = 0; i + 1 < counter; ++i) {
            for (int j = 0; j + 1 < counter; ++j) {
                int k = Math.max(i, j) + 1;
                dp[k][j] = Math.min(dp[k][j], dp[i][j] + cost[k][i]);
                dp[i][k] = Math.min(dp[i][k], dp[i][j] + cost[k][j]);
            }
        }
        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < counter; ++i) {
            answer = Math.min(answer, Math.min(dp[counter - 1][i], dp[i][counter - 1]));
        }
        out.println(answer);
    }

    private int getCost(int targetHH, int targetMM, int initHH, int initMM) {
        int sameMM = getSameCost(targetMM, initMM);
        int forwardMM = getForwardCost(targetMM, initMM, MINUTES);
        int backwardMM = getBackwardCost(targetMM, initMM, MINUTES);
        return Math.min(sameMM + getBestCost(targetHH, initHH),
               Math.min(forwardMM + getBestCost(targetHH, nextHour(initHH)),
                        backwardMM + getBestCost(targetHH, prevHour(initHH)))
        );
    }

    private int getBestCost(int target, int init) {
        return Math.min(getSameCost(target, init),
                Math.min(getForwardCost(target, init, HOURS), getBackwardCost(target, init, HOURS))
        );
    }

    private int getSameCost(int target, int init) {
        return Math.abs(target - init);
    }

    private int getForwardCost(int target, int init, int mod) {
        return target + mod - init;
    }

    private int getBackwardCost(int target, int init, int mod) {
        return init + mod - target;
    }

    private int prevHour(int hour) {
        return hour == 0 ? HOURS - 1 : hour - 1;
    }

    private int nextHour(int hour) {
        return hour == HOURS ? 0 : hour + 1;
    }

    private int getHours(String time) {
        return (time.charAt(0) - '0') * 10 + time.charAt(1) - '0';
    }

    private int getMinutes(String time) {
        return (time.charAt(3) - '0') * 10 + time.charAt(4) - '0';
    }
}
