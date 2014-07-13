package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int coldCount = in.nextInt();
        int warmCount = in.nextInt();
        long cold = in.nextInt();
        long warm = in.nextInt();
        int counter = in.nextInt();
        int[] coldSizes = new int[coldCount];
        int[] warmSizes = new int[warmCount];
        for (int i = 0; i < coldCount; i++) coldSizes[i] = in.nextInt();
        for (int i = 0; i < warmCount; i++) warmSizes[i] = in.nextInt();
        
    }

    private long gcd(long a, long b) {
        return a == 0 ? b : gcd(b % a, a);
    }
}
