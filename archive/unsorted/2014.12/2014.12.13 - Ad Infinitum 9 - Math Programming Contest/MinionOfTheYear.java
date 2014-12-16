package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class MinionOfTheYear {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int prime = in.nextInt();
        int queries = in.nextInt();
        int[] a = new int[queries];
        int[] b = new int[queries];
        int[] c = new int[queries];
        int[] d = new int[queries];
        for (int q = 0; q < queries; ++q) {
            a[q] = in.nextInt();
            b[q] = in.nextInt();
            c[q] = in.nextInt() % prime;
            d[q] = in.nextInt() % prime;
        }
        minPow = new int[prime];
        time = new int[prime];
        long[] answer = bruteForce(prime, a, b, c, d);
        for (long ans : answer) {
            out.println(ans == Long.MAX_VALUE ? "wala" : ans);
        }
    }

    private int[] minPow;
    private int[] time;
    private int currentTime;

    private long[] bruteForce(int prime, int[] a, int[] b, int[] c, int[] d) {
        long[] answer = new long[a.length];
        for (int q = 0; q < a.length; ++q) {
            currentTime++;

            int num = c[q];
            int currentPow = 1;
            while (time[num] != currentTime) {
                time[num] = currentTime;
                minPow[num] = currentPow;
                currentPow++;
                num = (int) (num * 1L * c[q] % prime);
            }
            num = d[q];
            currentPow = 1;
            long currentAnswer = Long.MAX_VALUE;
            do {
                if (time[num] == currentTime) {
                    currentAnswer = Math.min(currentAnswer, a[q] * 1L * minPow[num] + b[q] * 1L * currentPow);
                }
                currentPow++;
                num = (int) (num * 1L * d[q] % prime);
            } while (num != d[q]);
            answer[q] = currentAnswer;
        }
        return answer;
    }
}
