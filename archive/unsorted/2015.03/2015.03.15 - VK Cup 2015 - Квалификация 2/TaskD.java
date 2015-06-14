package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.PriorityQueue;

public class TaskD {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int videos = in.nextInt();
        int servers = in.nextInt();
        long[] uploaded = new long[videos + 1];
        int[] duration = new int[videos + 1];
        for (int i = 0; i < videos; ++i) {
            uploaded[i] = in.nextInt();
            duration[i] = in.nextInt();
        }
        uploaded[videos] = Long.MAX_VALUE;
        int[] queue = new int[videos + 1];
        int head = 0;
        int tail = 0;
        PriorityQueue<Long> heap = new PriorityQueue<>(servers);
        for (int i = 0; i < servers; ++i) {
            heap.add(0L);
        }
        for (int i = 0; i <= videos; ++i) {
            queue[tail++] = i;
            while (head < tail && heap.peek() <= uploaded[i]) {
                int id = queue[head++];
                long begin = Math.max(uploaded[id], heap.poll());
                long end = begin + duration[id];
                heap.add(end);
                if (id < videos) out.println(end);
            }
        }
    }
}
