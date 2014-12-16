package tasks;

public class TwoNumberGroups {
    public int solve(int[] A, int[] numA, int[] B, int[] numB) {
        final int ROOT = 31_623;
        final int MODULO = 1_000_000_007;
        int[] primes = sieve(ROOT);
        int[][] table = new int[A.length][B.length];
        for (int i = 0; i < A.length; ++i) {
            for (int j = 0; j < B.length; ++j) {
                table[i][j] = Math.abs(A[i] - B[j]);
            }
        }
        CleaningArray[] lists = new CleaningArray[ROOT + 1];
        for (int i = 0; i < lists.length; ++i) {
            lists[i] = new CleaningArray();
        }
        long answer = 0;
        for (int p : primes) {
            for (CleaningArray list : lists) {
                list.clear();
            }
            for (int i = 0; i < A.length; ++i) {
                lists[A[i] % p].add(i);
            }
            for (int j = 0; j < B.length; ++j) {
                int mod = B[j] % p;
                for (int k = 0; k < lists[mod].size(); ++k) {
                    int i = lists[mod].get(k);
                    if (A[i] != B[j]) {
                        answer = (answer + numA[i] * 1L * numB[j] * p) % MODULO;
                        while (table[i][j] % p == 0) {
                            table[i][j] /= p;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < A.length; ++i) {
            for (int j = 0; j < B.length; ++j) {
                if (table[i][j] > 1) {
                    answer = (answer + numA[i] * 1L * numB[j] % MODULO * table[i][j]) % MODULO;
                }
            }
        }
        return (int) answer;
    }

    static class CleaningArray {
        int[] data;
        int size;

        CleaningArray() {
            this(8);
        }

        CleaningArray(int size) {
            data = new int[size];
        }

        int get(int i) {
            return data[i];
        }

        void add(int val) {
            ensureCapacity(size + 1);
            data[size] = val;
            size++;
        }

        int size() {
            return size;
        }

        void clear() {
            size = 0;
        }

        void ensureCapacity(int capacity) {
            if (data.length < capacity) {
                int newCapacity = Math.max(data.length * 2, capacity);
                int[] buf = data;
                data = new int[newCapacity];
                System.arraycopy(buf, 0, data, 0, buf.length);
            }
        }
    }

    private int[] sieve(int upTo) {
        boolean[] notPrime = new boolean[upTo + 1];
        notPrime[0] = notPrime[1] = true;
        int cnt = 0;
        for (int p = 2; p <= upTo; ++p) {
            if (!notPrime[p]) {
                cnt++;
                if (p * 1L * p <= upTo) {
                    for (int d = p * p; d <= upTo; d += p) {
                        notPrime[d] = true;
                    }
                }
            }
        }
        int[] primes = new int[cnt];
        cnt = 0;
        for (int p = 0; p <= upTo; ++p) {
            if (!notPrime[p]) {
                primes[cnt++] = p;
            }
        }
        return primes;
    }
}
