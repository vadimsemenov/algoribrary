package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.*;

public class Toplist {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int conditions = in.nextInt();
        List<String> songs = new ArrayList<>();
        final Map<String, int[]> map = new HashMap<>();
        int cnt = 0;
        int maxPrefix = -1;
        for (int i = 0; i < conditions; i++) {
            int counter = in.nextInt();
            if (!"of".equals(in.next())) throw new AssertionError();
            int prefix = in.nextInt();
            maxPrefix = Math.max(maxPrefix, prefix);
            for (int j = 0; j < counter; j++) {
                String song = in.next();
                if (!map.containsKey(song)) {
                    songs.add(song);
                    map.put(song, new int[]{cnt, prefix});
                    cnt++;
                } else {
                    int[] pair = map.get(song);
                    pair[1] = Math.min(pair[1], prefix);
                }
            }
        }
        Collections.sort(songs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(map.get(o1)[1], map.get(o2)[1]);
            }
        });
        Set<String>[] sets = new Set[maxPrefix + 1];
        for (int i = 0; i <= maxPrefix; i++) sets[i] = new HashSet<>();
        for (String song : songs) {
            int maxPosition = map.get(song)[1];
            sets[maxPosition].add(song);
        }
        for (int i = 1; i <= maxPrefix; i++)
            sets[i].addAll(sets[i - 1]);
        for (int i = 1; i <= maxPrefix; i++) {
            if (sets[i - 1].size() == i - 1 && sets[i].size() == i) {
                out.println(i + " " + songs.get(i - 1));
            }
        }
    }
}
