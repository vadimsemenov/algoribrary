package tasks;

import algoribrary.collections.intervaltree.fenwicktree.SegmentFenwickTree;
import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class TaskA {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int ads = in.nextInt();
        int channels = in.nextInt();
        Segment[] ad = new Segment[ads];
        for (int i = 0; i < ads; ++i) {
            ad[i] = new Segment(in.nextInt(), in.nextInt(), -1, i);
        }
        {
            Arrays.sort(ad, new Comparator<Segment>() {
                @Override
                public int compare(Segment o1, Segment o2) {
                    int cmp = Integer.compare(o1.begin, o2.begin);
                    if (cmp == 0) cmp = -Integer.compare(o1.end, o2.end);
                    return cmp;
                }
            });
            int ptr = 1;
            int mostRight = ad[0].end;
            for (int i = 1; i < ads;++i) {
                if (mostRight < ad[i].end) {
                    mostRight = ad[i].end;
                    ad[ptr++] = ad[i];
                }
            }
            Segment[] tmp = new Segment[ptr];
            System.arraycopy(ad, 0, tmp, 0, ptr);
            ad = tmp;
        }
        Segment[] channel = new Segment[channels];
        for (int i = 0; i < channels; ++i) {
            channel[i] = new Segment(in.nextInt(), in.nextInt(), in.nextInt(), i);
        }
        Segment[] all = new Segment[ads + channels];
        System.arraycopy(ad, 0, all, 0, ads);
        System.arraycopy(channel, 0, all, ads, channels);
        int[] bestLength = new int[channels];
        int[] bestAd = new int[channels];
        Arrays.fill(bestAd, -1);

        {
            Arrays.sort(all);
            int mostRight = -1;
            int mostRightId = -1;
            for (int i = 0; i < all.length; ++i) {
                if (all[i].count == -1) {
                    if (mostRight < all[i].end) {
                        mostRight = all[i].end;
                        mostRightId = all[i].id;
                    }
                } else if (mostRight > all[i].begin){
                    int length = Math.min(all[i].end, mostRight) - all[i].begin;
                    if (bestLength[all[i].id] < length) {
                        bestLength[all[i].id] = length;
                        bestAd[all[i].id] = mostRightId;
                    }
                }
            }
        }
        {
            Arrays.sort(all, new Comparator<Segment>() {
                @Override
                public int compare(Segment o1, Segment o2) {
                    int cmp = -Integer.compare(o1.end, o2.end);
                    if (cmp == 0) cmp = Integer.compare(o1.count, o2.count);
                    return cmp;
                }
            });
            int mostLeft = Integer.MAX_VALUE;
            int mostLeftId = -1;
            for (int i = 0; i < all.length; ++i) {
                if (all[i].count == -1) {
                    if (mostLeft > all[i].begin) {
                        mostLeft = all[i].begin;
                        mostLeftId = all[i].id;
                    }
                } else if (mostLeft < all[i].end) {
                    int length = all[i].end - Math.max(all[i].begin, mostLeft);
                    if (bestLength[all[i].id] < length) {
                        bestLength[all[i].id] = length;
                        bestAd[all[i].id] = mostLeftId;
                    }
                }
            }
        }
        Arrays.sort(ad);
        Arrays.sort(channel);
        int ptr = 0;
        SegmentFenwickTree<Integer> bit = new SegmentFenwickTree<Integer>(0, ads) {
            @Override
            public Integer join(Integer leftValue, Integer rightValue) {
                return Math.max(leftValue, rightValue);
            }
        };
        for (int i = 0; i < ads; ++i) {
            bit.update(i, ad[i].end - ad[i].begin);
        }
        for (int i = 0; i < channels; ++i) {
            int left = -1;
            int right = ads;
            while (right - left > 1) {
                int middle = (left + right) >>> 1;
                if (ad[middle].begin >= channel[i].begin) {
                    left = middle;
                } else {
                    right = middle;
                }
            }
            int from = left;

            left = -1;
            right = ads;
            while (right - left > 1) {
                int middle = (left + right) >>> 1;
                if (ad[middle].end <= channel[i].end) {
                    left = middle;
                } else {
                    right = middle;
                }
            }
            int to = right;
            if (from < to) {
                int length = bit.query(from, to);
            }
        }

        long answer = 0;
        int CHANNEL = -1;
        int AD = -1;
        for (int i = 0; i < channels; ++i) {
            long ans = (long) bestLength[i] * channel[i].count;
            if (ans > answer) {
                answer = ans;
                CHANNEL = i + 1;
                AD = bestAd[i] + 1;
            }
        }
        out.println(answer);
        if (answer > 0) out.println(AD + " " + CHANNEL);
    }

    static class Segment implements Comparable<Segment> {
        final int begin, end;
        final int id;
        final int count;

        public Segment(int begin, int end, int count, int id) {
            this.begin = begin;
            this.end = end;
            this.count = count;
            this.id = id;
        }

        @Override
        public int compareTo(Segment o) {
            int cmp = Integer.compare(this.begin, o.begin);
            if (cmp == 0) cmp = -Integer.compare(this.count, o.count);
            return cmp;
        }
    }
}
