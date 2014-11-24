package tasks;

public class SimilarRatingGraph {
    public double maxLength(int[] date, int[] rating) {
        double maxLength = 0;
        int[][] tans = new int[date.length - 1][2];
        for (int i = 1; i < date.length; ++i) {
            int n = rating[i] - rating[i - 1];
            int d = date[i] - date[i - 1];
            int g = gcd(Math.abs(n), Math.abs(d));
            tans[i - 1][0] = n / g;
            tans[i - 1][1] = d / g;
        }
        for (int i = 0; i < tans.length; ++i) {
            outer:
            for (int j = i + 1; j < tans.length; ++j) {
                for (int len = 0; j + len <= tans.length; ++len) {
                    if (j + len == tans.length || tans[i + len][0] != tans[j + len][0] || tans[i + len][1] != tans[j + len][1]) {
                        if (len == 0) continue outer;
                        System.err.println(i + " " + j + " " + len);
                        maxLength = Math.max(
                                maxLength,
                                Math.max(calculate(date, rating, j, len),
                                        calculate(date, rating, i, len))
                        );
                        continue outer;
                    }
                }
            }
        }
        return maxLength;
    }

    private double calculate(int[] date, int[] rating, int from, int len) {
        double result = 0;
        for (int i = from + 1; i <= Math.min(date.length, from + len); ++i) {
            result += Math.sqrt(hypot(date[i] - date[i - 1], rating[i] - rating[i - 1]));
        }
        return result;
    }

    private long hypot(long a, long b) {
        a *= a;
        b *= b;
        return a + b;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}
