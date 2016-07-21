package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NavigableSet;
import java.util.TreeSet;

public class TaskM {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        Sequence[] sequences = new Sequence[counter];
        for (int i = 0; i < counter; ++i) {
            int[] a = new int[8];
            for (int j = 7; j >= 0; --j) {
                a[j] = in.nextInt();
            }
            sequences[i] = new Sequence(a);
        }
        int no = in.nextInt() - 1;
        NavigableSet<Sequence> heap = new TreeSet<>(Arrays.asList(sequences));
        while (no --> 0) {
            Sequence current = heap.pollFirst();
//            System.err.println(current.value);
            current.n++;
            current.calculateValue();
            heap.add(current);
        }
        out.println(heap.pollFirst().value);
    }

    static class Sequence implements Comparable<Sequence> {
        static int CNT = 0;
        int id;
        int[] a;
        int n;
        long value;

        Sequence(int[] a) {
            id = CNT++;
            this.a = a;
            n = 1;
            calculateValue();
        }

        public void calculateValue() {
            value = 0;
            for (int i = 0; i < a.length; ++i) {
                long ad = a[i];
                long mul = 1;
                for (int j = 0; j < i; ++j) {
                    if (mul > Long.MAX_VALUE / n) {
                        mul = Long.MAX_VALUE;
                    } else {
                        mul *= n;
                    }
                }
                if (ad > Long.MAX_VALUE / mul) {
                    ad = Long.MAX_VALUE;
                } else {
                    ad *= mul;
                }
                if (value > Long.MAX_VALUE - ad) {
                    value = Long.MAX_VALUE;
                } else {
                    value += ad;
                }
            }
        }

        @Override
        public int compareTo(Sequence o) {
            int cmp = Long.compare(value, o.value);
            if (cmp == 0) {
                cmp = Integer.compare(id, o.id);
            }
            return cmp;
        }
    }
}
