package tasks;

import java.util.Arrays;
import java.util.Comparator;

public class NarrowPassage {
    public int minDist(int L, final int[] a, final int[] b) {
        int counter = a.length;
        Integer[] indices = new Integer[counter];
        for (int i = 0; i < indices.length; i++) indices[i] = i;
        Arrays.sort(indices, new Comparator<Integer>() {
            @Override
            public int compare(Integer first, Integer second) {
                return Integer.compare(a[first], a[second]);
            }
        });
        int[] array = new int[counter];
        for (int i = 0; i < counter; i++) array[i] = b[indices[i]];
        int[] block = new int[counter];
        Arrays.fill(block, -1);
        block[0] = 0;
        System.err.println(Arrays.toString(a.clone()));
        for (int i = 1; i < counter; i++) {
            int mostLeft = i;
            for (int j = i - 1; j >= 0; j--) {
                if (array[j] > array[i]) mostLeft = j;
            }
            System.err.println(mostLeft + " " + i);
            if (mostLeft == i) block[i] = i;
            else for (int j = mostLeft + 1; j <= i; j++) block[j] = block[mostLeft];
        }

        int answer = 0;

        int start = 0;
        while (start + 1 < counter && block[start] != block[start + 1]) {
            answer += Math.abs(a[indices[start]] - b[indices[start]]);
            start++;
        }
        if (start == counter - 1) answer += Math.abs(a[indices[start]] - b[indices[start]]);
        else for (int i = start; i < counter; i++)
            answer += (L - a[indices[i]]) + (L - b[indices[i]]);
        System.err.println(Arrays.toString(block));
        System.err.println(answer);
        int sum = 0;
        for (int i = 0; i < counter; i++) sum += (L - a[i]) + (L - b[i]);
        int asum = 0;
        for (int i = 0; i < counter; i++) asum += a[i] + b[i];
        System.err.println(sum + " " + asum);
        for (int mid = 0; mid < counter; mid++) {
            int next = mid;
            while (next < counter && block[next] == block[mid]) next++;
            int result = 0;
            for (int i = 0; i < next; i++) result += a[indices[i]] + b[indices[i]];
            int nextnext = Math.min(counter, next + 1);
            while (nextnext + 1 < counter && block[nextnext] != block[nextnext + 1]) nextnext++;
            for (int i = next; i < nextnext; i++) result += Math.abs(a[indices[i]] - b[indices[i]]);
            for (int i = nextnext; i < counter; i++) result += (L - a[indices[i]]) + (L - b[indices[i]]);
            answer = Math.min(answer, result);
//            mid = next - 1;
        }

        return answer;
    }
}
