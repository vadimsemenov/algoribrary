package tasks;

public class PairGame {
    public int maxSum(int a, int b, int c, int d) {
        while (a > 0 && b > 0 && c > 0 && d > 0) {
            if (a == c && b == d) return a + b;
            if (a + b > c + d) {
                if (a > b) a -= b;
                else b -= a;
            } else {
                if (c > d) c -= d;
                else d -= c;
            }
        }
        return -1;
    }
}
