package tasks;

import java.util.Arrays;
import java.util.Comparator;

public class CatsOnTheLineDiv1 {
    public int getNumber(final int[] position, int[] count, int time) {
        Integer[] idx = new Integer[position.length];
        for (int i = 0; i < idx.length; ++i) idx[i] = i;
        Arrays.sort(idx, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(position[o1], position[o2]);
            }
        });
        int answer = 0;
        long lastFree = Integer.MIN_VALUE;
        long canBeAt = Integer.MIN_VALUE;
        for (int i : idx) {
            long left = Math.max(lastFree, position[i] - time);
            if (left + count[i] - 1 > position[i] + time) {
                if (canBeAt < position[i] - time) {
                    answer++;
                    canBeAt = position[i] + time;
                }
                lastFree = position[i] - time + 1;
            } else {
                lastFree = left + count[i] + 1;
            }
        }
        return answer;
    }
}
