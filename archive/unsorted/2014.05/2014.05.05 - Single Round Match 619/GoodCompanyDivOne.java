package tasks;

import java.util.Arrays;

public class GoodCompanyDivOne {
    public int minimumCost(int[] superior, int[] training) {
        Arrays.sort(training);
        int[] sums = new int[training.length];
        for (int i = 1; i < training.length; i++) sums[i] = sums[i - 1] + training[i];
//        System.err.println("\n" + Arrays.toString(training) + "\n" + Arrays.toString(sums));
        int[] cnt = new int[superior.length];
        for (int i = 1; i < superior.length; i++) cnt[superior[i]]++;
        System.err.println(Arrays.toString(cnt));
        int answer = 0;
        for (int i = 0; i < superior.length; i++) {
            if (cnt[i] < sums.length) answer += sums[cnt[i]];
            else return -1;
        }
        return training[0] * superior.length + answer + training[1];
    }
}
