package tasks;



import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] first = in.nextIntTable(n, 2);
        int[][] second = in.nextIntTable(m, 2);
        Set<Integer> firstGuess = guess(first, second);
        Set<Integer> secondGuess = guess(second, first);
        if (firstGuess == null || secondGuess == null) {
            out.println(-1);
        } else if (firstGuess.size() == 1 && secondGuess.size() == 1) {
            int answer = firstGuess.iterator().next();
            assert answer == secondGuess.iterator().next();
            out.println(answer);
        } else {
            out.println(0);
        }
    }

    private Set<Integer> guess(int[][] my, int[][] his) {
        Set<Integer> result = new HashSet<>();
        for (int[] pair : my) {
            int guess = analyse(pair, his);
            if (guess > 0) {
                result.add(guess);
            } else if (guess == -1) {
                return null;
            }
        }
        return result;
    }

    private int analyse(int[] pair, int[][] otherPairs) {
        boolean first = false;
        boolean second = false;
        for (int[] candidate : otherPairs) {
            boolean fst = pair[0] == candidate[0] || pair[0] == candidate[1];
            boolean snd = pair[1] == candidate[0] || pair[1] == candidate[1];
            if (fst && snd) {
                continue;
            }
            first |= fst;
            second |= snd;
        }
        if (!first && !second) {
            return -2;
        }
        if (first && second) {
            return -1;
        }
        return first ? pair[0] : pair[1];
    }
}
