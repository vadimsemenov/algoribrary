package tasks;

public class PeriodicJumping {
    public int minimalTime(int x, int[] jumpLengths) {
        int[] tmp = jumpLengths;
        jumpLengths = new int[tmp.length * 3];
        for (int i = 0; i < jumpLengths.length; ++i) {
            jumpLengths[i] = tmp[i % tmp.length];
        }
        x = Math.abs(x);
        if (x == 0) return 0;
        long maxSum = 0;
        long minSum = 0;
        for (int i = 0; i < jumpLengths.length; ++i) {
            if (minSum <= x && maxSum >= x) return i;
            long d1 = maxSum - jumpLengths[i];
            long d2 = minSum - jumpLengths[i];
            if (d2 <= 0 && d1 >= 0) minSum = 0;
            else minSum = Math.min(Math.abs(d1), Math.abs(d2));
            maxSum += jumpLengths[i];
        }
        if (minSum <= x && maxSum >= x) return jumpLengths.length;
        int ans = (int) (x / maxSum);
        int need =  (int) (x % maxSum);
        for (int i = 0; i < jumpLengths.length; ++i) {
            if (need <= 0) return ans * jumpLengths.length + i;
            need -= jumpLengths[i];
        }
        return ans * jumpLengths.length + jumpLengths.length;
    }
}
