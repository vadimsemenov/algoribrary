package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskC {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int itemCount = in.nextInt();
        int cardCount = in.nextInt();
        Node[] items = new Node[itemCount];
        for (int i = 0; i < itemCount; ++i) {
            items[i] = new Node(in.nextInt(), in.nextInt());
        }
        Arrays.sort(items);
        long[] sums = new long[itemCount];
        sums[0] = items[0].cost;
        long[] sale = new long[itemCount];
        sale[0] = items[0].cost * items[0].percents;
        for (int i = 1; i < itemCount; ++i) {
            sums[i] = sums[i - 1] + items[i].cost;
            sale[i] = sale[i - 1] + items[i].cost * items[i].percents;
        }

        Node[] cards = new Node[cardCount];
        for (int i = 0; i < cardCount; ++i) {
            cards[i] = new Node(in.nextInt(), in.nextInt());
        }
        // Arrays.sort(cards);
        long bestSale = sale[itemCount - 1];
        for (int i = 0; i < cardCount; ++i) {
            int need = cards[i].cost;
            if (need > itemCount) continue;
            int perc = cards[i].percents;
            int better = Arrays.binarySearch(items, new Node(-1, perc));
            if (better < 0) better = -better - 1;
            if (itemCount - need > better) need = itemCount - better;
            long cur;
            if (itemCount - need - 1 < 0) {
                cur = sums[itemCount - 1] * perc;
            } else {
                cur = sale[itemCount - need - 1] + (sums[itemCount - 1] - sums[itemCount - need - 1]) * perc;
            }
            if (cur > bestSale) {
                bestSale = cur;
            }
        }
        out.println(sums[itemCount - 1] - bestSale / 100.);
    }

    static class Node implements Comparable<Node> {
        int cost, percents;

        Node(int cost, int percents) {
            this.cost = cost;
            this.percents = percents;
        }

        @Override
        public int compareTo(Node other) {
            int res = -Integer.compare(this.percents, other.percents);
            if (res == 0) return -Integer.compare(this.cost, other.cost);
            return res;
        }
    }
}
