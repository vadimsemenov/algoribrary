package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskD {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int[] primes = sieve(100_000);
        int[] lastDiv = new int[primes.length];
        Arrays.fill(lastDiv, -1);
        int[] length = new int[counter];
        Arrays.fill(length, 1);
        int answer = 1;
        for (int i = 0; i < counter; ++i) {
            int ai = in.nextInt();
            int ptr = 0;
            while (ai != 1) {
                if (ai % primes[ptr] == 0) {
                    if (lastDiv[ptr] != -1) {
                        length[i] = Math.max(length[i], length[lastDiv[ptr]] + 1);
                        answer = Math.max(answer, length[i]);
                    }
                    lastDiv[ptr] = i;
                    while (ai % primes[ptr] == 0) {
                        ai /= primes[ptr];
                    }
                }
                ptr++;
            }
        }
        out.println(answer);
    }

    private int[] sieve(int maximal) {
        boolean[] notPrime = new boolean[maximal + 1];
        notPrime[0] = notPrime[1] = true;
        int cnt = 0;
        for (int p = 2; p <= maximal; ++p) if (!notPrime[p]) {
            cnt++;
            for (long np = p * 1L * p; np <= maximal; np += p) {
                notPrime[(int) np] = true;
            }
        }
        int[] primes = new int[cnt];
        cnt = 0;
        for (int p = 2; p <= maximal; ++p) if (!notPrime[p]) {
            primes[cnt++] = p;
        }
        return primes;
    }
}
