package tasks;

public class BearPlays {
    public int pileSize(int A, int B, int K) {
        long answer = A * pow(2, K, A + B);
        answer %= A + B;
        answer = Math.min(answer, A + B - answer);
        return (int) answer;
    }

    private long pow(long base, int pow, int mod) {
        long result = 1;
        while (pow > 0) {
            if ((pow & 1) == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            pow >>>= 1;
        }
        return result;
    }
}
