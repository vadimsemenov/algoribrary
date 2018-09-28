package task;

import algoribrary.io.InputReader;
import algoribrary.misc.ArrayUtils;

import java.io.PrintWriter;

public final class TaskC {
    public void solve(int __, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        State fst = new State(in.nextIntArray(length));
        State snd = new State(in.nextIntArray(length));
        while (fst.hasNext() || snd.hasNext()) {
            if (!fst.hasNext()) {
                snd.remove();
            } else if (!snd.hasNext()) {
                fst.advance();
            } else {
                if (fst.get() >= snd.get()) {
                    fst.advance();
                } else {
                    snd.remove();
                }
            }
            State tmp = fst;
            fst = snd;
            snd = tmp;
        }
        out.println(fst.sum - snd.sum);
    }

    static class State {
        final int[] array;
        int position;
        long sum;

        State(int[] array) {
            this.array = array;
            ArrayUtils.sort(array);
            position = array.length - 1;
            for (int elem : array) {
                sum += elem;
            }
        }

        boolean hasNext() {
            return position >= 0;
        }

        int get() {
            return array[position];
        }

        void remove() {
            sum -= array[position--];
        }

        void advance() {
            position--;
        }
    }
}
