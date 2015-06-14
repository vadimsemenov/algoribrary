package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

public class DEVSHOW {
    public void solve(final int testNumber, final InputReader in, final PrintWriter out) {
        int counter = in.nextInt();
        int[] firstVote = new int[counter - 2];
        int[] secondVote = new int[counter - 2];
        for (int i = 0; i < counter - 2; ++i) firstVote[i] = in.nextInt() - 1;
        for (int i = 0; i < counter - 2; ++i) secondVote[i] = in.nextInt() - 1;
        int[] cnt = new int[counter];
        for (int i = 0; i < counter - 2; ++i) {
            cnt[firstVote[i]]++;
            cnt[secondVote[i]]++;
        }
        int[] indices, count;
        int idv, idu;
        {
            int minIdx = -1;
            int secondMinIdx = -1;
            int thirdMinIdx = -1;
            int maxIdx = -1;
            int secondMaxIdx = -1;
            for (int i = 0; i < counter - 2; ++i) {
                if (minIdx == -1 || cnt[minIdx] >= cnt[i]) {
                    thirdMinIdx = secondMinIdx;
                    secondMinIdx = minIdx;
                    minIdx = i;
                } else if (secondMinIdx == -1 || cnt[secondMinIdx] >= cnt[i]) {
                    thirdMinIdx = secondMinIdx;
                    secondMinIdx = i;
                } else if (thirdMinIdx == -1 || cnt[thirdMinIdx] >= cnt[i]) {
                    thirdMinIdx = i;
                }
                if (maxIdx == -1 || cnt[maxIdx] <= cnt[i]) {
                    secondMaxIdx = maxIdx;
                    maxIdx = i;
                } else if (secondMaxIdx == -1 || cnt[secondMaxIdx] < cnt[i]) {
                    secondMaxIdx = i;
                }
            }
            Set<Integer> interesting = new TreeSet<>();
            if (minIdx != -1) interesting.add(minIdx);
            if (secondMinIdx != -1) interesting.add(secondMinIdx);
            if (thirdMinIdx != -1) interesting.add(thirdMinIdx);
            if (maxIdx != -1) interesting.add(maxIdx);
            if (secondMaxIdx != -1) interesting.add(secondMaxIdx);
            interesting.add(counter - 2);
            interesting.add(counter - 1);
            indices = new int[interesting.size()];
            count = new int[indices.length];
            int ptr = 0;
            for (int i = 0; i < counter; ++i) {
                if (interesting.contains(i)) {
                    count[ptr] = cnt[i];
                    indices[ptr++] = i;
                }
            }
            idv = ptr - 2;
            idu = ptr - 1;
        }
        if (indices[idv] != counter - 2 || indices[idu] != counter - 1) throw new AssertionError("WTF?!");
        int[] votes = new int[4];
        int ans = -1;
        for (int i = 0; i < indices.length; ++i) {
            if (indices[i] == counter - 2) continue;
            for (int j = i + 1; j < indices.length; ++j) {
                if (indices[j] == counter - 2) continue;
                for (int k = 0; k < indices.length; ++k) {
                    if (indices[k] == counter - 1) continue;
                    for (int l = k + 1; l < indices.length; ++l) {
                        if (indices[l] == counter - 1) continue;
                        int[] _count = count.clone();
                        for (int d : new int[]{i, j, k, l}) {
                            _count[d]++;
                        }
                        int mn = Integer.MAX_VALUE;
                        int mx = -1;
                        for (int d : _count) {
                            mn = Math.min(mn, d);
                            mx = Math.max(mx, d);
                        }
                        int cur = 0;
                        if (mn < _count[idv] && _count[idv] < mx) ++cur;
                        if (mn < _count[idu] && _count[idu] < mx) ++cur;
                        if (cur > ans) {
                            ans = cur;
                            votes = new int[]{indices[i], indices[j], indices[k], indices[l]};
                        }
                    }
                }
            }
        }
        if (ans == 0) out.println("none");
        else if (ans == 1) out.println("one");
        else if (ans == 2) out.println("both");
        else throw new AssertionError("WAT?!oO");
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                out.print((votes[i * 2 + j] + 1) + " ");
            }
            out.println();
        }
    }
}
