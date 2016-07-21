package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cylinders {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        State.scales = new int[in.nextInt()];
        for (int i = 0; i < State.scales.length; ++i) State.scales[i] = in.nextInt();
        State.need = in.nextInt();
        if (State.need == 0) {
            out.println(0);
            return;
        }
        State initial = new State(0, 0);
        Map<State, Integer> layer = new HashMap<>();
        layer.put(initial, 0);
        List<State> queue = new ArrayList<>();
        queue.add(initial);
        int head = 0;
        while (head < queue.size()) {
            State current = queue.get(head++);
            int currentLayer = layer.get(current);
            for (State next : current.next()) {
                if (!layer.containsKey(next)) {
                    if (next.inFirst == State.need || next.inSecond == State.need) {
                        out.println(currentLayer + 1);
                        return;
                    }
                    layer.put(next, currentLayer + 1);
                    queue.add(next);
                }
            }
        }
        out.println("IMPOSSIBLE");
    }

    private static class State {
        static int[] scales;
        static int need;

        final int inFirst, inSecond;

        private State(int inFirst, int inSecond) {
            this.inFirst = Math.min(inFirst, inSecond);
            this.inSecond = Math.max(inFirst, inSecond);
        }

        List<State> next() {
            List<State> next = new ArrayList<>();
            for (int x : scales) {
                int ff = inFirst;
                int ss = inSecond;
                for (int it = 0; it < 2; ++it) {
                    if (x > ff) {
                        next.add(new State(x, ss));
                        if (ss >= (x - ff)) {
                            next.add(new State(x, ss - (x - ff)));
                        }
                    } else { // x < ff
                        next.add(new State(x, ss));
                        if (ss + ff - x <= scales[scales.length - 1]) {
                            next.add(new State(x, ss + ff - x));
                        }
                    }
                    int tt = ff; ff = ss; ss = tt;
                }
            }
            return next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            State state = (State) o;
            return inFirst == state.inFirst && inSecond == state.inSecond;
        }

        @Override
        public int hashCode() {
            int result = inFirst;
            result = 100_003 * result + inSecond;
            return result;
        }
    }
}
