package tasks;

import algoribrary.io.InputReader;
import sun.jvm.hotspot.utilities.Assert;

import java.io.PrintWriter;
import java.util.Arrays;

public final class MinimumMSTGraph {
    private static final long[] knapsack = new long[10_000_000];

    public void solve(int __, InputReader in, PrintWriter out) {
        int failed = 0;
        for (int v = /*2*/6; v <= 10; ++v) {
            for (int e = (v - 1) * (v - 2) / 2 + 2; e <= v * (v - 1) / 2; ++e) {
                for (int s = v - 1; s <= 20; ++s) {
                    int expected = bf(v, e, s);
                    int actual = (int) solve(v, e, s);
                    if (expected != actual) {
                        ++failed;
                        System.out.println("POLUNDRA!!!");
                        System.out.println(v + " " + e + " " + s);
                        System.out.println("expected: " + expected);
                        System.out.println("actual: " + actual);
                    }
                }
            }
        }
        System.out.println("FAILED: " + failed);
        System.exit(0);
        int vertices = in.nextInt();
        long edges = in.nextLong();
        long mstWeight = in.nextLong();
        if (edges <= (vertices - 2L) * (vertices - 1) / 2 + 1) {
            out.println(mstWeight + edges - (vertices - 1));
        } else {
            if (true) {
                out.println(bf(vertices, (int) edges, (int) mstWeight));
            } else {
                out.println(solve(vertices, edges, mstWeight));
            }
        }
    }

    private long solve(int vertices, long edges, long mstWeight) {
        long base = mstWeight / (vertices - 1);
        if (true) {
            int remainder = (int) mstWeight;
            Arrays.fill(knapsack, 0, remainder + 1, Long.MAX_VALUE / 3);
            knapsack[vertices - 1] = edges;
            int from = vertices - 1;
            for (; from <= remainder - (vertices - 1L) * (vertices - 1); from += vertices - 1) {
                knapsack[from + vertices - 1] = Math.min(knapsack[from + vertices - 1], knapsack[from] + edges);
            }
            long cost = edges - (vertices - 1) * (vertices - 2) / 2;
            for (int weight = 1; weight <= Math.min(vertices - 1, remainder); ++weight) {
                for (int w = from; w + weight <= remainder; ++w) {
                    knapsack[w + weight] = Math.min(knapsack[w + weight], knapsack[w] + cost);
                }
                cost += vertices - weight - 1;
            }
            return /*base * edges + */knapsack[remainder];
        } else {
            int remainder = (int) (mstWeight % (vertices - 1));
            long ad = remainder == 0 ? 0 : Integer.MAX_VALUE;
            for (int d = 1; d * d <= remainder; ++d) {
                if (remainder % d == 0) {
                    int dd = remainder / d;
                    ad = Math.min(ad, d * (edges - (vertices - dd) * (vertices - dd - 1L) / 2));
                    ad = Math.min(ad, dd * (edges - (vertices - d) * (vertices - d - 1L) / 2));
                }
            }
//            long remainder = vertices - mstWeight % (vertices - 1);
//            long ad = remainder == vertices ? 0 : edges - (remainder) * (remainder - 1) / 2;
            return edges * base + ad;
        }
    }

    private int bf(int vertices, int edges, int mstWeight) {
        int[] cnt = new int[vertices - 1];
        for (int i = 1; i < cnt.length; ++i) {
            cnt[i] = vertices - i - 1;
        }
        cnt[0] = edges - (vertices - 2) * (vertices - 1) / 2;
        return dfs(vertices - 2, mstWeight, 1, 0, cnt);
    }

    private int dfs(int edgeId, long mstWeight, int min, int total, int[] cnt) {
        if (edgeId < 0) return mstWeight == 0 ? total : Integer.MAX_VALUE;
        int best = Integer.MAX_VALUE;
        for (int cost = min; cost * (edgeId + 1) <= mstWeight; ++cost) {
            best = Math.min(best, dfs(edgeId - 1, mstWeight - cost, cost, total + cnt[edgeId] * cost, cnt));
        }
        return best;
    }
}
