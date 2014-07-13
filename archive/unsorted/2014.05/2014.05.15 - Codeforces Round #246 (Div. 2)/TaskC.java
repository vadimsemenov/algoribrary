package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int size = in.nextInt();
        int[] array = new int[size + 1];
        int[] position = new int[size + 1];
        for (int i = 1; i <= size; i++) position[array[i] = in.nextInt()] = i;
        generate(size);
        List<int[]> list = new ArrayList<>();
        for (int i = size; i > 0; i--) {
            int idx = position[i];
            if (idx == i) continue;
            int len = i - idx + 1;
            if (isPrime[len]) {
                list.add(new int[]{idx, i});
                array[i] ^= array[idx] ^= array[i] ^= array[idx];
                position[array[i]] = i;
                position[array[idx]] = idx;
            } else if (isPrime[len - 1]) {
                list.add(new int[]{idx, i - 1});
                array[i - 1] ^= array[idx] ^= array[i - 1] ^= array[idx];
                position[array[i - 1]] = i - 1;
                position[array[idx]] = idx;

                list.add(new int[]{i - 1, i});
                array[i] ^= array[i - 1] ^= array[i] ^= array[i - 1];
                position[array[i]] = i;
                position[array[i - 1]] = i - 1;
            } else {
                int j = i;
                if ((j - idx) % 2 == 1) j--;
                int k = idx + first[j - idx + 2] - 1;

                list.add(new int[]{idx, k});
                array[k] ^= array[idx] ^= array[k] ^= array[idx];
                position[array[k]] = k;
                position[array[idx]] = idx;

                list.add(new int[]{k, j});
                array[j] ^= array[k] ^= array[j] ^= array[k];
                position[array[j]] = j;
                position[array[k]] = k;

                if (j != i) {
                    list.add(new int[]{j, i});
                    array[i] ^= array[j] ^= array[i] ^= array[j];
                    position[array[i]] = i;
                    position[array[j]] = j;
                }
            }
        }
        out.println(list.size());
        for (int[] i : list) out.println(i[0] + " " + i[1]);
    }

    private void generate(int size) {
        isPrime = new boolean[size + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        int cnt = 1;
        for (int i = 4; i < isPrime.length; i += 2) isPrime[i] = false;
        for (int i = 3; i < isPrime.length; i++) {
            if (isPrime[i]) cnt++;
            if (isPrime[i] && i <= (isPrime.length + i - 1) / i) {
                for (int j = i * i; j < isPrime.length; j += i)
                    isPrime[j] = false;
            }
        }
        primes = new int[cnt];
        cnt = 0;
        for (int i = 0; i < isPrime.length; i++) if (isPrime[i]) primes[cnt++] = i;
        first = new int[size + 1];
        Arrays.fill(first, Integer.MIN_VALUE);
        for (int i = 4; i <= size; i += 2) {
            for (int a = 0; a < primes.length; a++) {
                if (isPrime[i - primes[a]]) {
                    first[i] = primes[a];
                    break;
                }
            }
        }
    }

    private boolean[] isPrime;
    private int[] primes;
    private int[] first;
}
