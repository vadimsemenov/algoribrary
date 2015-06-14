package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.math.BigInteger;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] f = new int[counter];
        for (int i = 0; i < counter; ++i) {
            f[i] = in.nextInt() - 1;
        }
        long lcm = 1;
        int globalOffset = 0;
        for (int i = 0; i < counter; ++i) {
            int[] time = new int[counter];
            int current = i;
            int currentTime = 1;
            while (time[current] == 0) {
                time[current] = currentTime++;
                current = f[current];
            }
            int cycleLength = currentTime - time[current];
            int offset = time[current] - 1;
            globalOffset = Math.max(globalOffset, offset);
            long gcd = gcd(lcm, cycleLength);
            lcm /= gcd;
            lcm *= cycleLength;
        }
        long answer = lcm;
        while (answer < globalOffset) {
            answer += lcm;
        }
        out.println(answer);
    }

    private long gcd(long first, long second) {
        while (second != 0) {
            long tmp = first % second;
            first = second;
            second = tmp;
        }
        return first;
    }
}
