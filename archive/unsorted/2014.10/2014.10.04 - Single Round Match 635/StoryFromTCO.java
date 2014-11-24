package tasks;

import java.util.*;

public class StoryFromTCO {
    public int minimumChanges(final int[] places, final int[] cutoff) {
        final Integer[] idx1 = new Integer[places.length];
        final Integer[] idx2 = new Integer[places.length];
        for (int i = 0; i < idx1.length; ++i) {
            idx1[i] = i;
            idx2[i] = i;
        }
        Arrays.sort(idx1,new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -Integer.compare(places[o1], places[o2]);
            }
        });
        Arrays.sort(idx2,new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -Integer.compare(cutoff[o1], cutoff[o2]);
            }
        });
        boolean[] wake = new boolean[idx1.length];
        List<Integer> idx3 = new ArrayList<>();
        for (int i = 0; i < wake.length; ++i) {
            if (idx1[i] <= idx2[i]) wake[i] = true;
            else idx3.add(i);
        }
        int[] arr1 = new int[idx3.size()];
        int[] arr2 = new int[idx3.size()];
        int ptr1 = 0;
        int ptr2 = 0;
        for (int i = 0; i < idx1.length; ++i) {
            if (!wake[idx1[i]]) arr1[ptr1++] = i;
            if (!wake[idx2[i]]) arr2[ptr2++] = i;
        }
        idx3 = new ArrayList<>();
        for (int i = 0; i < idx3.size(); ++i) idx3.set(i, i);
        Collections.sort(idx3, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Math.abs(idx1[o1] - idx2[o1]) - Math.abs(idx1[o2] - idx2[o2]);
            }
        });
        int[] canDel = new int[idx3.size()];
        for (int i = 0; i < canDel.length; ++i) {
            // while (canDel[i] + i < )
        }
        return 0;
    }
}
