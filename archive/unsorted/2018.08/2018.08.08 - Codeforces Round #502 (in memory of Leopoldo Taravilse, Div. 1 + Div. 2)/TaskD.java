package task;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class TaskD {
    public void solve(int __, InputReader in, PrintWriter out) {
        int length = in.nextInt();
        int qty = in.nextInt();
        int queriesQty = in.nextInt();
        int[] weight = in.nextIntArray(length);
        int maskUpTo = 1 << length;
        int[] counter = new int[maskUpTo];
        for (int i = 0; i < qty; ++i) {
            String number = in.next();
            int mask = parseMask(length, number);
            counter[mask]++;
        }
        int[][] wuCost = new int[maskUpTo][maskUpTo];
        int[][] wuCostCount = new int[maskUpTo][];
        for (int i = 0; i < maskUpTo; ++i) {
            int maxCost = 0;
            for (int j = 0; j < maskUpTo; ++j) {
                wuCost[i][j] = wuCost(i, j, weight);
                maxCost = Math.max(maxCost, wuCost[i][j]);
            }
            wuCostCount[i] = new int[maxCost + 1];
            for (int j = 0; j < maskUpTo; ++j) {
                wuCostCount[i][wuCost[i][j]] += counter[j];
            }
            for (int cost = 1; cost <= maxCost; ++cost) {
                wuCostCount[i][cost] += wuCostCount[i][cost - 1];
            }
        }
        for (int i = 0; i < queriesQty; ++i) {
            int mask = parseMask(length, in.next());
            int upTo = Math.min(in.nextInt(), wuCostCount[mask].length - 1);
            out.println(wuCostCount[mask][upTo]);
        }
    }

    private int parseMask(int length, String number) {
        assert number.length() == length;
        int mask = 0;
        for (int j = 0; j < length; ++j) {
            if (number.charAt(j) == '1') {
                mask |= 1 << j;
            }
        }
        return mask;
    }

    private int wuCost(int fst, int snd, int[] weight) {
        int cost = 0;
        for (int i = 0; i < weight.length; ++i) {
            if (((fst >>> i) & 1) == ((snd >>> i) & 1)) {
                cost += weight[i];
            }
        }
        return cost;
    }
}
