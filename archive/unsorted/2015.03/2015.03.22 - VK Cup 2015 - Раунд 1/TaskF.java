package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;

public class TaskF {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        String sequence = in.next();
        final int length = sequence.length();
        int[] s = new int[length];
        for (int i = 0; i < length; ++i) {
            s[i] = sequence.charAt(i) - '(';
        }
        int[] suffixArray = buildSuffixArray(s);
        int[] balance = new int[length * 2];
        for (int i = 1; i < balance.length; ++i) {
            balance[i] = balance[i - 1];
            if (s[(i - 1) % length] == 0) {
                balance[i]++;
            } else {
                balance[i]--;
            }
        }
        int total = balance[length];
        int opened = 0, closed = 0;
        if (total > 0) {
            closed = total;
        } else {
            opened = -total;
        }
        RMQ rmq = new RMQ(balance);
        int best = -1;
        for (int i : suffixArray) {
            int offset = balance[i];
            offset = opened - offset;
            if (rmq.query(i + 1, i + 1 + length) + offset >= 0) {
                if (balance[i + length] + offset != closed) {
                    throw new AssertionError(balance[i + length] + " + " + offset + " != " + closed);
                }
                best = i;
                break;
            }
        }
        for (int i = 0; i < opened; ++i) {
            out.print('(');
        }
        out.print(sequence.substring(best, sequence.length()));
        out.print(sequence.substring(0, best));
        for (int i = 0; i < closed; ++i) {
            out.print(')');
        }
        out.println();
    }

    private static class RMQ {
        int[][] data;
        int[] log;

        RMQ(int[] initial) {
            int length = initial.length;
            log = new int[length + 1];
            for (int i = 2; i <= length; ++i) {
                log[i] = log[i >>> 1] + 1;
            }
            data = new int[log[length]][length];
            System.arraycopy(initial, 0, data[0], 0, length);
            for (int level = 1; level < log[length]; ++level) {
                int len = 1 << (level - 1);
                for (int i = 0; (i + len + len) <= length; ++i) {
                    data[level][i] = Math.min(data[level - 1][i], data[level - 1][i + len]);
                }
            }
        }

        int query(int from, int to) {
            int level = log[to - from];
            return Math.min(data[level][from], data[level][to - (1 << level)]);
        }
    }

    private int[] buildSuffixArray(int[] s) {
        final int length = s.length;
        int[] suffixArray = new int[length];
        int[] bucket = new int[length];
        int[] _suffixArray = new int[length];
        int[] _bucket = new int[length];
        int[] position = new int[Math.max(2, length)];
        int buckets = 0;
        {
            for (int i : s) {
                position[i]++;
            }
            int pos = 0;
            for (int i = 0; i < position.length; ++i) {
                int tmp = position[i];
                position[i] = pos;
                pos += tmp;
            }
            for (int i = 0; i < length; ++i) {
                suffixArray[position[s[i]]++] = i;
            }
            position[0] = 0;
            for (int i = 1; i < length; ++i) {
                if (s[suffixArray[i]] != s[suffixArray[i - 1]]) {
                    buckets++;
                    position[buckets] = i;
                }
                bucket[suffixArray[i]] = buckets;
            }
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
            int[] swap = bucket; bucket = _bucket; _bucket = swap;
            swap = suffixArray; suffixArray = _suffixArray; _suffixArray = swap;
        }
        return suffixArray;
    }
}
