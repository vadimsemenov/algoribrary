package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.*;

public final class TaskD {
    private static final int MODULO = 1_000_000_000 + 7;

    public void solve(int __, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        PriorityQueue<Integer> min = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> max = new PriorityQueue<>(Comparator.naturalOrder());
        min.add(0);
        max.add(Integer.MAX_VALUE);
        List<Integer> between = new ArrayList<>(counter);
        long answer = 1;
        for (int i = 0; i < counter; ++i) {
            String command = in.next();
            int price = in.nextInt();
            switch (command) {
                case "ADD":
                    if (min.peek() < price && price < max.peek()) {
                        between.add(price);
                    } else if (min.peek() > price) {
                        min.add(price);
                    } else if (max.peek() < price) {
                        max.add(price);
                    }
                    break;
                case "ACCEPT":
                    if (price < min.peek() || price > max.peek()) {
                        out.println(0);
                        return;
                    }
                    Collections.sort(between);
                    if (price == min.peek()) {
                        min.poll();
                        max.addAll(between);
                    } else if (price == max.peek()) {
                        max.poll();
                        min.addAll(between);
                    } else {
                        int idx = Collections.binarySearch(between, price);
                        if (idx < 0) throw new IllegalStateException("Order not found");
                        min.addAll(between.subList(0, idx));
                        max.addAll(between.subList(idx + 1, between.size()));
                        answer = 2 * answer % MODULO;
                    }
                    between.clear();
                    break;
                default:
                    throw new RuntimeException("Unknown command: " + command);
            }
        }
        if (between.size() > 0) {
            answer = answer * (between.size() + 1) % MODULO;
        }
        out.println(answer);
    }
}
