package tasks;

public class ModModMod {
    public long findSum(int[] mod, int r) {
        int ptr = 0;
        for (int i = 1; i < mod.length; ++i) {
            if (mod[i] < mod[ptr]) {
                mod[++ptr] = mod[i];
            }
        }
        int[] answer = new int[r + 1];
        for (int i = 0; i < answer.length; ++i) {
            answer[i] = i;
        }
        long sum = 0;
        for (int i = 1; i <= r; ++i) {
            if (ptr > 0 && i >= mod[ptr - 1]) --ptr;
            answer[i] = answer[i % mod[ptr]];
            sum += answer[i];
        }
        return sum;
    }
}
