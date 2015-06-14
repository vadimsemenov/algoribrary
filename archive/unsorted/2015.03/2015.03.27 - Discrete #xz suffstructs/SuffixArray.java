package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class SuffixArray {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        final String string = in.next() + ((char) ('a' - 1));
        final int length = string.length();
        int[] suffixArray = new int[length];
        int[] bucket = new int[length];
        int[] _suffixArray = new int[length];
        int[] _bucket = new int[length];
        int[] pointer;
        int buckets;
        {
            int minCharacter = Character.MAX_VALUE;
            int maxCharacter = Character.MIN_VALUE;
            for (int i = 0; i < length; ++i) {
                minCharacter = Math.min(minCharacter, string.charAt(i));
                maxCharacter = Math.max(maxCharacter, string.charAt(i));
            }
            int alphabet = maxCharacter - minCharacter + 1;
            pointer = new int[Math.max(alphabet, length)];
            for (int i = 0; i < length; ++i) {
                pointer[string.charAt(i) - minCharacter]++;
            }
            int idx = 0;
            for (int i = 0; i < pointer.length; ++i) {
                int cnt = pointer[i];
                pointer[i] = idx;
                idx += cnt;
            }
            for (int i = 0; i < length; ++i) {
                suffixArray[pointer[string.charAt(i) - minCharacter]++] = i;
            }
            pointer[0] = 0;
            buckets = 0;
            for (int i = 1; i < length; ++i) {
                if (string.charAt(suffixArray[i]) != string.charAt(suffixArray[i - 1])) {
                    buckets++;
                    pointer[buckets] = i;
                }
                bucket[suffixArray[i]] = buckets;
            }
        }
        for (int part = 1; part < length; part <<= 1) {
            for (int i = 0; i < length; ++i) {
                int idx = (suffixArray[i] - part + length) % length;
                _suffixArray[pointer[bucket[idx]]++] = idx;
            }
            buckets = 0;
            pointer[0] = 0;
            int previous = _suffixArray[0];
            for (int i = 1; i < length; ++i) {
                int current = _suffixArray[i];
                if (bucket[current] != bucket[previous] ||
                        bucket[(current + part) % length] != bucket[(previous + part) % length]) {
                    buckets++;
                    pointer[buckets] = i;
                }
                _bucket[current] = buckets;
                previous = current;
            }
            int[] swap = bucket; bucket = _bucket; _bucket = swap;
            swap = suffixArray; suffixArray = _suffixArray; _suffixArray = swap;
        }
        for (int i = 1; i < length; ++i) {
            if (i > 1) out.print(' ');
            out.print(suffixArray[i] + 1);
        }
    }
}
