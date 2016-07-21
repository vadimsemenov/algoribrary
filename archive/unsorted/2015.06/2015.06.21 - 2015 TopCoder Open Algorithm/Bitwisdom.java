package tasks;

public class Bitwisdom {
    public double expectedActions(int[] p) {
        int n = p.length;
        double[][][] dp = new double[n][n][2];
        dp[0][0][0] = (100 - p[0]) / 100.;
        dp[0][0][1] = p[0] / 100.;
        for (int i = 1; i < n; ++i) {
            for (int j = 0; j <= i; ++j) {
                dp[i][j][0] = (100 - p[i]) / 100. * dp[i - 1][j][0];
                dp[i][j][1] = (      p[i]) / 100. * dp[i - 1][j][1];
                if (j > 0) {
                    dp[i][j][0] += (100 - p[i]) / 100. * dp[i - 1][j - 1][1];
                    dp[i][j][1] += (      p[i]) / 100. * dp[i - 1][j - 1][0];
                }
            }
        }
//        System.err.println(Arrays.deepToString(dp));
        double answer = dp[n - 1][0][1];
        for (int i = 1; i < n; ++i) {
            answer += (dp[n - 1][i][0] + dp[n - 1][i][1]) * i;
        }
        return answer;
    }
}
