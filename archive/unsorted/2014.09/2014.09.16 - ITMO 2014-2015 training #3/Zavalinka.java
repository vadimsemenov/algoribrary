package tasks;

import algoribrary.io.InputReader;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Zavalinka {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int teams = in.nextInt();
        int words = in.nextInt();
        Map<String, Integer> map = new HashMap<>();
        String[] names = new String[teams + 1];
        for (int i = 0; i < teams; ++i) {
            names[i] = in.next();
            map.put(names[i], i);
        }
        names[teams] = "$jury$";
        map.put(names[teams], teams);
        int[] result = new int[teams + 1];

        // unnecessary words :/
        for (int i = 0; i < words; ++i) {
            in.next();
        }

        String[] author = new String[teams + 2];
        for (int block = 0; block < words; ++block) {
            in.next(); // one more :|
            for (int i = 0; i < teams + 2; ++i) {
                author[i] = in.next();
            }
            for (int t = 0; t < teams; ++t) {
                String teamName = in.next();
                int num = in.nextInt() - 1;
                if (author[num].equals(teamName)) {
                    result[map.get(teamName)] -= 10;
                } else if (author[num].equals("$answer$")) {
                    result[map.get(teamName)] += 2;
                } else {
                    result[map.get(author[num])]++;
                }
            }
        }
        for (int t = 0; t <= teams; ++t) {
            out.println(names[t] + " " + result[t]);
        }
    }
}
