package tasks;

public class MaximumBipartiteMatchingProblem {
    public long solve(int n1, int n2, int ans, int d) {
        if (n1 > n2) {
            int tmp = n1; n1 = n2; n2 = tmp;
        }
        if (ans == n1) return n1 * 1L * n2;
        if (ans < 2 * d) return -1;

        long result = d * 1L * d;
        result += (ans - d) * 1L * (ans - d);
        result += (ans - d) * 1L * d;
        result += (n1 - ans) * 1L * d;
        result += (n2 - ans) * 1L * (ans - d);

        return result;
    }
}
