package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;

public class TaskE {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int trees = in.nextInt();
        int queries = in.nextInt();
        int length = in.nextInt();
        int counter = trees - 1;
        int[] lens = new int[counter];
        int[] newTrees = new int[counter];
        int[] bufVals = new int[counter];
        PriorityQueue<Integer> queue = new PriorityQueue<>(counter, (first, second) ->
                -Integer.compare(bufVals[first], bufVals[second]));
        int last = in.nextInt();
        for (int i = 0; i < counter; ++i) {
            int current = in.nextInt();
            bufVals[i] = lens[i] = current - last;
            last = current;
            queue.add(i);
        }
        final int BUBEN = Math.min(1_000_000, length - trees + 2);
        int[] smallK = new int[BUBEN];
        for (int i = 0; i < BUBEN; ++i) {
            int max = queue.poll();
            smallK[i] = bufVals[max];
            ++newTrees[max];
            bufVals[max] = (lens[max] + newTrees[max]) / (newTrees[max] + 1);
            queue.add(max);
        }
        final int THRESHOLD = smallK[BUBEN - 1] + 1;
        int[] need = new int[THRESHOLD];
        for (int i = 0; i < counter; ++i) if (lens[i] >= THRESHOLD) {
            for (int len = 1; len < THRESHOLD; ++len) {
                need[len] += (lens[i] + len - 1) / len - 1;
            }
        }
        int[] sums = new int[THRESHOLD];
        for (int i = 0; i < counter; ++i) if (lens[i] < THRESHOLD) {
            ++sums[lens[i]];
        }
        for (int i = 1; i < THRESHOLD; ++i) {
            sums[i] += sums[i - 1];
        }
        for (int len = 1; len < THRESHOLD; ++len) {
            for (int it = 1; len * it < THRESHOLD; ++it) {
                int left = len * it;
                int right = Math.min(len * it + len, THRESHOLD - 1);
                need[len] += it * (sums[right] - sums[left]);
            }
        }
        for (int query = 0; query < queries; ++query) {
            int k = in.nextInt();
            if (k < BUBEN) {
                out.println(smallK[k]);
            } else {
                int left = 0;
                int right = THRESHOLD;
                while (right - left > 1) {
                    int middle = (left + right) >>> 1;
                    if (need[middle] <= k) {
                        right = middle;
                    } else {
                        left = middle;
                    }
                }
                out.println(right);
            }
        }
    }
}
