package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;

public final class TaskG {
    private static final int MAX_QUERIES = 5;
    private static final int MAX_QUERY_LENGTH = 10000;
    private static final long MAX_VALUE = 10004205361450474L;

    private long[][] mem = new long[MAX_QUERIES + 1][MAX_QUERY_LENGTH + 1];

    public void solve(int __, InputReader in, PrintWriter out) {
        long left = 1;
        long right = MAX_VALUE + 1;
        for (int queriesLeft = MAX_QUERIES; queriesLeft >= 1; queriesLeft--) {
            long[] query = new long[(int) Math.min(left, MAX_QUERY_LENGTH)];
            long from = left;
            int size = -1;
            while (++size < query.length && from < right) {
                query[size] = Math.min(right - 1, from + canGuess(queriesLeft - 1, from));
                from = query[size] + 1;
            }
            int result = makeQuery(query, size, in, out);
            if (result < 0) return;
            if (result == 0) {
                right = query[result];
            } else if (result == size) {
                left = query[result - 1] + 1;
            } else {
                left = query[result - 1] + 1;
                right = query[result];
            }
        }
        throw new AssertionError();
    }

    private int makeQuery(long[] query, int size, InputReader in, PrintWriter out) {
        out.print(size);
        for (int i = 0; i < size; ++i) {
            out.print(' ');
            out.print(query[i]);
        }
        out.println();
        out.flush();
        return in.nextInt();
    }

    private long canGuess(int queriesLeft, long left) {
        return canGuess(queriesLeft, (int) Math.min(left, MAX_QUERY_LENGTH));
    }

    private long canGuess(int queriesLeft, int left) {
        if (queriesLeft < 2) {
            return queriesLeft * left;
        }
        if (mem[queriesLeft][left] == 0) {
            long length = canGuess(queriesLeft - 1, left) + 1;
            for (int i = 1; i < left; ++i) {
                length += canGuess(queriesLeft - 1, left + length) + 1;
                if (length > MAX_VALUE) break;
            }
            length += canGuess(queriesLeft - 1, left + length);
            mem[queriesLeft][left] = Math.min(length, MAX_VALUE);
        }
        return mem[queriesLeft][left];
    }
}
