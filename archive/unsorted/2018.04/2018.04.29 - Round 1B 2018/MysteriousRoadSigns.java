package tasks;

import algoribrary.io.InputReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class MysteriousRoadSigns {
    public void solve(int testCase, InputReader in, PrintWriter out) {
        int counter = in.nextInt();
        Sign[] signs = new Sign[counter];
        int minEast = 0;
        int minWest = 0;
        int maxEast = 0;
        int maxWest = 0;
        for (int i = 0; i < counter; ++i) {
            int dist = in.nextInt();
            int west = in.nextInt();
            int east = in.nextInt();
            signs[i] = new Sign(dist + west, dist - east);
            minEast = Math.min(minEast, signs[i].east);
            maxEast = Math.max(maxEast, signs[i].east);
            minWest = Math.min(minWest, signs[i].west);
            maxWest = Math.max(maxWest, signs[i].west);
        }
        List<Sign>[] byEast = new List[maxEast - minEast + 1];
        List<Sign>[] byWest = new List[maxWest - minWest + 1];
        for (Sign sign : signs) {
            if (byEast[sign.east - minEast] == null) {
                byEast[sign.east - minEast] = new ArrayList<>();
            }
            byEast[sign.east - minEast].add(sign);
            if (byWest[sign.west - minWest] == null) {
                byWest[sign.west - minWest] = new ArrayList<>();
            }
            byWest[sign.west - minWest].add(sign);
        }
        Comparator<Sign> eastComparator = Comparator.comparingInt(Sign::getEast);
        Comparator<Sign> westComparator = Comparator.comparingInt(Sign::getWest);
        List<Integer> bigEast = new ArrayList<>();
        List<Integer> bigWest = new ArrayList<>();
        int threshold = (int) (1 + Math.sqrt(counter));
        for (int i = 0; i < byEast.length; ++i) {
            if (byEast[i] != null) {
                byEast[i].sort(westComparator);
                if (byEast[i].size() >= threshold) {
                    bigEast.add(i);
                }
            }
        }
        for (int i = 0; i < byWest.length; ++i) {
            if (byWest[i] != null) {
                byWest[i].sort(eastComparator);
                if (byWest[i].size() > threshold) {
                    bigWest.add(i);
                }
            }
        }
        
    }

    static class Sign {
        final int west;
        final int east;

        Sign(int west, int east) {
            this.west = west;
            this.east = east;
        }

        public int getWest() {
            return west;
        }

        public int getEast() {
            return east;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Sign sign = (Sign) o;
            return west == sign.west && east == sign.east;
        }

        @Override
        public int hashCode() {
            return 31 * west + east;
        }
    }
}
