package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.*;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        int height = in.nextInt();
        List<int[]> caramels = new ArrayList<>();
        List<int[]> fruits = new ArrayList<>();
        for (int i = 0; i < counter; i++) {
            int type = in.nextInt();
            int[] pair = new int[]{in.nextInt(), in.nextInt()};
            if (type == 0) caramels.add(pair);
            else if (type == 1) fruits.add(pair);
            else throw new AssertionError(type);
        }
        Comparator<int[]> comparator = new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        };
        Collections.sort(fruits, comparator);
        Collections.sort(caramels, comparator);
        out.println(Math.max(go(caramels, fruits, height), go(fruits, caramels, height)));
    }

    private int go(List<int[]> fst, List<int[]> snd, int height) {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -Integer.compare(o1, o2);
            }
        };
        PriorityQueue<Integer> fheap = new PriorityQueue<>(1 + fst.size(), comparator);
        PriorityQueue<Integer> sheap = new PriorityQueue<>(1 + snd.size(), comparator);
        int fptr = 0;
        int sptr = 0;
        int eaten = 0;
        while (true) {
            while (fptr < fst.size() && fst.get(fptr)[0] <= height)
                fheap.add(fst.get(fptr++)[1]);
            if (fheap.isEmpty()) return eaten;
            height += fheap.poll();
            eaten++;
            while (sptr < snd.size() && snd.get(sptr)[0] <= height)
                sheap.add(snd.get(sptr++)[1]);
            if (sheap.isEmpty()) return eaten;
            height += sheap.poll();
            eaten++;
        }
    }
}
