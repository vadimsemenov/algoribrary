package algoribrary.strings;

/**
 * @author Vadim Semenov (semenov@rain.ifmo.ru)
 */
public class StringUtils {
    public static int[] buildPrefixFunction(final char[] string) {
        final int length = string.length;
        final int[] prefixFunction = new int[length];
        for (int i = 1, k = 0; i < length; ++i) {
            while (k > 0 && string[i] != string[k]) {
                k = prefixFunction[k - 1];
            }
            if (string[i] == string[k]) {
                k++;
            }
            prefixFunction[i] = k;
        }
        return prefixFunction;
    }

    public static int[] buildPrefixFunction(final String string) {
        return buildPrefixFunction(string.toCharArray());
    }

    public static int[] buildZFunction(final char[] string) {
        final int length = string.length;
        final int[] zFunction = new int[length];
        for (int i = 1, l = 0, r = 0; i < length; ++i) {
            if (i < r) {
                zFunction[i] = Math.min(zFunction[i - l], r - i);
            }
            while (i + zFunction[i] < length && string[zFunction[i]] == string[i + zFunction[i]]) {
                zFunction[i]++;
            }
            if (i + zFunction[i] > r) {
                l = i;
                r = i + zFunction[i];
            }
        }
        return zFunction;
    }

    public static int[] buildZFunction(final String string) {
        return buildZFunction(string.toCharArray());
    }

    public static int[] buildSuffixArray(final int[] string) {
        final int length = string.length;
        int[] suffixArray = new int[length];
        int[] bucket = new int[length];
        int[] _suffixArray = new int[length];
        int[] _bucket = new int[length];
        final int[] position;
        {
            int alphabet = 0;
            for (int i : string) {
                alphabet = Math.max(alphabet, i);
            }
            position = new int[Math.max(length, alphabet)];
            for (int ch : string) {
                position[ch]++;
            }
            int idx = 0;
            for (int i = 0; i < position.length; ++i) {
                int tmp = position[i];
                position[i] = idx;
                idx += tmp;
            }
            for (int i = 0; i < length; ++i) {
                suffixArray[position[string[i]]++] = i;
            }
        }
        int buckets = 0;
        position[0] = 0;
        for (int i = 1; i < length; ++i) {
            if (string[suffixArray[i]] != string[suffixArray[i - 1]]) {
                buckets++;
                position[buckets] = i;
            }
            bucket[suffixArray[i]] = buckets;
        }
        for (int part = 1; part < length; part <<= 1) {
            for (int i = 0; i < length; ++i) {
                int idx = (suffixArray[i] - part + length) % length;
                _suffixArray[position[bucket[idx]]++] = idx;
            }
            buckets = 0;
            position[0] = 0;
            for (int i = 1; i < length; ++i) {
                if (bucket[_suffixArray[i]] != bucket[_suffixArray[i - 1]] ||
                        bucket[(_suffixArray[i] + part) % length] != bucket[(_suffixArray[i - 1] + part) % length]) {
                    buckets++;
                    position[buckets] = i;
                }
                _bucket[_suffixArray[i]] = buckets;
            }
            int[] swap = suffixArray;
            suffixArray = _suffixArray;
            _suffixArray = swap;
            swap = bucket;
            bucket = _bucket;
            _bucket = swap;
        }
        return suffixArray;
    }

    public static int[] buildSuffixArray(final char[] string) {
        final int length = string.length;
        int[] aux = new int[length];
        int min = 256;
        for (char ch : string) {
            min = Math.min(min, ch);
        }
        for (int i = 0; i < length; ++i) {
            aux[i] = string[i] - min;
        }
        return buildSuffixArray(aux);
    }

    public static int[] buildSuffixArray(final String string) {
        return buildSuffixArray(string.toCharArray());
    }
}
