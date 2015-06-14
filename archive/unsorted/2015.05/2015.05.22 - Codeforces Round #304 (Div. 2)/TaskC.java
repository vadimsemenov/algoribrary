package tasks;

import algoribrary.io.InputReader;
import net.egork.chelper.tester.StringInputStream;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class TaskC {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int total = in.nextInt();
        int firstCnt = in.nextInt();
        long firstState = 0;
        for (int i = 0; i < firstCnt; ++i) {
            firstState = push(in.nextInt(), firstState, i);
        }
        int secondCnt = in.nextInt();
        long secondState = 0;
        for (int i = 0; i < secondCnt; ++i) {
            secondState = push(in.nextInt(), secondState, i);
        }
        Set<Pair> set = new HashSet<>();
        set.add(new Pair(firstState, secondState));
        for (int iteration = 0; ; ++iteration) {
            if (firstCnt == 0) {
                out.println(iteration + " 2");
                return;
            }
            if (secondCnt == 0) {
                out.println(iteration + " 1");
                return;
            }
            int fst = pull(firstState); firstState = pop(firstState); --firstCnt;
            int snd = pull(secondState); secondState = pop(secondState); --secondCnt;
            if (fst > snd) {
                firstState = push(snd, firstState, firstCnt); ++firstCnt;
                firstState = push(fst, firstState, firstCnt); ++firstCnt;
            } else if (snd > fst) {
                secondState = push(fst, secondState, secondCnt); ++secondCnt;
                secondState = push(snd, secondState, secondCnt); ++secondCnt;
            } else throw new AssertionError("WTF?!");
            Pair state = new Pair(firstState, secondState);
            if (set.contains(state)) {
                out.println(-1);
                return;
            }
            set.add(state);
        }
    }

    private static class Pair {
        final long first, second;

        public Pair(long first, long second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            Pair pair = (Pair) o;
            if (first != pair.first) return false;
            return second == pair.second;
        }

        @Override
        public int hashCode() {
            int result = (int) (first ^ (first >>> 32));
            result = 31 * result + (int) (second ^ (second >>> 32));
            return result;
        }
    }

    private static long[] pow11 = new long[12];
    static {
        pow11[0] = 1;
        for (int i = 1; i < pow11.length; ++i) {
            pow11[i] = 11 * pow11[i - 1];
        }
    }

    private static long push(int card, long state, int cnt) {
        return card * pow11[cnt] + state;
    }

    private static int pull(long state) {
        return (int) (state % 11);
    }

    private static long pop(long state) {
        return state / 11;
    }
}
