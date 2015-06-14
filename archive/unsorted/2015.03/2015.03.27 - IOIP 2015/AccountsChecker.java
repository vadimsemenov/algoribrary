package tasks;

import net.egork.chelper.checkers.Checker;
import net.egork.chelper.checkers.TokenChecker;
import net.egork.chelper.tester.Verdict;

import java.util.StringTokenizer;

public class AccountsChecker implements Checker {
    private final String parameters;

    public AccountsChecker(String parameters) {
        this.parameters = parameters;
    }

    public Verdict check(String input, String expectedOutput, String actualOutput) {
        if (expectedOutput != null && !expectedOutput.equals("")) {
            return new TokenChecker(parameters).check(input, expectedOutput, actualOutput);
        }
        StringTokenizer inputTokenizer = new StringTokenizer(input);
        int accounts = Integer.parseInt(inputTokenizer.nextToken());
        int hints = Integer.parseInt(inputTokenizer.nextToken());
        int[][] account = new int[accounts][];
        long[][] sum = new long[hints][];
        for (int i = 0; i < hints; ++i) {
            int cnt = Integer.parseInt(inputTokenizer.nextToken());
            account[i] = new int[cnt];
            sum[i] = new long[cnt];
            for (int j = 0; j < cnt; ++j) {
                account[i][j] = Integer.parseInt(inputTokenizer.nextToken()) - 1;
                sum[i][j] = Long.parseLong(inputTokenizer.nextToken());
            }
        }
        StringTokenizer outputTokenizer = new StringTokenizer(actualOutput);
        String verdict = outputTokenizer.nextToken();
        if (verdict.equals("NO") || !verdict.equals("YES")) {
            return Verdict.WA;
        }
        long[] b = new long[accounts];
        for (int i = 0; i < accounts; ++i) {
            b[i] = Long.parseLong(outputTokenizer.nextToken());
        }
        if (outputTokenizer.hasMoreElements()) {
            return Verdict.UNDECIDED;
        }
        for (int i = 0; i < hints; ++i) {
            long day = -1;
            for (int j = 0; j < account[i].length; ++j) {
                long ans = b[account[i][j]];
                if (ans == 0 || sum[i][j] % ans != 0) {
                    return Verdict.WA;
                }
                long _day = sum[i][j] / ans;
                if (day == -1) {
                    day = _day;
                } else if (day != _day) {
                    return Verdict.WA;
                }
            }
        }
        return Verdict.OK;
    }
}
