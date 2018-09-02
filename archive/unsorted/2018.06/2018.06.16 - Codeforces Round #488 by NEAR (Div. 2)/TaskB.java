package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public final class TaskB {
    public void solve(int __, InputReader in, PrintWriter out) {
        int count = in.nextInt();
        int limit = in.nextInt();
        int[] rewards = new int[limit + 1];
        int[] powers = in.nextIntArray(count);
        int[] coins = in.nextIntArray(count);
        Integer[] order = new Integer[count];
        for (int i = 0; i < count; ++i) order[i] = i;
        Arrays.sort(order, Comparator.comparingInt(i -> powers[i]));
        int[] position = new int[count];
        for (int i = 0; i < count; ++i) {
            position[order[i]] = i;
        }
        long[] prefix = new long[count];
        rewards[limit] = coins[order[0]];
        long sum = limit > 0 ? rewards[limit] : 0;
        for (int i = 1; i < count; ++i) {
            prefix[i] = sum;
            int hisMoney = coins[order[i]];
            int ptr = 0;
            while (ptr + 1 < rewards.length && rewards[ptr + 1] < hisMoney) {
                rewards[ptr] = rewards[ptr + 1];
                ptr++;
            }
            rewards[ptr] = hisMoney;
            sum += hisMoney - rewards[0];
        }
        for (int i = 0; i < count; ++i) {
            if (i > 0) out.print(' ');
            out.print(prefix[position[i]] + coins[i]);
        }
    }

    int[] rewards;
    private long killThem(int id, int[] powers, int[] coins) {
        Arrays.fill(rewards, 0);
        int myPower = powers[id];
        for (int i = 0; i < powers.length; i++) {
            int hisPower = powers[i];
            if (hisPower >= myPower) continue;
            int hisMoney = coins[i];
            int ptr = 0;
            while (ptr + 1 < rewards.length && hisMoney > (rewards[ptr + 1])) {
                rewards[ptr] = rewards[ptr + 1];
                ptr++;
            }
            rewards[ptr] = hisMoney;
        }
        long answer = -rewards[0];
        for (int i : rewards) {
            answer += i;
        }
        return answer;
    }
}
