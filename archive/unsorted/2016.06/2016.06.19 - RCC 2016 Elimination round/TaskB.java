package tasks;

import algoribrary.collections.Heap;
import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;

public class TaskB {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int tests = in.nextInt();
        while (tests --> 0) {
            int legs = in.nextInt();
            int counter = in.nextInt();
            int[][] records = new int[counter][2];
            int ptr = 0;
            for (int i = 0; i < counter; ++i) {
                int left = in.nextInt();
                int right = in.nextInt();
                if (left + right > legs) continue;
                records[ptr][0] = left;
                records[ptr][1] = right;
                ++ptr;
            }
            records = Arrays.copyOf(records, ptr);
            Arrays.sort(records, (fst, snd) -> {
                int cmp = Integer.compare(fst[0], snd[0]);
                if (cmp == 0) cmp = -Integer.compare(fst[1], snd[1]);
                return cmp;
            });
            PriorityQueue<Integer> heap = new PriorityQueue<>(counter, (fst, snd) -> -Integer.compare(fst, snd));
            int answer = 0;
            int bestLeft = 1;
            int bestRight = legs - bestLeft;
            for (int[] pair : records) {
                int left = pair[0];
                int right = legs - left;
                heap.add(pair[1]);
                while (heap.peek() > right) {
                    heap.poll();
                }
                if (answer < heap.size()) {
                    answer = heap.size();
                    bestLeft = left;
                    bestRight = right;
                }
            }
            out.println(bestLeft + " " + bestRight);
        }
    }
}
