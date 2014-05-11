package tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CandidatesSelection {
    public String possible(String[] score, int[] result) {
        int params = score[0].length();
        List<Integer> borders = Arrays.asList(0, score.length);
        outer:
        while (true) {
            boolean sorted = true;
            for (int cur = 0; cur < borders.size(); cur += 2) {
                for (int i = borders.get(cur); i + 1 < borders.get(cur + 1); i++) {
                    if (result[i] > result[i + 1]) sorted = false;
                }
            }
            if (sorted) break outer;
            inner:
            for (int param = 0; param < params; param++) {
                boolean good = false;
                for (int cur = 0; cur < borders.size(); cur += 2) {
                    for (int i = borders.get(cur); i + 1 < borders.get(cur + 1); i++) {
                        if (score[result[i]].charAt(param) > score[result[i + 1]].charAt(param))
                            continue inner;
                        good |= score[result[i]].charAt(param) < score[result[i + 1]].charAt(param);
                    }
                }
                if (good) {
                    List<Integer> newBorders = new ArrayList<>();
                    for (int cur = 0; cur < borders.size(); cur += 2) {
                        for (int i = borders.get(cur); i + 1 < borders.get(cur + 1); i++) {
                            newBorders.add(i);
                            int end = i;
                            while (end < borders.get(cur + 1) &&
                                    score[result[i]].charAt(param) == score[result[end]].charAt(param))
                                end++;
                            newBorders.add(end);
                            i = end - 1;
                        }
                    }
                    borders = newBorders;
                    continue outer;
                }
            }
            return "Impossible";
        }
        return "Possible";
    }
}
